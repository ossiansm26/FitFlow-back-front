<template>
  <v-container>
    <BackBar />
    <v-card>
      <v-card-title>
        Lista de Ejercicios para la Collecion de Ejercicios{{ collectionId }}
      </v-card-title>
      <v-card-text>
        <table class="table">
          <thead>
            <tr>
              <th></th>
              <th>ID</th>
              <th>Nombre del Ejercicio</th>
              <th>Duración (min)</th>
              <th>Descripción</th>
              <th>Material</th>
              <th>Grupos Musculares</th>
              <th>Imagen</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="exercise in sortedExercises" :key="exercise.id">
              <td>
                <v-checkbox
                  v-model="selectedExercises"
                  :value="exercise.id"
                  @change="checkboxChanged(exercise)"
                ></v-checkbox>
              </td>
              <td>{{ exercise.id }}</td>
              <td>{{ exercise.exerciseName }}</td>
              <td>{{ exercise.duration }}</td>
              <td>{{ exercise.description }}</td>
              <td>
                <ul>
                  <li v-for="(material, index) in exercise.material" :key="index">
                    {{ material.materialName }}
                  </li>
                </ul>
              </td>
              <td>
                <ul>
                  <li v-for="(muscle, index) in exercise.muscleGroup" :key="index">
                    {{ muscle.groupName }}
                  </li>
                </ul>
              </td>
              <td>
                <v-img
                  class="exercise-image"
                  :src="getImageUrl(exercise.urlImage)"
                  :alt="exercise.exerciseName"
                  contain
                ></v-img>
              </td>
              <td>
                <v-row class="actions-row">
                  <v-col cols="12" sm="4">
                    <v-btn icon color="primary" @click="editExercise(exercise)">
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                  </v-col>
                  <v-col cols="12" sm="4">
                    <v-btn icon color="error" @click="deleteExercise(exercise.id)">
                      <v-icon>mdi-delete</v-icon>
                    </v-btn>
                  </v-col>
                  <v-col cols="12" sm="4">
                    <v-btn icon color="purple" @click="viewExerciseDetail(exercise.id)">
                      <v-icon>mdi-eye</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </td>
            </tr>
          </tbody>
        </table>
      </v-card-text>
    </v-card>
    <div class="floating-btn">
      <v-btn fab dark color="green" @click="addExercise">
        <v-icon dark>mdi-plus</v-icon>
      </v-btn>
      <v-btn fab dark color="blue" @click="goBack">
        <v-icon dark>mdi-content-save</v-icon>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import BackBar from "@/components/navbar/BackBar.vue";
import router from "@/router";
import axios from "axios";

export default {
  components: {
    BackBar,
  },
  data() {
    return {
      exercises: [],
      selectedExercises: [],
      collectionId: 0,
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
  created() {
    this.collectionId = localStorage.getItem("selectedExerciseId");
  },
  mounted() {
    this.loadExercises();
    this.loadSelectedExercises();
  },
  methods: {
    loadExercises() {
      axios
        .get(`http://localhost:3001/api/exercices`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          console.log("Exercises:", response.data);
          this.exercises = response.data;
        })
        .catch((error) => {
          console.error("Error fetching exercise data:", error);
        });
    },
    loadSelectedExercises() {
      axios
        .get(`http://localhost:3001/api/collectionExercices/${this.collectionId}/getExercices`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          this.selectedExercises = response.data.map(exercise => exercise.id);
        })
        .catch((error) => {
          console.error("Error fetching selected exercises:", error);
        });
    },
    getImageUrl(imageName) {
      console.log("Image name:", imageName);
      return `http://localhost:3001/api/file/download/${imageName}`;
    },
    editExercise(exercise) {
      localStorage.setItem("selectedExerciseId", exercise.id);
      this.$router.push({ name: "exerciceEdit" });
    },
    deleteExercise(exerciseId) {
      axios
        .delete(`http://localhost:3001/api/exercices/${exerciseId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          this.exercises = this.exercises.filter(
            (exercise) => exercise.id !== exerciseId
          );
        })
        .catch((error) => {
          console.error("Error deleting exercise:", error);
        });
    },
    addExercise() {
      router.push({ name: "createExercice" });
    },
    checkboxChanged(exercise) {
      const exerciseId = exercise.id;
      if (this.selectedExercises.includes(exerciseId)) {
        this.addExerciseToCollection(exerciseId);
      } else {
        this.removeExerciseFromCollection(exerciseId);
      }
    },
    addExerciseToCollection(exerciseId) {
      axios
        .post(`http://localhost:3001/api/collectionExercices/${this.collectionId}/addExercices/${exerciseId}`,{},{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log(`Exercise ${exerciseId} added to collection ${this.collectionId}`);
        })
        .catch((error) => {
          console.error(`Error adding exercise ${exerciseId} to collection:`, error);
        });
    },
    removeExerciseFromCollection(exerciseId) {
      axios
        .delete(`http://localhost:3001/api/collectionExercices/${this.collectionId}/removeExercices/${exerciseId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log(`Exercise ${exerciseId} removed from collection ${this.collectionId}`);
        })
        .catch((error) => {
          console.error(`Error removing exercise ${exerciseId} from collection:`, error);
        });
    },
    viewExerciseDetail(exerciseId) {
      localStorage.setItem("selectedExerciseId", exerciseId);
      this.$router.push({ name: "exercicesDetails" });
    },
    goBack() {
      window.history.go(-1); 
    },
  },
};
</script>

<style scoped>
.headline {
  font-size: 24px;
  margin-bottom: 10px;
}

.mb-4 {
  margin-bottom: 1.5rem !important;
}

.mt-4 {
  margin-top: 1.5rem !important;
}

.floating-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
}

.actions-row {
  display: flex;
  flex-wrap: wrap;
}

.exercise-image {
  width: 100px;
  height: 100px;
  object-fit: contain;
}
</style>