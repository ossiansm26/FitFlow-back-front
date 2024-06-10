<template>
  <v-container>
    <BackBar/>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center">Crear Nueva Rutina</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="crearRutina">
              <v-row>
                <v-col cols="12" sm="6">
                  <v-text-field label="Descripción" v-model="routine.description" required></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-text-field label="Categoría" v-model="routine.category" required></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-checkbox label="Estado" v-model="routine.status"></v-checkbox>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-text-field label="Inicio" type="date" v-model="routine.start" required></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-text-field label="Fin" type="date" v-model="routine.end" required></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-textarea label="Comentarios del Entrenador" v-model="routine.coachsComments"></v-textarea>
                </v-col>
                <v-col cols="12" class="text-center">
                  <v-btn type="submit" color="primary">Crear Rutina</v-btn>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import Routine from '@/models/routine';
import BackBar from '@/components/navbar/BackBar.vue';

export default {
  components: {
    BackBar
  },
  data() {
    return {
      routine: new Routine("", "", "", "", "", "", ""),
      userId: null,
      token: localStorage.getItem('token')
    };
  },
  created() {
    this.userId = localStorage.getItem('userId');
    console.log(this.userId);
  },
  methods: {
    crearRutina() {
      const nuevaRutina = this.routine;
      axios.post(`http://localhost:3001/api/user/createRoutine/${this.userId}`, nuevaRutina,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log('Rutina creada');
          this.$router.push({ name: 'routine' });
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>

<style scoped>
</style>
