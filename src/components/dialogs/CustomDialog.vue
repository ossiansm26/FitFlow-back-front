<template>
  <v-container>
    <v-dialog v-model="loading" persistent max-width="400">
      <v-card color="white" dark elevation="0">
        <v-card-text class="text-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
          <br />
          <div class="black--text">Cargando...</div>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-dialog v-model="showErrorDialog" max-width="400">
      <v-card color="white" dark elevation="0">
        <v-card-text class="text-center">
          <v-icon size="64" color="error">mdi-alert-circle</v-icon>
          <br />
          <div class="black--text">{{ errorText }}</div>
        </v-card-text>
        <v-card-actions class="justify-center">
          <v-btn color="error" @click="handleErrorOK">OK</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="showSuccessDialog" max-width="400">
      <v-card color="white" dark elevation="0">
        <v-card-text class="text-center">
          <v-icon size="64" color="success">mdi-check-circle</v-icon>
          <br />
          <div class="black--text">{{ successText }}</div>
        </v-card-text>
        <v-card-actions class="justify-center">
          <v-btn color="success" @click="handleSuccessOK">OK</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
export default {
  props: {
    loading: Boolean,
    errorText: String,
    successText: String,
    routeOnSuccess: String,
  },
  computed: {
    showErrorDialog() {
      return !!this.errorText;
    },
    showSuccessDialog() {
      return !!this.successText;
    }
  },
  methods: {
    handleSuccessOK() {
      this.$emit("close");
      if (this.routeOnSuccess) {
        this.$router.push(this.routeOnSuccess);
      }
    },
    handleErrorOK() {
      this.$emit("close");
    }
  }
};
</script>
