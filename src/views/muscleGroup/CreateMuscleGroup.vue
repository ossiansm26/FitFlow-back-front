<template>
  <v-container>
    <BackBar />
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center">Crear Musculo</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="crearObjeto">
              <div class="form-group">
                <v-text-field
                  v-model="nuevoObjeto.groupName"
                  id="groupName"
                  label="Nombre del grupo"
                  required
                ></v-text-field>
              </div>
              <div class="form-group">
                <v-text-field
                  v-model="nuevoObjeto.bodyPart"
                  id="bodyPart"
                  label="Parte del cuerpo"
                  required
                ></v-text-field>
              </div>
              <div class="form-group">
                <label for="muscleImage">Imagen del músculo:</label>
                <vue-dropzone
                  @vdropzone-success="onDropSuccess"
                  ref="dropzone"
                  :id="'dropzoneId'"
                  :options="dropzoneOptions"
                ></vue-dropzone>
                <v-btn
                  @click="removeMuscleImage"
                  class="mt-2"
                  color="error"
                  v-if="nuevoObjeto.muscleImage"
                >
                  Eliminar Imagen
                </v-btn>
              </div>
              <button type="submit" class="btn btn-primary mt-2">Crear grupo muscular</button>
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
import MuscleGroup from '@/models/MuscleGroup';
import BackBar from "@/components/navbar/BackBar.vue";

export default {
  name: "CrearObjeto",
  components: {
    VueDropzone,
    BackBar
  },
  data() {
    return {
      nuevoObjeto: new MuscleGroup("", "", ""),
      dropzoneOptions: {
        url: "http://localhost:3001/api/file/upload",
        maxFilesize: 10, // MB
        acceptedFiles: "image/*",
      },
      token: localStorage.getItem('token'),
    };
  },
  methods: {
    async crearObjeto() {
      axios.post("http://localhost:3001/api/muscleGroup/create", this.nuevoObjeto,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log("Objeto creado");
          this.$router.push({ name: "muscleGroup" });
        })
        .catch((error) => {
          console.error(error);
        });
    },
    removeMuscleImage() {
      this.nuevoObjeto.muscleImage = "";
      this.$refs.dropzone.removeAllFiles();
    },
    onDropSuccess(file) {
      console.log("Archivo subido:", file.upload.filename);
      this.nuevoObjeto.muscleImage = file.upload.filename;
    },
  },
};
</script>
