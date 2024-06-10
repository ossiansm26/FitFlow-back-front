<template>
  <v-container>
    <BackBar />
    <v-card>
      <v-card-title>Registro de Material</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submitForm">
          <v-row>
            <v-col cols="12" sm="6">
              <v-text-field
                v-model="material.materialName"
                label="Nombre del Material"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6">
              <v-switch
                v-model="material.avalibilityStatus

"
                label="Estado de Disponibilidad"
              ></v-switch>
            </v-col>
            <v-col cols="12">
              <v-menu
                v-model="menu"
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="formattedLastMaintenance"
                    label="Último Mantenimiento"
                    readonly
                    v-on="on"
                    v-bind="attrs"
                    required
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="material.lastMaintence

"
                  @input="updateLastMaintenance"
                  scrollable
                ></v-date-picker>
              </v-menu>
            </v-col>
          </v-row>
          <v-btn type="submit" color="primary">Registrar Material</v-btn>
        </v-form>
      </v-card-text>
    </v-card>

    <CustomDialog
      :loading="loading"
      :error-text="errorText"
      :success-text="successText"
      :route-on-success="routeOnSuccess"
      @close="handleDialogClose"
    ></CustomDialog>
  </v-container>
</template>

<script>
import axios from "axios";
import Material from "@/models/Material";
import BackBar from "@/components/navbar/BackBar.vue";
import CustomDialog from "@/components/dialogs/CustomDialog.vue";

export default {
  components: {
    BackBar,
    CustomDialog
  },
  data() {
    return {
      material: new Material(
        null,
        "",
        false,
        new Date().toISOString().substr(0, 10)
      ),
      menu: false,
      loading: false,
      successText: "",
      errorText: "",
      routeOnSuccess: "",
      token: localStorage.getItem("token")
    };
  },
  computed: {
    formattedLastMaintenance() {
      return this.material.lastMaintence


        ? new Date(this.material.lastMaintence

).toLocaleDateString()
        : "";
    }
  },
  methods: {
    updateLastMaintenance() {
      this.menu = false;
      this.material.lastMaintence

 = this.material.lastMaintence

;
    },
    async submitForm() {
      this.loading = true;
      try {
        console.log("Registrando material:", this.material);
        const response = await axios.post(
          "http://localhost:3001/api/material",
          this.material,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              });
        console.log("Material registrado:", response.data);
        this.successText = "¡Material registrado con éxito!";
        this.routeOnSuccess = "/Material";
      } catch (error) {
        console.error("Error al registrar material:", error);
        this.errorText = "Error al registrar el material. Por favor, inténtalo de nuevo.";
      } finally {
        this.loading = false;
      }
    },
    handleDialogClose() {
      this.successText = "";
      this.errorText = "";
    }
  }
};
</script>
