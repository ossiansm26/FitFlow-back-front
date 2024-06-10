<template>
  <v-container>
    <BackBar />
    <v-form @submit.prevent="editCommunity">
      <v-text-field v-model="editedCommunity.name" label="Nombre"></v-text-field>
      <v-textarea v-model="editedCommunity.description" label="Descripción"></v-textarea>
      <v-btn type="submit" color="primary">Guardar cambios</v-btn>
    </v-form>
  </v-container>
</template>

<script>
import axios from 'axios';
import Community from '@/models/Community';
import BackBar from '@/components/navbar/BackBar.vue';

export default {
  components: {
    BackBar
  },
  data() {
    return {
      editedCommunity:new Community(),
      token: localStorage.getItem('token')
    }
  },
  mounted() {
    const communityId = localStorage.getItem('selectedCommunityId');
    this.fetchCommunity(communityId);
  },
  methods: {
    fetchCommunity(communityId) {
      axios.get(`http://localhost:3001/api/community/getCommunityById/${communityId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
            console.log('Comunidad cargada exitosamente:', response.data);
          this.editedCommunity = response.data;
        })
        .catch(error => {
          console.error('Error al cargar datos de la comunidad:', error);
        });
    },
    editCommunity() {
      axios.put(`http://localhost:3001/api/community/update/${this.editedCommunity.id}`, this.editedCommunity,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          console.log('Comunidad editada exitosamente:', response.data);
            this.$router.push({ name: 'community' });
        })
        .catch(error => {
          console.error('Error al editar comunidad:', error);
        });
    }
  }
};
</script>

<style scoped>

</style>
