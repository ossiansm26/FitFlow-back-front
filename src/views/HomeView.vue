<template>
  <v-container>
    <HomeBar />
    <v-layout>
      <v-flex xs12 sm6 md4>
        <v-card class="mx-4" @click="verLogros">
          <v-img
            src="https://raw.githubusercontent.com/ossiansm26/FitFlowSpringBoot/main/images/mujerPesas.png"
            aspect-ratio="1"
          ></v-img>
          <v-card-title class="headline text">Chats</v-card-title>
        </v-card>
      </v-flex>
      <v-flex xs12 sm6 md4>
        <v-card class="mx-4" @click="verComunidad">
          <v-img
            src="https://raw.githubusercontent.com/ossiansm26/FitFlowSpringBoot/main/images/comunidades.png"
            aspect-ratio="1"
          ></v-img>
          <v-card-title class="headline text">Comunidad</v-card-title>
        </v-card>
      </v-flex>
      <v-flex xs12 sm6 md4>
        <v-card class="mx-4" @click="verRutinas">
          <v-img
            src="https://raw.githubusercontent.com/ossiansm26/FitFlowSpringBoot/main/images/routines.png"
            aspect-ratio="1"
          ></v-img>
          <v-card-title class="headline text">Rutinas</v-card-title>
        </v-card>
      </v-flex>
      <v-flex xs12 sm6 md4>
        <v-card class="mx-4" @click="anadirRegistro">
          <v-img
            src="https://raw.githubusercontent.com/ossiansm26/FitFlowSpringBoot/main/images/ejercicios.png"
            aspect-ratio="1"
          ></v-img>
          <v-card-title class="headline text">Ejercicios</v-card-title>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import HomeBar from '@/components/navbar/HomeBar.vue';

export default {
  components: {
    HomeBar,
  },
  created() {
    const userId = localStorage.getItem("userId");
    localStorage.setItem("userId", userId);
  },
  methods: {
    verUsuarios() {
      this.$router.push({ name: "UserDetails" });
    },
    verEjercicios() {
      this.$router.push({ name: "exercices" });
    },
    verRutinas() {
      this.$router.push({ name: "routine" });
    },
    verComunidad() {
      this.$router.push({ name: "community" });
    },
    verLogros() {
      this.$router.push({ name: "chats" });
    },
    anadirRegistro() {
      this.$router.push({ name: "exercicesLogView" });
    },
  },
};
</script>

<style scoped>
.text {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
