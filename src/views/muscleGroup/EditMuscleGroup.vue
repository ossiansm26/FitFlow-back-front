<template>
  <v-container>
    <BackBar />
    <v-card>
      <v-card-title>Edit Muscle Group</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="saveMuscleGroup">
          <v-text-field v-model="editedMuscleGroup.groupName" label="Group Name" required></v-text-field>
          <label for="muscleImage">Muscle Image:</label>
          <div>
            <vue-dropzone
              @vdropzone-success="onDropSuccess"
              ref="dropzone"
              :id="'dropzoneId'"
              :options="dropzoneOptions"
            >
            </vue-dropzone>
            <label for="oldMuscleText" v-if="!newPhoto" > Old Muscle Image:
            <div>
            <img class="oldMuscleImage" :src="preview" alt="Muscle Preview">
            </div>
            </label>
          </div>
          <v-text-field v-model="editedMuscleGroup.bodyPart" label="Body Part" required></v-text-field>
          <v-btn type="submit" color="primary">Save</v-btn>
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>


<script>
import axios from 'axios';
import VueDropzone from "vue2-dropzone";
import "vue2-dropzone/dist/vue2Dropzone.min.css";
import MuscleGroup from '@/models/MuscleGroup';
import BackBar from '@/components/navbar/BackBar.vue';

export default {
  components: {
    VueDropzone,
    BackBar,
    token: localStorage.getItem('token')
  },
  data() {
    return {
      editedMuscleGroup: new MuscleGroup(0, '', '', ''),
      dropzoneOptions: {
        url: "http://localhost:3001/api/file/upload",
        maxFilesize: 10, // MB
        acceptedFiles: "image/*",
      },
      preview: '',
      newPhoto: false,
    };
  },
  mounted() {
    const muscleGroupId = localStorage.getItem('editGroupID');
    axios.get(`http://localhost:3001/api/muscleGroup/getById/${muscleGroupId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
      .then(response => {
        this.editedMuscleGroup = response.data;
        this.preview = `http://localhost:3001/api/file/download/${this.editedMuscleGroup.muscleImage}`;
        console.log('Muscle Group:', this.preview);
      })
      .catch(error => {
        console.error('Error fetching Muscle Group:', error);
      });
  },
  methods: {
    saveMuscleGroup() {
      axios.put('http://localhost:3001/api/muscleGroup/update', this.editedMuscleGroup,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          console.log('Muscle Group saved successfully:', response.data);
          this.$router.push({ name: 'muscleGroup' });
        })
        .catch(error => {
          console.error('Error saving Muscle Group:', error);
        });
    },
    onDropSuccess(file) {
        this.newPhoto = true;
        this.editedMuscleGroup.muscleImage = file.upload.filename;
    },
  },
};
</script>

<style scoped>
.oldMuscleImage {
  width: 300px;
  height: auto;
}
</style>
