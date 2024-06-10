<template>
  <v-container>
    <BackBar />
    <h2 class="display-1">Achievements</h2>
    <v-row v-if="achievements && achievements.length > 0">
      <v-col v-for="achievement in achievements" :key="achievement.id" cols="12">
        <v-card>
          <v-card-title><strong>ID:</strong> {{ achievement.id }}</v-card-title>
          <v-card-text>
            <p><strong>Name:</strong> {{ achievement.achievementName }}</p>
            <p>
              <strong>Description:</strong>
              {{ achievement.achievementDescription }}
            </p>
            <p>
              <strong>URL:</strong>
              <a :href="achievement.achievementURL" target="_blank">{{
                achievement.achievementURL
              }}</a>
            </p>
          </v-card-text>
          <v-card-actions>
            <v-btn color="blue darken-1" @click="editAchievement(achievement)">
              <v-icon left>mdi-pencil</v-icon>
            </v-btn>
            <v-btn color="red darken-1" @click="deleteAchievement(achievement)">
              <v-icon left>mdi-delete</v-icon>
            </v-btn>
            <v-btn color="purple" @click="viewAchievement(achievement)">
              <v-icon>mdi-eye</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <v-row v-else>
      <v-col cols="12">
        <p>No achievements found.</p>
      </v-col>
    </v-row>
    <v-btn color="success" fab dark fixed right bottom @click="addAchievement">
      <v-icon>mdi-plus</v-icon>
    </v-btn>
  </v-container>
</template>

<script>
import BackBar from "@/components/navbar/BackBar.vue";
import axios from "axios";


export default {
  components: {
    BackBar,
  },
  data() {
    return {
      achievements: [],
      userId: null,
      token: localStorage.getItem('token')
    };
  },
  mounted() {
    const userId = localStorage.getItem("userId");
    this.userId = userId;
    this.fetchAchievements(userId);
  },
  methods: {
    fetchAchievements(userId) {
      axios.get(`http://localhost:3001/api/user/getAllAchievementsById/${userId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          this.achievements = response.data;
        })
        .catch((error) => {
          console.error("Error fetching achievements:", error);
        });
    },
    editAchievement(achievement) {
      localStorage.setItem("selectedAchievement", achievement.id);
      this.$router.push({ name: "editAchievement" });
    },
    deleteAchievement(achievement) {
      axios.delete(`http://localhost:3001/api/user/${this.userId}/removeAchievement/${achievement.id}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          this.achievements = this.achievements.filter(
            (a) => a.id !== achievement.id
          );
        })
        .catch((error) => {
          console.error("Error deleting achievement:", error);
        });
    },
    addAchievement() {
      this.$router.push({ name: "createAchievement" });
    },
    viewAchievement(achievement) {
      localStorage.setItem("selectedAchievement", achievement.id);
      this.$router.push({ name: "achievementDetails" });
    },
  },
};
</script>

<style scoped>
/* You can add custom styles here */
</style>