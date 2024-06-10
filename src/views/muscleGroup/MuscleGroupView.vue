<template>
  <v-container>
    <BackBar/>
    <v-row>
      <v-col
        v-for="(group, index) in muscleGroups"
        :key="index"
        cols="12"
        md="6"
        lg="4"
      >
        <v-card class="card">
          <v-card-title>{{ group.groupName }}</v-card-title>
          <div class="card-image">
            <v-img
              class="card-image"
              :src="getImageUrl(group.muscleImage)"
              :alt="group.groupName"
              contain
            ></v-img>
          </div>
          <v-card-text>
            <p><strong>Body Part:</strong> {{ group.bodyPart }}</p>
            
          </v-card-text>
          <v-card-actions>
            <v-checkbox
              v-model="selectedGroups"
              :value="group.id"
              @change="checkboxChanged(group)"
            ></v-checkbox>
            <v-btn icon @click="editGroup(group)">
              <v-icon color="blue darken-1">mdi-pencil</v-icon>
            </v-btn>
            <v-btn icon @click="deleteGroup(group.id)">
              <v-icon color="red darken-1">mdi-delete</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <v-btn class="create-btn" color="success" fab large @click="createGroup">
      <v-icon>mdi-plus</v-icon>
    </v-btn>
  </v-container>
</template>


<script>
import axios from "axios";
import BackBar from "@/components/navbar/BackBar.vue";

export default {
  components: {
    BackBar,
  },
  data() {
    return {
      muscleGroups: [],
      selectedGroups: [],
      exerciseId: 0,
      token: localStorage.getItem('token')
    };
  },
  mounted() {
    this.exerciseId = localStorage.getItem('exerciseId');
    this.fetchData();
    this.fetchSelectedGroups();
  },
  methods: {
    async fetchData() {
      try {
        const response = await axios.get("http://localhost:3001/api/muscleGroup/getAll",{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              });
        this.muscleGroups = response.data;
        console.log("Grupos musculares:", this.muscleGroups);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
    getImageUrl(imageName) {
      return `http://localhost:3001/api/file/download/${imageName}`;
    },
    editGroup(group) {
      localStorage.setItem("editGroupID", group.id);
      this.$router.push({ name: "editMuscleGroup" });
    },
    deleteGroup(groupId) {
      axios.delete(`http://localhost:3001/api/muscleGroup/${groupId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          this.muscleGroups = this.muscleGroups.filter(
            (group) => group.id !== groupId
          );
        })
        .catch((error) => {
          console.error("Error al borrar el grupo:", error);
        });
      console.log("Borrar grupo con ID:", groupId);
    },
    createGroup() {
      this.$router.push({ name: "createMuscleGroup" });
      console.log("Crear nuevo grupo");
    },
    viewGroupDetail(group) {
      localStorage.setItem("selectedGroupId", group.id);
      this.$router.push({ name: "muscleGroupDetails" });
    },
    checkboxChanged(group) {
      const groupId = group.id;
      if (this.selectedGroups.includes(groupId)) {
        this.addGroupToExercise(groupId);
      } else {
        this.removeGroupFromExercise(groupId);
      }
    },
    addGroupToExercise(groupId) {
      axios.post(`http://localhost:3001/api/exercices/${this.exerciseId}/addMuscleGroup/${groupId}`,{},{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log(`Muscle group ${groupId} added to exercise ${this.exerciseId}`);
        })
        .catch((error) => {
          console.error(`Error adding muscle group ${groupId} to exercise:`, error);
        });
    },
    removeGroupFromExercise(groupId) {
      axios.delete(`http://localhost:3001/api/exercices/${this.exerciseId}/deleteMuscleGroup/${groupId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log(`Muscle group ${groupId} removed from exercise ${this.exerciseId}`);
        })
        .catch((error) => {
          console.error(`Error removing muscle group ${groupId} from exercise:`, error);
        });
    },
    fetchSelectedGroups() {
      axios.get(`http://localhost:3001/api/exercices/${this.exerciseId}/muscleGroups`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          this.selectedGroups = response.data.map(group => group.id);
          console.log('Selected muscle groups:', this.selectedGroups);
        })
        .catch(error => {
          console.error('Error fetching selected muscle groups:', error);
        });
    }
  }
};

</script>

<style scoped>
.card {
  height: 100%; 
}
.card-image {
  width: 100%; 
  height: 200px; 
}
.create-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
}
.card-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
}
.card-actions button {
  transition: background-color 0.3s, color 0.3s;
}
.card-actions button:hover {
  background-color: #eee;
  color: #333;
}
</style>
