<template>
  <v-container>
    <BackBar />
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-card-title>{{ muscleGroup.groupName }} Details</v-card-title>
          <v-img :src="muscleGroup.muscleImage" :alt="muscleGroup.groupName" class="muscleGroupImage"></v-img>
          <v-card-text>
            <p><strong>Body Part:</strong> {{ muscleGroup.bodyPart }}</p>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import MuscleGroup from '@/models/MuscleGroup';
import BackBar from '@/components/navbar/BackBar.vue';

export default {
  components: {
    BackBar
  },
  data() {
    return {
      muscleGroup: new MuscleGroup(0, '', '', ''),
      token: localStorage.getItem('token')
    };
  },
  mounted() {
   const muscleGroupId = localStorage.getItem('selectedGroupId');
    this.fetchMuscleGroup(muscleGroupId);
  },
  methods: {
    fetchMuscleGroup(muscleGroupId) {
      axios.get(`http://localhost:3001/api/muscleGroup/getById/${muscleGroupId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          this.muscleGroup = response.data;
          this.muscleGroup.muscleImage=`http://localhost:3001/api/file/download/${response.data.muscleImage}`;
    
        })
        .catch(error => {
          console.error('Error fetching muscle group:', error);
        });
    }
  }
};
</script>

<style scoped>
.muscleGroupImage {
  width: 300px;
  height: auto;
}
</style>
