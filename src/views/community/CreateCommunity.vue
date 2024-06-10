<template>
  <v-container>
    <BackBar />
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center">Crear Comunidad</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="createCommunity">
              <div class="form-group">
                <v-text-field v-model="newCommunity.name" label="Nombre" required></v-text-field>
              </div>
              <div class="form-group">
                <v-textarea v-model="newCommunity.description" label="Descripción" required></v-textarea>
              </div>
              <div class="form-group">
                <vue-dropzone
                  @vdropzone-success="onDropSuccess"
                  ref="dropzone"
                  :id="'dropzoneId'"
                  :options="dropzoneOptions"
                ></vue-dropzone>
              </div>
              <div class="form-group" v-if="newCommunity.urlpicture">
                <v-btn @click="removeCommunityImage" 
                  class="mt-2"
                  color="error"
                >Eliminar Imagen</v-btn>
              </div>
              <div class="form-group">
                <v-btn type="submit" color="primary">Crear comunidad</v-btn>
              </div>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import Community from '@/models/Community';
import BackBar from '@/components/navbar/BackBar.vue';
import VueDropzone from "vue2-dropzone";

export default {
  components: {
    BackBar,
    VueDropzone
  },
  data() {
    return {
      newCommunity: new Community(),
      userId: '',
      dropzoneOptions: {
        url: "http://localhost:3001/api/file/upload",
        maxFilesize: 10, // MB
        acceptedFiles: "image/*",
      },
      token: localStorage.getItem('token'),
    };
  },
  mounted() {
    this.userId = localStorage.getItem('userId');
  },
  methods: {
    createCommunity() {
      axios.post(`http://localhost:3001/api/community/create/${this.userId}`, this.newCommunity,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          console.log('Comunidad creada:', response.data);
          
          this.$router.push({ name: 'community' });
        })
        .catch(error => {
          console.error('Error al crear comunidad:', error);
          
        });
    },
    removeCommunityImage() {
      this.newCommunity.urlpicture = '';
      this.$refs.dropzone.removeAllFiles();
    },
    onDropSuccess(file) {
      this.newCommunity.urlpicture = file.upload.filename;
    },
  }
};
</script>

<style scoped>

</style>
