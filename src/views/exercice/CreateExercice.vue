<template>
  <v-container>
    <BackBar />
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center">Crear Ejercicio</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="createExercise">
              <div class="form-group">
                <label for="exerciseName">Nombre del Ejercicio</label>
                <input v-model="exercise.exerciseName" type="text" class="form-control" id="exerciseName" required>
              </div>
              <div class="form-group">
                <label for="duration">Duración</label>
                <input v-model.number="exercise.duration" type="number" class="form-control" id="duration" required>
              </div>
              <div class="form-group">
                <label for="description">Descripción</label>
                <input v-model="exercise.description" type="text" class="form-control" id="description" required>
              </div>
              <div class="form-group">
                <label for="exerciseImage">Imagen del Ejercicio</label>
                <vue-dropzone
                  @vdropzone-success="onDropSuccess"
                  ref="dropzone"
                  :id="'dropzoneId'"
                  :options="dropzoneOptions">
                  <div class="dropzone"></div>
                </vue-dropzone>
                <v-btn
                  @click="removeExerciseImage"
                  class="mt-2"
                  color="error"
                  v-if="exercise.urlImage"
                  >Eliminar Imagen</v-btn>
              </div>
              <button type="submit" class="btn btn-primary">Crear Ejercicio</button>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import VueDropzone from "vue2-dropzone";
import "vue2-dropzone/dist/vue2Dropzone.min.css";
import Exercices from '@/models/Exercices';
import BackBar from "@/components/navbar/BackBar.vue";

export default {
  name: "CrearEjercicio",
  components: {
    VueDropzone,
    BackBar
  },
  data() {
    return {
      exercise: new Exercices("", "", "", "", "", [], []),
      dropzoneOptions: {
        url: "http://localhost:3001/api/file/upload",
        maxFilesize: 10, // MB
        acceptedFiles: "image/*",
      },
      token: localStorage.getItem('token'),
    };
  },
  methods: {
    async createExercise() {
      try {
        await axios.post("http://localhost:3001/api/exercices/create", this.exercise,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              });
        console.log("Ejercicio creado", this.exercise);
        this.$router.push({ name: "exercices" });
      } catch (error) {
        console.error("Error creando el ejercicio:", error);
      }
    },
    removeExerciseImage() {
      this.exercise.urlImage = "";
      this.$refs.dropzone.removeAllFiles();
    },
    onDropSuccess(file) {
      console.log("Archivo subido:", file.upload.filename);
      this.exercise.urlImage = file.upload.filename;
    },
  },
};
</script>

<style>
.dropzone {
  border: 2px dashed #ccc;
  border-radius: 5px;
  padding: 10px;
  text-align: center;
}
</style>
