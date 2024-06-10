<template>
  <v-container>
    <BackBar />
    <h2 class="display-4 mb-4">Detalles de la Rutina</h2>
    <v-card class="mb-4">
      <v-card-title>{{ routine.description }}</v-card-title>
      <v-card-text>
        <p>Categoría: {{ routine.category }}</p>
        <p v-if="routine.status" class="text-success">Estado: Activo</p>
        <p v-else class="text-danger">Estado: Inactivo</p>
        <p>Comentarios del Entrenador: {{ routine.coachsComments }}</p>
        <p>Fecha de Inicio: {{ formatDate(routine.start) }}</p>
        <p>Fecha de Fin: {{ formatDate(routine.end) }}</p>
        <v-btn @click="addExerciseCollection" color="primary">Añadir Colección de Ejercicios</v-btn>
        <p>Lista de Ejercicios:</p>
        <ul>
          <li v-for="(exercise, index) in routine.exercicesCollection" :key="index">
            {{ index + 1 }}
            <p><strong>Nombre de la Colección:</strong> {{ exercise.collectionName }}</p>
            <p><strong>Nivel de Dificultad:</strong> {{ exercise.difficultyLevel }}</p>
            <p><strong>Video Explicativo:</strong> <a :href="exercise.urlExplanatoryVideo" target="_blank">Ver Video</a></p>
            <p><strong>Ejercicios:</strong></p>
            <ul>
              <li v-for="(ex, exIndex) in exercise.exercices" :key="exIndex">{{ ex.exerciseName }}</li>
            </ul>
          </li>
        </ul>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BackBar from '@/components/navbar/BackBar.vue';
import axios from 'axios';
import Routine from '@/models/routine';

export default {
  components: {
    BackBar
  },
  data() {
    return {
      routine: new Routine('', '', '', '', '', '', []),
      token: localStorage.getItem('token')
    };
  },
  mounted() {
    const routineId = localStorage.getItem('selectedRoutineId');
    this.fetchRoutineDetails(routineId);
  },
  methods: {
    fetchRoutineDetails(routineId) {
      axios.get(`http://localhost:3001/api/routine/getRoutineById/${routineId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          console.log(response.data);
          this.routine = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES');
    },
    addExerciseCollection() {
      localStorage.setItem('selectedRoutineId', this.routine.id);
      this.$router.push('exercicesCollection');

    }
  }
};
</script>

<style scoped>
</style>
