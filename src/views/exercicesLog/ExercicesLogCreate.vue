<template>
  <v-container>
    <BackBar/>
    <v-card class="pa-5">
      <v-card-title>
        <h1>Crear Ejercicio</h1>
      </v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submitExercise">
          <v-autocomplete
            ref="autocomplete"
            v-model="selectedExercise"
            :items="exerciseNames"
            label="Nombre del Ejercicio"
            required
            clearable
            solo
            append-outer-icon="mdi-magnify"
            @input="filterExercises"
            item-value="exerciseName"
            item-text="exerciseName"
          >
            <template v-slot:append-outer>
              <v-icon class="large-icon">mdi-magnify</v-icon>
            </template>
            <template v-slot:item="data">
              <v-list-item @click="selectExercise(data.item)">
                <v-list-item-avatar>
                  <v-img :src="getImageUrl(data.item.urlImage)" v-if="data.item.urlImage"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>{{ data.item.exerciseName }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-autocomplete>

          <v-divider class="my-3"></v-divider>

          <v-date-picker v-model="selectedDate" label="Fecha de realización" required></v-date-picker>

          <v-divider class="my-3"></v-divider>

          <v-row v-for="(set, index) in sets" :key="index">
            <v-col cols="4">
              <v-text-field v-model.number="set.weight" label="Peso (kg)" type="number" required></v-text-field>
            </v-col>
            <v-col cols="4">
              <v-text-field v-model.number="set.reps" label="Repeticiones" type="number" required></v-text-field>
            </v-col>
            <v-col cols="4">
              <v-btn @click="removeSet(index)" color="red" icon>
                <v-icon large>mdi-delete</v-icon>
              </v-btn>
            </v-col>
          </v-row>

          <v-btn @click="addSet" color="primary" class="my-3">Agregar Set</v-btn>
          <v-btn type="submit" color="success">Guardar Ejercicio</v-btn>
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import axios from 'axios';
import router from '@/router';
import BackBar from '@/components/navbar/BackBar.vue';

export default {
  components: {
    BackBar
  },
  data() {
    return {
      selectedExercise: null,
      sets: [{ weight: 0, reps: 0 }],
      exercises: [],
      exerciseNames: [],
      selectedExerciseImage: '',
      selectedDate: new Date().toISOString().substr(0, 10),
      token: localStorage.getItem('token')
    };
  },
  methods: {
    addSet() {
      this.sets.push({ weight: 0, reps: 0 });
    },
    removeSet(index) {
      this.sets.splice(index, 1);
    },
    async fetchExercises() {
      try {
        const response = await axios.get('http://localhost:3001/api/exercices',{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              });
        this.exercises = response.data;
        this.exerciseNames = response.data.map(exercise => ({
          exerciseName: exercise.exerciseName,
          urlImage: exercise.urlImage,
          id: exercise.id
        }));
      } catch (error) {
        console.error('Error fetching exercises:', error);
      }
    },
    filterExercises(query) {
      if (!query) {
        this.fetchExercises();
        return;
      }
      const filteredExercises = this.exercises.filter(exercise =>
        exercise.exerciseName.toLowerCase().includes(query.toLowerCase())
      );
      this.exerciseNames = filteredExercises.map(exercise => ({
        exerciseName: exercise.exerciseName,
        urlImage: exercise.urlImage,
        id: exercise.id
      }));
    },
    selectExercise(exercise) {
      this.selectedExercise = exercise;
      this.selectedExerciseImage = exercise.urlImage;
      this.$refs.autocomplete.blur();
    },
    async submitExercise() {
      if (!this.selectedExercise) {
        console.error('Debe seleccionar un ejercicio.');
        return;
      }
      if (this.sets.length === 0) {
        console.error('Debe agregar al menos un set.');
        return;
      }

      const setsToSend = this.sets.map((set, index) => ({
        weight: set.weight,
        reps: set.reps,
      }));
      console.log('id', this.selectedExercise.id);
      const exerciceLog = {
        exercice: this.exercises.find(exercise => exercise.id === this.selectedExercise.id),
        date: this.selectedDate,
        sets: setsToSend
      };
      try {
        console.log('exerciceLog', exerciceLog);
        await axios.post('http://localhost:3001/api/exerciceLog/create', exerciceLog,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              });
        this.selectedExercise = null;
        this.sets = [{ weight: 0, reps: 0 }];
        this.selectedDate = new Date().toISOString().substr(0, 10);
        router.push('exercicesLogView');
        await this.fetchExercises();
      } catch (error) {
        console.error('Error submitting exercise:', error);
      }
    },
    getImageUrl(imageName) {
      return `http://localhost:3001/api/file/download/${imageName}`;
    }
  },
  mounted() {
    this.fetchExercises();
  }
};
</script>

<style scoped>
.large-icon {
  font-size: 32px;
}
</style>
