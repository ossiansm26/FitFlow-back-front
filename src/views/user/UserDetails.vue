<template>
  <v-container>
    <BackBar/>
    <v-card>
      <v-card-title>Detalles del Usuario</v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="12" sm="6">
            <strong>Nombre:</strong> {{ user.name }}
          </v-col>
          <v-col cols="12" sm="6">
            <strong>Apellidos:</strong> {{ user.lastNames }}
          </v-col>
          <v-col cols="12" sm="6">
            <strong>Edad:</strong> {{ user.age }}
          </v-col>
          <v-col cols="12" sm="6">
            <strong>Email:</strong> {{ user.email }}
          </v-col>
          <v-col cols="12" sm="6">
            <strong>Número de Teléfono:</strong> {{ user.phoneNumber }}
          </v-col>
          <v-col cols="12" sm="6">
            <strong>Dirección:</strong> {{ user.address }}
          </v-col>
          <v-col cols="12" sm="6">
            <strong>Rol:</strong> {{ user.rol }}
          </v-col>
          <v-col cols="12" sm="6">
            <strong>Especialidad:</strong> {{ user.speciality }}
          </v-col>
          <v-col cols="12" sm="6" class="userProfile">
           <strong>Foto de perfil:</strong> 
            <img class="userProfileImage" :src="getImageUrl(user.image)" alt="User profile">
          </v-col>
          <div v-if="user.routinesCreated.length > 0">
            <v-col cols="12">
              <ul>
                <strong>Rutinas Creadas:</strong>
                <li v-for="routine in user.routinesCreated" :key="routine.id">
                  {{ routine.description }}
                </li>
              </ul>
            </v-col>
          </div>
          <div v-if="user.routinesAssociated.length > 0">
            <v-col cols="12">
              <ul>
                <strong>Rutinas Asociadas:</strong>
                <li v-for="routine in user.routinesAssociated" :key="routine.id">
                  {{ routine.description }}
                </li>
              </ul>
            </v-col>
          </div>
          <v-col cols="12" v-if="user.communityCreated.length > 0">
            <strong>Comunidades Creadas:</strong>
            <ul>
              <li v-for="community in user.communityCreated" :key="community.id">
                {{ community.name }}
              </li>
            </ul>
          </v-col>
          <v-col cols="12" v-if="user.communityAssociated.length > 0">
            <strong>Comunidades Asociadas:</strong>
            <ul>
              <li v-for="community in user.communityAssociated" :key="community.id">
                {{ community.name }}
              </li>
            </ul>
          </v-col>
          <v-col cols="12" v-if="user.achievement.length > 0">
            <strong>Logros:</strong>
            <ul>
              <li v-for="achievement in user.achievement" :key="achievement.id">
                {{ achievement.achievementName }}
              </li>
            </ul>
          </v-col>
          <v-col cols="12" v-if="user.post.length > 0">
            <strong>Posts Creados:</strong>
            <ul>
              <li v-for="post in user.post" :key="post.id">
                {{ post.title }}
              </li>
            </ul>
          </v-col>
          <v-col cols="12" v-if="user.comment.length > 0">
            <strong>Comentarios:</strong>
            <ul>
              <li v-for="comment in user.comment" :key="comment.id">
                {{ comment.content }}
              </li>
            </ul>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import BackBar from '@/components/navbar/BackBar.vue';
import User from '@/models/User';
import axios from 'axios';
import { calculateAge } from '@/utils/utils.ts';

export default {
  name: 'UserDetails',
  components: {
    BackBar,
  },
  data() {
    return {
      user: new User('', '', new Date(), '', '', new Date(), '', '', 'User', '', ''),
      token: localStorage.getItem('token'),
    };
  },
  mounted() {
    const ID = localStorage.getItem('userId');
    this.fetchUser(ID);
  },
  methods: {
    fetchUser(ID) {
      axios.get(`http://localhost:3001/api/user/getById/${ID}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          this.user = response.data;
          console.log('Usuario recuperado:', this.user);
          this.user.age = calculateAge(this.user.age);
        })
        .catch(error => {
          console.error('Error al recuperar usuario:', error);
        });
    },
    getImageUrl(imageName) {
      return `http://localhost:3001/api/file/download/${imageName}`;
    },
  }
};
</script>

<style scoped>
.userProfile {
  display: flex;
  flex-direction: column;
  width: 200px;
  height: auto;

}
.userProfileImage {
  border: 3px solid #ccc;
    border-radius: 8px; 
    padding: 4px;
}
</style>
