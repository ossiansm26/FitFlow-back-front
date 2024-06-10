<template>
  <v-app-bar app color="primary" dark>
      <div class="d-flex align-center" @click="goBack">
        <v-img alt="FitFLow Logo" class="shrink mr-2" contain
          src="https://raw.githubusercontent.com/ossiansm26/FitFlow/main/images/fitflow-home.png"
          transition="scale-transition" width="55" />
        <v-img alt="FitFlow name" class="shrink mt-1 hidden-sm-and-down" contain min-width="100"
          src="https://raw.githubusercontent.com/ossiansm26/FitFlow/main/images/logo-texto.png" width="200" />
      </div>
    <v-spacer></v-spacer>
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-avatar color="indigo" size="57" v-bind="attrs" v-on="on">
          <img :src="userImageUrl" alt="User Image" />
        </v-avatar>
      </template>
      <v-list>
        <v-list-item link>
          <v-list-item-title @click="viewProfile">Ver Perfil</v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-title @click="editProfile">Editar Perfil</v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-title @click="verLogros">Ver logros</v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-title @click="logout">Log Out</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {
      userImageUrl: "",
    };
  },
  mounted() {
    this.fetchUserImage();
  },
  methods: {
    goBack() {
      this.$router.push("home");
    },
    fetchUserImage() {
      const cachedLogoImage = localStorage.getItem("cachedLogoImage");
      if (cachedLogoImage) {
        this.userImageUrl = cachedLogoImage;
        console.log("User Image URL:", this.userImageUrl);
      } else {
        const nameImg = localStorage.getItem("userImg");
        if (nameImg) {
          this.userImageUrl = `http://localhost:3001/api/file/download/${nameImg}`;
          console.log("User Image URL:", this.userImageUrl);
          localStorage.setItem("cachedLogoImage", this.userImageUrl);
        } else {
          console.error("No image name found in localStorage");
        }
      }
    },
    logout() {
      console.log("Cerrando sesión...");
      this.$router.push("/");
    },
    viewProfile() {
      this.$router.push({ name: "UserDetails" });
    },
    editProfile() {
      this.$router.push({ name: "editarUsuario" });
    },
    verLogros() {
      this.$router.push({ name: "achievement" });
    },

  },
};
</script>

<style></style>