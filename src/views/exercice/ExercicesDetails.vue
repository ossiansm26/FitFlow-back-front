<template>
  <v-container>
    <BackBar />
    <h2 class="display-4 mb-4">Detalles del Ejercicio</h2>
    <v-card class="mb-4">
      <v-card-title>{{ exercise.exerciseName }}</v-card-title>
      <v-card-text>
        <v-img
          class="exercise-image"
          :src="getImageUrl(exercise.urlImage)"
          :alt="exercise.exerciseName"
          contain
        ></v-img>
        <p><strong>Duración:</strong> {{ exercise.duration }} minutos</p>
        <p><strong>Descripción:</strong> {{ exercise.description }}</p>

        <p><strong>Material Necesario:</strong></p>
        <v-list>
          <v-list-item v-for="(item, index) in exercise.material" :key="index">
            <v-list-item-content>
              <v-list-item-title>{{ item.materialName }}</v-list-item-title>
              <v-list-item-subtitle>
                Disponibilidad: {{ item.avalibilityStatus ? 'Disponible' : 'No Disponible' }}<br>
                Última Mantención: {{ new Date(item.lastMaintence).toLocaleDateString() }}
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-btn color="primary" @click="addMaterial">Añadir Material</v-btn>

        <p><strong>Grupos Musculares:</strong></p>
        <v-list>
          <v-list-item v-for="(group, index) in exercise.muscleGroup" :key="index">
            <v-list-item-avatar>
              <v-img
                :src="getImageUrl(group.muscleImage)"
                :alt="group.groupName"
                class="muscle-image"
              ></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>{{ group.groupName }}</v-list-item-title>
              <v-list-item-subtitle>
                Parte del Cuerpo: {{ group.bodyPart }}
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-btn color="primary" @click="addMuscleGroup">Añadir Grupo Muscular</v-btn>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BackBar from '@/components/navbar/BackBar.vue';
import axios from 'axios';
import Exercices from '@/models/Exercices.ts';
import router from '@/router';

export default {
  components: {
    BackBar
  },
  data() {
    return {
      exercise: new Exercices('', '', '', '', '', '', [], [], []),
      token: localStorage.getItem('token')
    };
  },
  mounted() {
    const exerciseId = localStorage.getItem('selectedExerciseId');
    this.fetchExerciseDetails(exerciseId);
  },
  methods: {
    fetchExerciseDetails(exerciseId) {
      axios.get(`http://localhost:3001/api/exercices/getExerciseById/${exerciseId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          console.log(response.data);
          this.exercise = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    getImageUrl(imageName) {
      return `http://localhost:3001/api/file/download/${imageName}`;
    },
    addMaterial() {
      localStorage.setItem('exerciseId', this.exercise.id);
      router.push({ name: 'material' });
    },
    addMuscleGroup() {
      localStorage.setItem('exerciseId', this.exercise.id);
      router.push({ name: 'muscleGroup' });
    }
  }
};
</script>

<style scoped>
.exercise-image {
  width: 100%;
  height: 300px;
  object-fit: contain;
  margin-bottom: 20px;
}
.muscle-image {
  width: 50px;
  height: 50px;
  object-fit: contain;
}
</style>
