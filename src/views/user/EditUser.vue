<template>
  <v-container>
    <BackBar/>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center">Edit User</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="saveUser">
              <div class="form-group">
                <label for="name">Name</label>
                <input v-model="user.name" type="text" class="form-control" id="name" required>
              </div>
              <div class="form-group">
                <label for="lastNames">Last Names</label>
                <input v-model="user.lastNames" type="text" class="form-control" id="lastNames" required>
              </div>
              <div class="form-group">
                <label for="age">Age</label>
                <input v-model="user.age" type="date" class="form-control" id="age" required>
              </div>
              <div class="form-group">
                <label for="email">Email</label>
                <input v-model="user.email" type="email" class="form-control" id="email" required>
              </div>
              <div class="form-group">
                <label for="password">Password</label>
                <input v-model="user.password" type="password" class="form-control" id="password" required>
              </div>
              <div class="form-group">
                <label for="phoneNumber">Phone Number</label>
                <input v-model="user.phoneNumber" type="text" class="form-control" id="phoneNumber" required>
              </div>
              <div class="form-group">
                <label for="address">Address</label>
                <input v-model="user.address" type="text" class="form-control" id="address" required>
              </div>
              <div class="form-group">
                <label for="picture">Picture</label>
                <img class="imagePicture" :src="getImageUrl(user.image)" alt="User picture">
                <vue-dropzone
                  @vdropzone-success="onDropSuccess"
                  ref="dropzone"
                  :id="'dropzoneId'"
                  :options="dropzoneOptions">
                  <div class="dropzone"></div>
                </vue-dropzone>
              </div>
              <button type="submit" class="btn btn-primary">Save</button>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import User from '@/models/User';
import axios from 'axios';
import BackBar from '@/components/navbar/BackBar.vue';
import VueDropzone from "vue2-dropzone";
import { formatDateYYMMDD } from '@/utils/utils';

export default {
  components: {
    BackBar,
    VueDropzone
  },
  data() {
    return {
      token: localStorage.getItem('token'),
      user: new User(),
      dropzoneOptions: {
        url: "http://localhost:3001/api/file/upload",
        maxFilesize: 10, // MB
        acceptedFiles: "image/*",
      }
    };
  },
  created() {
    const userId = localStorage.getItem('userId');
    this.fetchUser(userId);
  },
  methods: {
    saveUser() {
      axios.put(`http://localhost:3001/api/user/update`, this.user,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          window.history.go(-1);
        })
        .catch(error => {
          console.error('Error saving user:', error);
        });
    },
    fetchUser(userId) {
      axios.get(`http://localhost:3001/api/user/getById/${userId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          this.user = response.data;
          this.user.age = formatDateYYMMDD(this.user.age);
        })
        .catch(error => {
          console.error('Error fetching user:', error);
        });
    },
    getImageUrl(imageName) {
      return `http://localhost:3001/api/file/download/${imageName}`;
    },
    onDropSuccess(file) {
      console.log("Archivo subido:", file.upload.filename);
      this.user.image = file.upload.filename;
      localStorage.removeItem('cachedLogoImage');
      localStorage.setItem('userImg', file.upload.filename);
    }
  }
}
</script>

<style scoped>
.center {
  display: flex;
  justify-content: center;
}
.imagePicture {
  display: flex;
  justify-content: center;
  width: 200px;
  height: auto;
}
</style>