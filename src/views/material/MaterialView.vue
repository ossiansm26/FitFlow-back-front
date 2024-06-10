<template>
  <v-container>
    <BackBar />
    <v-card>
      <v-card-title>
        Detalles de los Materiales
      </v-card-title>
      <v-card-text>
        <table class="table">
          <thead>
            <tr>
              <th></th>
              <th>ID</th>
              <th>Nombre</th>
              <th>Disponibilidad</th>
              <th>Último Mantenimiento</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="material in materials" :key="material.id">
              <td>
                <v-checkbox
                  v-model="selectedMaterials"
                  :value="material.id"
                  @change="checkboxChanged(material)"
                ></v-checkbox>
              </td>
              <td>{{ material.id }}</td>
              <td>{{ material.materialName }}</td>
              <td>
                <p v-if="material.avalibilityStatus" class="text-success">Disponible</p>
                <p v-else class="text-danger">No Disponible</p>
              </td>
              <td>{{ formattedlastMaintence(material) }}</td>
              <td>
                <v-row class="actions-row">
                  <v-col cols="12" sm="4">
                    <v-btn icon color="primary" @click="editMaterial(material)">
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                  </v-col>
                  <v-col cols="12" sm="4">
                    <v-btn icon color="error" @click="deleteMaterial(material.id)">
                      <v-icon>mdi-delete</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </td>
            </tr>
          </tbody>
        </table>
      </v-card-text>
    </v-card>
    <div class="floating-btn">
      <v-btn fab dark color="green" @click="addMaterial">
        <v-icon dark>mdi-plus</v-icon>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import axios from 'axios';
import { formatDateDDMMYYYY } from '@/utils/utils.ts';
import BackBar from '@/components/navbar/BackBar.vue';

export default {
  components: {
    BackBar
  },
  data() {
    return {
      token: localStorage.getItem("token"),
      materials: [],
      selectedMaterials: [],
      exerciceId: localStorage.getItem('exerciseId'),
    };
  },
  mounted() {
    axios.get('http://localhost:3001/api/material',{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
      .then(response => {
        this.materials = response.data;
        console.log('Materiales cargados:', this.materials);
      })
      .catch(error => {
        console.error('Error al cargar los detalles de los materiales:', error);
      });

    this.fetchSelectedMaterials();
  },
  methods: {
    formattedlastMaintence(material) {
      return material.lastMaintence ? formatDateDDMMYYYY(material.lastMaintence) : '';
    },
    editMaterial(material) {
      this.$router.push({ name: 'editMaterial', params: { material } });
      console.log('Editar material:', material);
    },
    deleteMaterial(materialId) {
      axios.delete(`http://localhost:3001/api/material/${materialId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          this.materials = this.materials.filter(material => material.id !== materialId);
        })
        .catch(error => {
          console.error('Error al borrar el material:', error);
        });
      console.log('Borrar material con ID:', materialId);
    },
    addMaterial() {
      this.$router.push({ name: 'crearMaterial' });
      console.log('Añadir nuevo material');
    },
    checkboxChanged(material) {
      const materialId = material.id;
      if (this.selectedMaterials.includes(materialId)) {
        this.addMaterialToCollection(materialId);
      } else {
        this.removeMaterialFromCollection(materialId);
      }
    },
    addMaterialToCollection(materialId) {
      axios.put(`http://localhost:3001/api/exercices/${this.exerciceId}/addMaterial/${materialId}`,{},{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log(`Material ${materialId} added to exercise ${this.exerciceId}`);
        })
        .catch((error) => {
          console.error(`Error adding material ${materialId} to exercise:`, error);
        });
    },
    removeMaterialFromCollection(materialId) {
      axios.delete(`http://localhost:3001/api/exercices/${this.exerciceId}/deleteMaterial/${materialId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          console.log(`Material ${materialId} removed from exercise ${this.exerciceId}`);
        })
        .catch((error) => {
          console.error(`Error removing material ${materialId} from exercise:`, error);
        });
    },
    fetchSelectedMaterials() {
      axios.get(`http://localhost:3001/api/exercices/${this.exerciceId}/materials`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(response => {
          this.selectedMaterials = response.data.map(material => material.id);
          console.log('Selected materials:', this.selectedMaterials);
        })
        .catch(error => {
          console.error('Error fetching selected materials:', error);
        });
    }
  }
};
</script>

<style scoped>
.floating-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
}
</style>
