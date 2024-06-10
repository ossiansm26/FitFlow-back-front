<template>
  <v-container>
    <BackBar />
    <h2 class="display-3 mb-4">Rutinas</h2>
    <v-row>
      <v-col v-for="routine in routines" :key="routine.id" cols="12">
        <v-card class="mb-4">
          <v-card-title>{{ routine.description }}</v-card-title>
          <v-card-text>
            <p>Category: {{ routine.category }}</p>
            <p v-if="routine.status" class="text-success">Status: Active</p>
            <p v-else class="text-danger">Status: Inactive</p>
            <p>Coach's Comments: {{ routine.coachsComments }}</p>
            <p>Start Date: {{ formatDate(routine.start) }}</p>
            <p>End Date: {{ formatDate(routine.end) }}</p>
          </v-card-text>
          <v-card-actions>
            <v-btn icon @click="viewDetails(routine)">
              <v-icon color="purple darken-1">mdi-eye</v-icon>
            </v-btn>
            <v-btn icon @click="editRoutine(routine)">
              <v-icon color="blue darken-1">mdi-pencil</v-icon>
            </v-btn>
            <v-btn icon @click="deleteRoutine(routine.id)">
              <v-icon color="red darken-1">mdi-delete</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <div class="fixed-btn-group">
      <v-btn class="search-btn" color="blue" fab large @click="searchRoutine">
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <v-btn class="create-btn" color="success" fab large @click="createRoutine">
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import BackBar from '@/components/navbar/BackBar.vue';
import axios from 'axios';

export default {
  components: {
    BackBar,
  },
  data() {
    return {
      routines: [],
      id: null,
      token: localStorage.getItem('token')
    };
  },
  mounted() {
    this.id = localStorage.getItem('userId');
    console.log('User ID:', this.id);
    this.fetchRoutines();
  },
  methods: {
    async fetchRoutines() {
      try {
        const response = await axios.get(`http://localhost:3001/api/user/getUserRoutines/${this.id}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              });
        this.routines = response.data;
      } catch (error) {
        console.error('Error fetching routines:', error);
      }
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES');
    },
    editRoutine(routine) {
      localStorage.setItem('selectedRoutineId', routine.id);
      this.$router.push({ name: 'editRoutine' });
    },
    deleteRoutine(routineId) {
      axios.delete(`http://localhost:3001/api/user/${this.id}/removeRoutine/${routineId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          this.routines = this.routines.filter(routine => routine.id !== routineId);
        })
        .catch(error => {
          console.error('Error deleting routine:', error);
        }); 
    },
    createRoutine() {
      localStorage.setItem('userId', this.id);
      this.$router.push({ name: 'createRoutine' });
    },
    viewDetails(routine) {
      localStorage.setItem('selectedRoutineId', routine.id);
      this.$router.push({ name: 'routerDetails', params: { id: routine.id } });
    },
    searchRoutine() {
      this.$router.push({ name: 'searchRoutine' });
    }
  }
};
</script>

<style scoped>
.fixed-btn-group {
  position: fixed;
  bottom: 20px;
  right: 20px;
  display: flex;
  flex-direction: row;
}

.search-btn {
  margin-right: 10px;
}
.create-btn {
  margin-left: 10px;
}
</style>
