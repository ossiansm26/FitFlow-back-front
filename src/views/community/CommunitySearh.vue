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
                <v-btn icon @click="addToUser(community)" class="green">
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
      fetchData() {
        console.log('token:', this.token);
        axios.get(`http://localhost:3001/api/community/getAllCommunity`,{
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
      viewCommunity(communityId) {
        localStorage.setItem('selectedCommunityId', communityId);
        this.$router.push({ name: 'communityDetails' });
      },
      createCommunity() {
        this.$router.push({ name: 'communityCreate' });
      },
      getImageUrl(imageName) {
        return `http://localhost:3001/api/file/download/${imageName}`;
      }
    }
  }
  </script>
  
  <style scoped>
  .card {
    margin-bottom: 20px;
  }
  .create-btn {
    position: fixed;
    right: 20px;
    bottom: 20px;
  }
  .card-image {
    height: 200px;
    width: auto;
  }
  </style>
  
  