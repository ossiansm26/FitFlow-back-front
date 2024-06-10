<template>
  <v-container>
    <h2>Edit Routine</h2>
    <v-form @submit.prevent="saveChanges">
      <v-text-field
        v-model="editedRoutine.description"
        label="Description"
      ></v-text-field>
      <v-text-field
        v-model="editedRoutine.category"
        label="Category"
      ></v-text-field>
      <v-checkbox v-model="editedRoutine.status" label="Status"></v-checkbox>
      <v-text-field
        v-model="formattedStartDate"
        label="Start"
        type="datetime-local"
      ></v-text-field>
      <v-text-field
        v-model="formattedEndDate"
        label="End"
        type="datetime-local"
      ></v-text-field>
      <v-textarea
        v-model="editedRoutine.coachsComments"
        label="Coach's Comments"
      ></v-textarea>

      <v-btn color="primary" type="submit">Save Changes</v-btn>
    </v-form>
  </v-container>
</template>

<script>
import axios from "axios";
import Routine from "@/models/routine";
export default {
  data() {
    return {
      editedRoutine: new Routine("", "", "", "", "", "", []),
      token: localStorage.getItem("token"),
    };
  },
  computed: {
    formattedStartDate: {
      get() {
        return this.formatDate(this.editedRoutine.start);
      },
      set(value) {
        this.editedRoutine.start = new Date(value).toISOString();
      },
    },
    formattedEndDate: {
      get() {
        return this.formatDate(this.editedRoutine.end);
      },
      set(value) {
        this.editedRoutine.end = new Date(value).toISOString();
      },
    },
  },
  mounted() {
    const routineId = localStorage.getItem("selectedRoutineId");
    this.fetchRoutineDetails(routineId);
  },
  methods: {
    fetchRoutineDetails(routineId) {
      axios
        .get(`http://localhost:3001/api/routine/getRoutineById/${routineId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          console.log("Routine details:", response.data);
          this.editedRoutine = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    },
    saveChanges() {
      // Formatear las fechas antes de guardar
      this.editedRoutine.start = this.formatDate(this.editedRoutine.start);
      this.editedRoutine.end = this.formatDate(this.editedRoutine.end);

      axios
        .put(
          `http://localhost:3001/api/routine/update/${this.editedRoutine.id}`,
          this.editedRoutine,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              }
        )
        .then(() => {
          this.$router.push({ name: "routine" });
        })
        .catch((error) => {
          console.error("Error saving routine:", error);
        });
    },
    formatDate(dateString) {
      if (!dateString) return "";
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = `${date.getMonth() + 1}`.padStart(2, "0");
      const day = `${date.getDate()}`.padStart(2, "0");
      const hours = `${date.getHours()}`.padStart(2, "0");
      const minutes = `${date.getMinutes()}`.padStart(2, "0");
      return `${year}-${month}-${day}T${hours}:${minutes}`;
    },
  },
};
</script>

<style scoped>
</style>
