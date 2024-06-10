<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center">Registro de Usuario</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="registrarUsuario">
              <div class="form-group" :class="{ 'has-error': errors.name }">
                <label for="name">Nombre</label>
                <input v-model="user.name" type="text" class="form-control" id="name" required>
                <span v-if="errors.name" class="error-message">{{ errorMessages.name }}</span>
              </div>
              <div class="form-group" :class="{ 'has-error': errors.lastNames }"> 
                <label for="lastNames">Apellidos</label>
                <input v-model="user.lastNames" type="text" class="form-control" id="lastNames" required>
                <span v-if="errors.lastNames" class="error-message">{{ errorMessages.lastNames }}</span>
              </div>
              <div class="form-group" :class="{ 'has-error': errors.dateOfBirth }">
                <v-menu v-model="menu" :close-on-content-click="false" transition="scale-transition" offset-y min-width="auto">
                  <template v-slot:activator="{ on }">
                    <v-text-field v-model="user.dateOfBirth" label="Fecha de Nacimiento" readonly v-on="on" required></v-text-field>
                  </template>
                  <v-date-picker v-model="user.dateOfBirth" @input="menu = false" scrollable></v-date-picker>
                </v-menu>
                <span v-if="errors.dateOfBirth" class="error-message">{{ errorMessages.dateOfBirth }}</span>
              </div>
              <div class="form-group" :class="{ 'has-error': errors.email }">
                <label for="email">Email</label>
                <input v-model="user.email" type="email" class="form-control" id="email" required>
                <span v-if="errors.email" class="error-message">{{ errorMessages.email }}</span>
              </div>
              <div class="form-group" :class="{ 'has-error': errors.password }">
                <label for="password">Contraseña</label>
                <input v-model="user.password" type="password" class="form-control" id="password" required>
                <span v-if="errors.password" class="error-message">{{ errorMessages.password }}</span>
              </div>
              <div class="form-group" :class="{ 'has-error': errors.phoneNumber }">
                <label for="phoneNumber">Número de Teléfono</label>
                <input v-model="user.phoneNumber" type="text" class="form-control" id="phoneNumber" required>
                <span v-if="errors.phoneNumber" class="error-message">{{ errorMessages.phoneNumber }}</span>
              </div>
              <div class="form-group" :class="{ 'has-error': errors.address }">
                <label for="address">Dirección</label>
                <input v-model="user.address" type="text" class="form-control" id="address" required>
                <span v-if="errors.address" class="error-message">{{ errorMessages.address }}</span>
              </div>
              <div class="form-group" :class="{ 'has-error': errors.image }">
                <label for="image">Imagen</label>
                <vue-dropzone @vdropzone-success="onDropSuccess" ref="dropzone" :id="'dropzoneId'" :options="dropzoneOptions">
                  <div class="dropzone"></div>
                </vue-dropzone>
                <v-btn class="mt-2" color="error" v-if="user.image" @click="removeUserImage()">Eliminar Imagen</v-btn>
                <span v-if="errors.image" class="error-message">{{ errorMessages.image }}</span>
              </div>
              <button type="submit" class="btn btn-primary">Registrar</button>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <CustomDialog :loading="loading" :error-text="errorText" :success-text="successText" :route-on-success="routeOnSuccess" @close="handleDialogClose"></CustomDialog>
  </v-container>
</template>

<script>
import axios from 'axios';
import User from '@/models/User';
import VueDropzone from "vue2-dropzone";
import CustomDialog from '@/components/dialogs/CustomDialog.vue';

export default {
  components: {
    VueDropzone,
    CustomDialog,
  },
  
  data() {
    return {
      user: new User("", "", new Date(), "", "", new Date(), "", "", "User", "", ""),
      menu: false,
      dropzoneOptions: {
        url: "http://localhost:3001/api/file/upload",
        maxFilesize: 10, // MB
        acceptedFiles: "image/*",
      },
      loading: false,
      errorText: "",
      successText: "",
      routeOnSuccess: "",
      errors: {
        name: false,
        lastNames: false,
        dateOfBirth: false,
        email: false,
        password: false,
        phoneNumber: false,
        address: false,
        image: false,
      },
      errorMessages: {
        name: "Campo obligatorio",
        lastNames: "Campo obligatorio",
        dateOfBirth: "Campo obligatorio",
        email: "Campo obligatorio",
        password: "Campo obligatorio",
        phoneNumber: "Campo obligatorio",
        address: "Campo obligatorio",
        image: "Campo obligatorio",
      }
    };
  },
  methods: {
    async registrarUsuario() {
      this.resetErrors();
      let valid = true;
      if (!this.user.name) {
        this.errors.name = true;
        valid = false;
      }
      if (!this.user.lastNames) {
        this.errors.lastNames = true;
        valid = false;
      }
      if (!this.user.dateOfBirth) {
        this.errors.dateOfBirth = true;
        valid = false;
      }
      if (!this.user.email) {
        this.errors.email = true;
        valid = false;
      }
      if (!this.user.password) {
        this.errors.password = true;
        valid = false;
      }
      if (!this.user.phoneNumber) {
        this.errors.phoneNumber = true;
        valid = false;
      }
      if (!this.user.image) {
        this.errors.image = true;
        valid = false;
      }

      if (!valid) {
        this.errorText = 'Por favor complete los campos obligatorios.';
        return;
      }

      this.loading = true;
      try {
        this.user.registrationDate = new Date().toISOString().substr(0, 10);
        this.user.age = this.user.dateOfBirth;
        console.log('Registrando usuario:', this.user);
        const response = await axios.post('http://localhost:3001/api/user/createUser', this.user);
        console.log('Usuario registrado:', response.data);
        this.successText = "¡Usuario registrado con éxito!";
        this.routeOnSuccess = { name: 'login' };
      } catch (error) {
        console.error('Error al registrar usuario:', error);
        this.errorText = 'Error al registrar usuario. Por favor, inténtelo de nuevo.';
      } finally {
        this.loading = false;
      }
    },
    onDropSuccess(file) {
      console.log("Archivo subido:", file.upload.filename);
      this.user.image = file.upload.filename;
    },
    removeUserImage() {
      this.user.image = '';
      this.$refs.dropzone.removeAllFiles();
    },
    handleDialogClose() {
      this.successText = "";
      this.errorText = "";
    },
    getImageUrl(image) {
      return image ? `http://localhost:3001/uploads/${image}` : '';
    },
    resetErrors() {
      this.errors = {
        name: false,
        lastNames: false,
        dateOfBirth: false,
        email: false,
        password: false,
        phoneNumber: false,
        address: false,
        image: false,
      };
    }
  }
};
</script>

<style scoped>
.center {
  text-align: center;
}

.imagePicture {
  width: 100%;
  max-width: 200px;
  margin-bottom: 10px;
}

.v-menu__content {
  z-index: 1000 !important;
}

.has-error input,
.has-error .v-text-field,
.has-error .dropzone {
  border-color: red;
}

.error-message {
  color: red;
  font-size: 12px;
}
</style>
