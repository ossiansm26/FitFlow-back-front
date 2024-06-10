<template class="">
  <v-container fluid class="text-center fondo" >
    <v-row justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card>
          <v-card-title>Inicio de Sesión</v-card-title>
          <v-card-text>
            <v-form ref="form" v-model="valid">
              <v-text-field
                v-model="user.email"
                label="Usuario"
                required
              ></v-text-field>
              <v-text-field
                v-model="user.password"
                label="Contraseña"
                type="password"
                required
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="login" :disabled="!valid">Iniciar Sesión</v-btn>
            <v-btn color="success" @click="goToRegistration">Registrarse</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col cols="12" sm="4" md="4">
        <div class="lorem-ipsum">
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et odio sit amet ex sagittis fermentum. Maecenas nec nisl in purus laoreet consequat. Suspendisse potenti. Integer in quam felis. Vestibulum id consequat metus. Vivamus et mi nec leo fringilla pretium ac a mauris.</p>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  data() {
    return {
      user: {
        email: "",
        password: "",
      },
      valid: true,
    };
  },
  created() {
    localStorage.clear();
  },
  methods: {
    login() {
      axios
        .post("http://localhost:3001/api/user/login", this.user)
        .then((response) => {
          const token = response.data.token;
          console.log("Token:", token);
          localStorage.setItem("token", token);
          if (token) {
            axios
              .post("http://localhost:3001/api/user/userLogin", this.user, {
                headers: {
                  Authorization: `Bearer ${token}`, 
                },
              })
              .then((response) => {
                localStorage.setItem("userId", response.data.id);
                localStorage.setItem('userImg', response.data.image);
                console.log("Usuario autenticado:", response.data);
                router.push({ name: "home" });
              });
          }
        })
        .catch((error) => {
          console.error("Error al autenticar usuario:", error.response.data);
        });
    },
    goToRegistration() {
      router.push({ name: "reguistrarUsuario" });
    },
  },
};
</script>

<style scoped>
.text-center {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.lorem-ipsum {
  padding: 20px;
  background-color: white;
}

</style>