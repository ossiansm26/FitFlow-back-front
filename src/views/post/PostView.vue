<template>
    <v-container>
        <v-row>
            <v-col v-for="post in posts" :key="post.id" cols="12" md="6">
                <v-card class="mb-4">
                    <v-card-title>{{ post.title }}</v-card-title>
                    <v-card-text>{{ post.content }}</v-card-text>
                    <v-card-actions>
                        <v-row>
                            <v-col cols="12">
                                <div>Fecha de Creación: {{ formatDate(post.creationDate) }}</div>
                            </v-col>
                            <v-col cols="12">
                                <div>Categoría: {{ post.category }}</div>
                            </v-col>
                            <v-col cols="12">
                                <v-btn color="primary" @click="likePost(post.id)">Me gusta</v-btn>
                            </v-col>
                        </v-row>
                    </v-card-actions>
                    <v-subheader>Comentarios</v-subheader>
                    <v-list>
                        <v-list-item v-for="comment in post.comments" :key="comment.id">
                            <v-list-item-content>{{ comment.text }}</v-list-item-content>
                        </v-list-item>
                    </v-list>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>
    
<script>
import axios from 'axios'

export default {
    data() {
        return {
            posts: [],
            token: localStorage.getItem('token')
        }
    },
    mounted() {
       const userId = localStorage.getItem('userId');
       this.fetchPosts(userId);
    },
    methods: {
        fetchPosts(userId) {
            axios.get(`http://localhost:3001/api/user/get/findPostById/${userId}`,{
                headers: {
                  Authorization: `Bearer ${this.token}`, 
                },
              })
            .then(response => {
                this.posts = response.data;
            })
            .catch(error => {
                console.error('Error fetching posts:', error)
            })
        },
        likePost(postId) {
            const post = this.posts.find(post => post.id === postId)
            if (post) {
                post.likes++
            }
        },
        formatDate(dateString) {
            const date = new Date(dateString)
            return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`
        }
    }
}
</script>

<style scoped>
  .post {
    margin-bottom: 20px;
  }
  </style>