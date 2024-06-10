<template>
  <v-container>
    <BackBar />
    <v-card class="mb-4">
      <v-card-title>{{ community.name }}</v-card-title>
      <v-card-text>
        <div><strong>ID:</strong> {{ community.id }}</div>
        <div><strong>Descripción:</strong> {{ community.description }}</div>
      </v-card-text>
    </v-card>

    <v-btn @click="showForm = !showForm" color="primary" class="mb-4">
      Añadir nueva publicación
    </v-btn>

    <v-expand-transition>
      <v-card v-if="showForm" class="mb-4">
        <v-card-title>Añadir nueva publicación</v-card-title>
        <v-card-text>
          <v-form @submit.prevent="addPost">
            <v-text-field
              v-model="newPostTitle"
              label="Título de la publicación"
              required
            ></v-text-field>
            <v-textarea
              v-model="newPostContent"
              label="Contenido de la publicación"
              required
            ></v-textarea>
            <v-select
              v-model="newPostCategory"
              :items="categories"
              item-text="name"
              item-value="value"
              label="Categoría"
              required
            ></v-select>
            <v-btn type="submit" color="primary">Añadir Publicación</v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-expand-transition>

    <v-card v-if="community.post && community.post.length > 0">
      <v-card-title>Publicaciones</v-card-title>
      <v-list>
        <v-list-item
          v-for="(post, index) in community.post"
          :key="index"
          class="forum-post"
        >
          <v-list-item-content>
            <v-list-item-title class="post-title">{{ post.title }}</v-list-item-title>
            <v-list-item-subtitle class="post-content">
              {{ post.content }}
              <br />
              <strong>Categoria:</strong> {{ getCategoryName(post.category) }}
              <br />
              <strong>Fecha de Creación:</strong> {{ formatDate(post.creationDate) }}
              <br />
              <strong>Comentarios:</strong>
              <ul>
                <li v-for="(comment, commentIndex) in post.comment" :key="comment.id" class="comment-item">
                  <strong>{{ commentIndex + 1 }}</strong><br />
                  <strong>Contenido:</strong> {{ comment.content }}<br />
                  <strong>Fecha:</strong> {{ formatDate(comment.creationDate) }}
                </li>
              </ul>
              <v-btn @click="showReplyForm(index)" color="primary" class="mt-2">
                Responder
              </v-btn>
              <v-expand-transition>
                <v-card v-if="replyFormIndex === index" class="reply-form">
                  <v-card-text>
                    <v-form @submit.prevent="addReply(post.id)">
                      <v-textarea
                        v-model="newReplyContent"
                        label="Tu respuesta"
                        required
                      ></v-textarea>
                      <v-btn type="submit" color="primary">Enviar Respuesta</v-btn>
                    </v-form>
                  </v-card-text>
                </v-card>
              </v-expand-transition>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-card>
    <div v-else>
      <v-alert type="info">No hay publicaciones disponibles</v-alert>
    </div>
  </v-container>
</template>

<script>
import BackBar from "@/components/navbar/BackBar.vue";
import axios from "axios";
import { format } from "date-fns";
import {
  VContainer,
  VCard,
  VCardTitle,
  VCardText,
  VTextField,
  VTextarea,
  VBtn,
  VForm,
  VList,
  VListItem,
  VListItemContent,
  VListItemTitle,
  VListItemSubtitle,
  VAlert,
  VExpandTransition,
  VSelect,
} from "vuetify/lib";

export default {
  components: {
    VContainer,
    VCard,
    VCardTitle,
    VCardText,
    VTextField,
    VTextarea,
    VBtn,
    VForm,
    VList,
    VListItem,
    VListItemContent,
    VListItemTitle,
    VListItemSubtitle,
    VAlert,
    VExpandTransition,
    VSelect,
    BackBar,
  },
  data() {
    return {
      community: {},
      newPostTitle: "",
      newPostContent: "",
      newPostCategory: "",
      showForm: false,
      replyFormIndex: null,
      newReplyContent: "",
      categories: [
        { name: "Gimnasio", value: "gym" },
        { name: "Top", value: "top" },
        { name: "Eventos", value: "events" },
        { name: "Noticias", value: "news" },
        { name: "Discusión", value: "discussion" },
        { name: "Ayuda", value: "help" },
        { name: "Otros", value: "others" },
      ],
      userId: "",
      token: localStorage.getItem("token"),
    };
  },
  mounted() {
    const communityId = localStorage.getItem("selectedCommunityId");
    this.userId = localStorage.getItem("userId");
    this.fetchCommunityDetails(communityId);
    token: localStorage.getItem("token");
  },
  methods: {
    fetchCommunityDetails(communityId) {
      axios
        .get(`http://localhost:3001/api/community/getCommunityById/${communityId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then((response) => {
          this.community = response.data;
        })
        .catch((error) => {
          console.error("Error al cargar detalles de la comunidad:", error);
        });
    },
    addPost() {
      const newPost = {
        title: this.newPostTitle,
        content: this.newPostContent,
        creationDate: new Date().toISOString(),
        category: this.newPostCategory,
        comment: [],
      };
      axios
        .post(`http://localhost:3001/api/community/addPost/${this.community.id}/${this.userId}`, newPost,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          this.fetchCommunityDetails(this.community.id);
          this.newPostTitle = "";
          this.newPostContent = "";
          this.newPostCategory = "";
          this.showForm = false;
        })
        .catch((error) => {
          console.error("Error al añadir publicación:", error);
        });
    },
    showReplyForm(index) {
      this.replyFormIndex = this.replyFormIndex === index ? null : index;
    },
    addReply(postId) {
      const newReply = {
        content: this.newReplyContent,
        creationDate: new Date().toISOString(),
      };
      axios
        .post(`http://localhost:3001/api/community/addReply/${postId}/${this.userId}`, newReply,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
        .then(() => {
          this.fetchCommunityDetails(this.community.id);
          this.newReplyContent = "";
          this.replyFormIndex = null;
        })
        .catch((error) => {
          console.error("Error al añadir respuesta:", error);
        });
    },
    getCategoryName(categoryValue) {
      const category = this.categories.find((cat) => cat.value === categoryValue);
      return category ? category.name : categoryValue;
    },
    formatDate(dateString) {
      return format(new Date(dateString), 'dd/MM/yyyy HH:mm');
    },
  },
};
</script>

<style scoped>
.mb-4 {
  margin-bottom: 1rem;
}

.forum-post {
  border-bottom: 1px solid #e0e0e0;
  padding: 1rem 0;
  margin-left: 20px;
}

.post-title {
  font-weight: bold;
  font-size: 1.2rem;
}

.post-content {
  margin-top: 0.5rem;
}

.reply-form {
  margin-left: 200px;
}
</style>
