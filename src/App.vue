<template>
  <v-app >
    <v-main class="fondo">
      <router-view/>
    </v-main>
  </v-app>
</template>
 
<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
  name: 'App',

  data: () => ({
  })
});
</script>
<style scoped>
.fondo {
  background: linear-gradient(to bottom , rgb(248, 248, 248), rgb(228, 227, 227));
}
</style>