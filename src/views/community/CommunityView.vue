<template>
  <v-container>
    <BackBar/>
    <v-row>
      <v-col v-for="community in communities" :key="community.id" cols="12" md="6">
        <v-card class="mb-4">
          <v-card-title>Comunidad: {{ community.name }}</v-card-title>
          <div >
            <v-img
              class="card-image"
              :src="getImageUrl(community.urlpicture)"
              :alt="community.name"
              contain
            ></v-img>
          </div>
          <v-card-text>{{ community.description }}</v-card-text>
        
          <v-card-actions>
            <v-btn color="red" @click="deleteCommunity(community.id)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
            <v-btn color="blue" @click="editCommunity(community.id)">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn color="purple" @click="viewCommunity(community.id)">
              <v-icon>mdi-eye</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <div class="fixed-btn-group">
      <v-btn class="search-btn" color="blue" fab large @click="searchCommunity">
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
    <v-btn class="create-btn" color="success" fab large @click="createCommunity">
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
    BackBar
  },
  data() {
    return {
      token: localStorage.getItem('token'),
      communities: [],
      userId: ''
    };
  },
  mounted() {
    this.userId = localStorage.getItem('userId');
    this.fetchData(this.userId);
  },
  methods: {
    fetchData(userId) {
      axios.get(`http://localhost:3001/api/user/getCommunity/${userId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          console.log('Comunidades:', response.data);
          this.communities = response.data;
        })
        .catch(error => {
          console.error('Error al obtener los datos:', error);
        });
    },
    deleteCommunity(communityId) {
      axios.delete(`http://localhost:3001/api/user/${this.userId}/removeCommunity/${communityId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          this.communities = this.communities.filter(c => c.id !== communityId);
        })
        .catch(error => {
          console.error('Error al eliminar la comunidad:', error);
        });
    },
    editCommunity(communityId) {
      localStorage.setItem('selectedCommunityId', communityId);
      this.$router.push({ name: 'editCommunity' });
    },
    viewCommunity(communityId) {
      localStorage.setItem('selectedCommunityId', communityId);
      this.$router.push({ name: 'communityDetails' });
    },
    createCommunity() {
      this.$router.push({ name: 'communityCreate' });
    },
    getImageUrl(imageName) {
      return `http://localhost:3001/api/file/download/${imageName}`;
    },
    searchCommunity() {
      this.$router.push({ name: 'communitySearch' });
    }
  }
}
</script>

<style scoped>
.card {
  margin-bottom: 20px;
}
.fixed-btn-group {
  position: fixed;
  bottom: 20px;
  right: 20px;
  display: flex;
  flex-direction: row;
}

.card-image {
  height: 200px;
  width: auto;
}
</style>

