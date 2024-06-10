<template>
  <v-container>
    <BackBar />
    <h1 class="headline">Lista de Usuarios</h1>
    <v-data-table
      :headers="headers"
      :items="users"
      item-key="id"
      class="elevation-1"
    >
      <template v-slot:item="{ item }">
        <tr>
          <td>{{ item.id }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.lastNames }}</td>
          <td>{{ calculateAge(item.age) }}</td>
          <td>{{ item.email }}</td>
          <td>{{ formatDate(item.enrollamentDate) }}</td>
          <td>{{ item.phoneNumber }}</td>
          <td>{{ item.address }}</td>
          <td>{{ item.role }}</td>
          <td>
             <div class="button-group">
            <v-btn color="blue darken-1" @click="editUser(item)">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn color="red darken-1" @click="confirmDeleteUser(item)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
            <v-btn color="purple" @click="viewUserDetail(item)">
              <v-icon>mdi-eye</v-icon>
            </v-btn>
          </div>
          </td>
        </tr>
      </template>
    </v-data-table>

    <v-dialog v-model="dialogDeleteUser" max-width="500">
      <v-card>
        <v-card-title>¿Seguro que deseas borrar este usuario?</v-card-title>
        <v-card-actions>
          <v-btn color="red darken-1" text @click="dialogDeleteUser = false"
            >Cancelar</v-btn
          >
          <v-btn color="green darken-1" text @click="deleteUser(userToDelete)"
            >Confirmar</v-btn
          >
        </v-card-actions> 
      </v-card>
    </v-dialog>
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
      users: [],
      headers: [
        { text: "ID", value: "id" },
        { text: "Nombre", value: "name" },
        { text: "Apellidos", value: "lastNames" },
        { text: "Edad", value: "age" },
        { text: "Email", value: "email" },
        { text: "Fecha de Inscripción", value: "enrollmentDate" },
        { text: "Número de Teléfono", value: "phoneNumber" },
        { text: "Dirección", value: "address" },
        { text: "Rol", value: "role" },
        { text: "Acciones", value: "actions", sortable: false },
      ],
      dialogDeleteUser: false,
      userToDelete: null,
      userId: null,
      token: localStorage.getItem("token"),
    };
  },
  created() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers() {
      axios
        .get(`http://localhost:3001/api/user/getAllUser`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          console.log("Usuarios recuperados:", response.data);
          this.users = response.data;
        })
        .catch((error) => {
          console.error("Error al recuperar usuarios:", error);
        });
    },
    calculateAge(birthDate) {
      const today = new Date();
      const birthDateObj = new Date(birthDate);
      let age = today.getFullYear() - birthDateObj.getFullYear();
      const month = today.getMonth() - birthDateObj.getMonth();
      if (
        month < 0 ||
        (month === 0 && today.getDate() < birthDateObj.getDate())
      ) {
        age--;
      }
      return age;
    },
    formatDate(date) {
      const enrollmentDate = new Date(date);
      return enrollmentDate.toLocaleDateString(undefined, {
        year: "numeric",
        month: "long",
        day: "numeric",
      });
    },
    editUser(user) {
      localStorage.setItem("userId", user.id);
      this.$router.push({ name: "editarUsuario" });
    },
    viewUserDetail(user) {
      localStorage.setItem("selectedUserId", user.id);
      this.$router.push({ name: "UserDetails" });
    },
    confirmDeleteUser(user) {
      this.userToDelete = user;
      this.dialogDeleteUser = true;
    },
    deleteUser(user) {
      console.log("Borrando usuario con ID:", user.id);
      axios
        .delete(`http://localhost:3001/api/user/delete/${user.id}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          console.log("Usuario borrado:", response.data);
          this.fetchUsers();
        })
        .catch((error) => {
          console.error("Error al borrar usuario:", error);
        });

      this.dialogDeleteUser = false;
    },
  },
};
</script>

<style scoped>
.headline {
  color: #1976d2;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
}

.elevation-1 {
  box-shadow: 0px 2px 4px rgba(34, 50, 1, 0.1);
}
.button-group > * {
  margin-right: 2px;
}



</style>
