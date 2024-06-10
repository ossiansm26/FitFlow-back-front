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
              <v-btn icon @click="addToUser(routine)" class="green">
                <v-icon >mdi-plus</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
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
          const response = await axios.get(`http://localhost:3001/api/routine/getAllRoutine`,{
                  headers: {
                    Authorization: `Bearer ${this.token}`, 
                  },
                });
          this.routines = response.data;
        } catch (error) {
          console.error('Error fetching routines:', error);
        }
      },
      addToUser(routine) {
        axios.put(`http://localhost:3001/api/user/${this.id}/addRoutine/${routine.id}`,{}, {
                  headers: {
                    Authorization: `Bearer ${this.token}`, 
                  },
                })
          .then(response => {
            console.log('Rutina añadida:', response.data);
            this.$router.push({ name: 'routine' });
          })
          .catch(error => {
            console.error('Error al añadir rutina:', error);
          });
      },
      formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES');
    }
    }
  };
  </script>
  
  <style scoped>
.search-btn {
  margin-right: 10px;
}
create-btn {
  margin-left: 10px;
}
  </style>
  