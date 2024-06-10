<template>
  <v-container>
    <BackBar />
    <v-card class="mb-4">
      <v-card-title>
        <v-text-field
          v-model="exercise.exerciseName"
          label="Nombre del Ejercicio"
        ></v-text-field>
      </v-card-title>
      <v-card-text>
        <v-img
          class="exercise-image"
          :src="exercise.urlImage ? getImageUrl(exercise.urlImage) : ''"
          :alt="exercise.exerciseName"
          contain
        ></v-img>
        <vue-dropzone
          id="exercise-image-dropzone"
          ref="exerciseImageDropzone"
          :options="dropzoneOptions"
          @vdropzone-success="onDropSuccess"
        >
        </vue-dropzone>
        <v-text-field
          v-model="exercise.duration"
          label="Duración (min)"
        ></v-text-field>
        <v-text-field
          v-model="exercise.description"
          label="Descripción"
        ></v-text-field>
      </v-card-text>
    </v-card>
    <v-btn color="primary" @click="saveExercise">Guardar Cambios</v-btn>
  </v-container>
</template>
  
  <script>
import BackBar from "@/components/navbar/BackBar.vue";
import axios from "axios";
import Exercices from "@/models/Exercices.ts";
import router from "@/router";
import VueDropzone from "vue2-dropzone";

export default {
  components: {
    BackBar,
    VueDropzone,
  },
  data() {
    return {
      exercise: new Exercices("", "", "", "", [], [], []),
      dropzoneOptions: {
        url: "http://localhost:3001/api/file/upload",
        maxFilesize: 10, // MB
        acceptedFiles: "image/*",
      },
      token: localStorage.getItem("token"),
    };
  },
  mounted() {
    const exerciseId = localStorage.getItem("selectedExerciseId");
    this.fetchExerciseDetails(exerciseId);
  },
  methods: {
    fetchExerciseDetails(exerciseId) {
      axios
        .get(
          `http://localhost:3001/api/exercices/getExerciseById/${exerciseId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              }
        )
        .then((response) => {
          this.exercise = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getImageUrl(imageName) {
      return `http://localhost:3001/api/file/download/${imageName}`;
    },
    onDropSuccess(file) {
      this.exercise.urlImage = file.upload.filename;
    },
    saveExercise() {
      console.log("Exercise:", this.exercise);
      axios
        .post(
          `http://localhost:3001/api/exercices/update/${this.exercise.id}`,this.exercise,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              }
        )
        .then((response) => {
          console.log("Exercise updated successfully:", response.data);
          router.push({ name: "exercices" });
        })
        .catch((error) => {
          console.error("Error updating exercise:", error);
        });
    },
  },
};
</script>
  
  <style scoped>
.exercise-image {
  width: 100%;
  height: 300px;
  object-fit: contain;
  margin-bottom: 20px;
}
</style>
  