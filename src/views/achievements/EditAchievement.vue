<template>
  <v-container>
    <BackBar />
    <v-card class="achievement-edit">
      <v-card-title>Editar Logro</v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field v-model="achievement.achievementName" label="Nombre del Logro" required></v-text-field>
          <v-textarea v-model="achievement.achievementDescription" label="Descripción del Logro" required></v-textarea>
          <v-text-field v-model="achievement.achievementURL" label="URL del Logro" required></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" @click="guardarCambios" :disabled="!valid">Guardar Cambios</v-btn>
      </v-card-actions>
    </v-card>
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
      achievement: new Achievement(),
      valid: false,
      token: localStorage.getItem('token')
    };
  },
  created() {
    const achievementId = localStorage.getItem('selectedAchievement');
    this.fetchAchievement(achievementId);
  },
  methods: {
    async fetchAchievement(achievementId) {
      try {
        const response = await axios.get(`http://localhost:3001/api/achievement/getAchievementById/${achievementId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              });
        this.achievement = response.data;
      } catch (error) {
        console.error('Error al obtener el logro:', error);
      }
    },
    async guardarCambios() {
      
    }
  }
};
</script>

<style scoped>
.achievement-edit {
  max-width: 400px;
  margin: auto;
}
</style>
