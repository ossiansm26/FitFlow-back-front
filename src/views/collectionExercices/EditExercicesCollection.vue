<template>
  <v-container>
    <BackBar />
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center"
            >Edit Collection Exercises</v-card-title
          >
          <v-card-text>
            <v-form @submit.prevent="saveCollection">
              <v-text-field
                v-model="collection.collectionName"
                label="Collection Name"
                required
              ></v-text-field>
              <v-text-field
                v-model="collection.difficultyLevel"
                label="Difficulty Level"
                type="number"
                required
              ></v-text-field>
              <v-text-field
                v-model="collection.urlExplanatoryVideo"
                label="Explanatory Video URL"
                type="url"
              ></v-text-field>
              <v-btn type="submit" color="primary">Save</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
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
      collection: {},
      token: localStorage.getItem("token"),
    };
  },
  created() {
    this.collection.id = localStorage.getItem("selectedExerciseId");
    this.fetchCollection(this.collection.id);
  },
  methods: {
    saveCollection() {
      axios
        .put(
          `http://localhost:3001/api/collectionExercices/update/${this.collection.id}`,this.collection,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              },
        )
        .then(() => {
          this.$router.push({ name: "exercicesCollection" });
        })
        .catch((error) => {
          console.error("Error saving collection:", error);
        });
    },
    fetchCollection(collectionId) {
      axios
        .get(
          `http://localhost:3001/api/collectionExercices/getById/${collectionId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              }
        )
        .then((response) => {
          this.collection = response.data;
        })
        .catch((error) => {
          console.error("Error fetching collection:", error);
        });
    },
  },
};
</script>
  
  <style scoped>
.center {
  display: flex;
  justify-content: center;
}
</style>