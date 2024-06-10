<template>
  <v-container>
  <BackBar/>
    <v-card class="achievement-details" v-if="achievement">
      <v-img :src="achievement.achievementURL" height="200px"></v-img>
      <v-card-title class="headline">{{ achievement.achievementName }}</v-card-title>
      <v-card-text>{{ achievement.achievementDescription }}</v-card-text>
    </v-card>
    <v-alert v-else>No se ha seleccionado ningún logro.</v-alert>
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
      token: localStorage.getItem('token')
    };
  },
  created() {
    const achievementId = localStorage.getItem('selectedAchievement');
    this.fetchAchievement(achievementId);
  },
  methods: {
    async fetchAchievement(achievementId) {
       axios.get(`http://localhost:3001/api/achievement/getAchievementById/${achievementId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          this.achievement = response.data;
        })
        .catch(error => {
          console.error('Error al obtener el logro:', error);
        });
    }
  }
};
</script>

<style scoped>
.achievement-details {
  margin: 20px;
}

.headline {
  font-size: 24px;
  font-weight: bold;
}
</style>
