<template>
  <v-container>
    <BackBar/>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline center">Create Collection</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="createCollection">
              <div class="form-group">
                <label for="collectionName">Collection Name</label>
                <input v-model="collection.collectionName" type="text" class="form-control" id="collectionName" required>
              </div>
              <div class="form-group">
                <label for="difficultyLevel">Difficulty Level</label>
                <input v-model.number="collection.difficultyLevel" type="number" class="form-control" id="difficultyLevel" required>
              </div>
              <div class="form-group">
                <label for="urlExplanatoryVideo">URL Explanatory Video</label>
                <input v-model="collection.urlExplanatoryVideo" type="url" class="form-control" id="urlExplanatoryVideo">
              </div>
              <button type="submit" class="btn btn-primary">Create</button>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
  
  <script>
import axios from "axios";
import CollectionExercices from "@/models/CollectionExercices";

export default {
  data() {
    return {
      collection: new CollectionExercices("", "", 0, 0, ""),
      token: localStorage.getItem("token"),
    };
  },
  methods: {
    async createCollection() {
      axios
        .post(
          "http://localhost:3001/api/collectionExercices/add",this.collection,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              }
        )
        .then((response) => {
          console.log("Collection created:", response.data);
          this.$router.push("exercicesCollection");
        })
        .catch((error) => {
          console.error("Error creating collection:", error);
        });
    },
  },
};
</script>
  