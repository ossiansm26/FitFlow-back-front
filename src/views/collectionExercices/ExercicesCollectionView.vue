<template>
  <v-container>
    <BackBar />
    <v-card>
      <v-card-title>
        Lista de Ejercicios para la Rutina {{ routineId }}
      </v-card-title>
      <v-card-text>
        <table class="table">
          <thead>
            <tr>
              <th></th>
              <th>ID</th>
              <th>Nombre de la Colección</th>
              <th>Nivel de Dificultad</th>
              <th>Video Explicativo</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="exercise in sortedExercises" :key="exercise.id">
              <td>
                <v-checkbox
                  v-model="selectedExercises"
                  :value="exercise.id"
                  @change="checkboxChanged(exercise.id)"
                ></v-checkbox>
              </td>
              <td>{{ exercise.id }}</td>
              <td>{{ exercise.collectionName }}</td>
              <td>{{ exercise.difficultyLevel }}</td>
              <td>
                <iframe
                  v-if="getYouTubeEmbedUrl(exercise.urlExplanatoryVideo)"
                  title="Exercise Video"
                  width="560"
                  height="315"
                  :src="getYouTubeEmbedUrl(exercise.urlExplanatoryVideo)"
                  allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                  allowfullscreen
                ></iframe>
              </td>
              <td>
                <v-btn icon color="primary" @click="editExercise(exercise)">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <v-btn icon color="error" @click="deleteExercise(exercise.id)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
                <v-btn icon color="purple" @click="viewExerciseDetail(exercise.id)">
                  <v-icon>mdi-eye</v-icon>
                </v-btn>
              </td>
            </tr>
          </tbody>
        </table>
      </v-card-text>
      <div class="floating-button">
        <v-btn fab dark color="green" @click="createExercise">
          <v-icon dark>mdi-plus</v-icon>
        </v-btn>
        <v-btn fab dark color="blue" @click="goBack">
          <v-icon dark>mdi-content-save</v-icon>
        </v-btn>
      </div>
    </v-card>
  </v-container>
</template>

<script>
import BackBar from "@/components/navbar/BackBar.vue";
import axios from "axios";

export default {
  components: {
    BackBar,
  },
  data() {
    return {
      exercises: [],
      selectedExercises: [],
      routineId: 0,
      token: localStorage.getItem("token"),
    };
  },
  computed: {
    sortedExercises() {
      return this.exercises.slice().sort((a, b) => {
        return this.selectedExercises.includes(b.id) - this.selectedExercises.includes(a.id);
      });
    },
  },
  mounted() {
    this.fetchExercises();
    this.routineId = localStorage.getItem("selectedRoutineId");
    this.fetchRoutineExercises();
  },
  methods: {
    fetchExercises() {
      axios
        .get("http://localhost:3001/api/collectionExercices",{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          this.exercises = response.data;
        })
        .catch((error) => {
          console.error("Error fetching exercises:", error);
        });
    },
    fetchRoutineExercises() {
      axios
        .get(`http://localhost:3001/api/routine/${this.routineId}/getExercices`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          console.log("Routine exercises:", response.data);
          this.selectedExercises = response.data.map((exercise) => exercise.id);
        })
        .catch((error) => {
          console.error("Error fetching routine exercises:", error);
        });
    },
    getYouTubeEmbedUrl(url) {
      const regExp = /^(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/(?:[^\/\n\s]+\/\S+\/|(?:v|embed)\/|\S*?[?&]v=)|youtu\.be\/)([a-zA-Z0-9_-]{11})/;
      const match = url.match(regExp);
      return match && match[1] ? `https://www.youtube.com/embed/${match[1]}` : null;
    },
    viewExerciseDetail(exercise) {
      localStorage.setItem("selectedExerciseId", exercise);
      this.$router.push("Exercices");
    },
    editExercise(exercise) {
      localStorage.setItem("selectedExerciseId", exercise.id);
      this.$router.push("editExercicesCollection");
    },
    deleteExercise(exerciseId) {
      axios
        .delete(`http://localhost:3001/api/collectionExercices/delete/${exerciseId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log("Ejercicio eliminado con éxito");
          this.fetchExercises();
        })
        .catch((error) => {
          console.error("Error eliminando ejercicio:", error);
        });
    },
    createExercise() {
      this.$router.push("createExercicesCollection");
    },
    checkboxChanged(exerciseId) {
      if (this.selectedExercises.includes(exerciseId)) {
        this.addExercise(exerciseId);
      } else {
        this.deleteExercise(exerciseId);
      }
    },
    goBack() {
      window.history.go(-1);
    },
    addExercise(exerciseId) {
      axios
        .post(
          `http://localhost:3001/api/routine/${this.routineId}/addExercices/${exerciseId}`,{},{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              }
        )
        .then(() => {
          console.log("Ejercicio añadido a la rutina con éxito");
        })
        .catch((error) => {
          console.error("Error añadiendo ejercicio a la rutina:", error);
        });
    },
    deleteExercise(exerciseId) {
      axios
        .delete(
          `http://localhost:3001/api/routine/${this.routineId}/removeExercices/${exerciseId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              }
        )
        .then(() => {
          console.log("Ejercicio eliminado de la rutina con éxito");
        })
        .catch((error) => {
          console.error("Error eliminando ejercicio de la rutina:", error);
        });
    },
  },
};
</script>

<style>
.floating-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
}
</style>
