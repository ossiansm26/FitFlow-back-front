<template>
  <v-container>
    <BackBar/>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center">Nuevo Logro</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="crearLogro">
              <div class="form-group">
                <v-text-field v-model="logro.achievementName" label="Nombre del Logro" required></v-text-field>
              </div>
              <div class="form-group">
                <v-textarea v-model="logro.achievementDescription" label="Descripción del Logro" required></v-textarea>
              </div>
              <div class="form-group">
                <v-text-field v-model="logro.achievementURL" label="URL del Logro" required></v-text-field>
              </div>
              <div class="form-group">
                <v-btn color="primary" type="submit">Crear Logro</v-btn>
              </div>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import Achievement from '@/models/Achievement';
import BackBar from '@/components/navbar/BackBar.vue';

export default {
  components: {
    BackBar
  },
  data() {
    return {
      logro: new Achievement('', '', ''),
      token: localStorage.getItem('token')
    };
  },
  methods: {
    async crearLogro() {
      try {
        await axios.post('http://localhost:3001/api/achievement/add', this.logro,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              });
        console.log('Logro creado:', this.logro);
        this.$router.push('achievement');
      } catch (error) {
        console.error('Error al crear el logro:', error);
      }
    }
  }
};
</script>

<style scoped>

</style>
