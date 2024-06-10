<template>
    <v-app>

        <v-container fluid >
            <v-row>
                <v-col cols="3" class="chat-list">
                    <v-text-field v-model="searchQuery" @input="searchUsers" label="Search user"
                        outlined></v-text-field>
                    <v-list dense>
                        <v-list-item v-for="user in filteredUsers" :key="user.id" @click="selectChatWithUser(user)"
                            :class="{ 'selected-chat': selectedChat && user.id === getOtherParticipant(selectedChat)?.id }"
                            ripple>
                            <v-list-item-avatar>
                                <v-avatar>
                                    <img :src="getUserAvatar(user)" alt="Avatar">
                                </v-avatar>
                            </v-list-item-avatar>
                            <v-list-item-content>
                                <v-list-item-title>{{ user.name }}</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                </v-col>
                <v-col cols="9" class="chat-window">
                    <div v-if="selectedChat" class="messages-container" ref="messageContainer">
                        <v-toolbar flat>
                            <v-toolbar-title>{{ getOtherParticipantName(selectedChat) }}</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-avatar>
                                <img :src="getOtherParticipantAvatar(selectedChat)" alt="Avatar">
                            </v-avatar>
                        </v-toolbar>
                        <v-container>
                            <v-row>
                                <v-col v-for="message in selectedChat.messages" :key="message.id" cols="12">
                                    <div :class="getMessageClass(message)">
                                        <v-card class="message-card mb-2" outlined>
                                            <v-card-text>
                                                <div class="timestamp">{{ formatTimestamp(message.timestamp) }}</div>
                                                <div class="sender-name">{{ message.sender.name }}</div>
                                                <div class="message-content">{{ message.content }}</div>
                                            </v-card-text>
                                        </v-card>
                                    </div>
                                </v-col>
                            </v-row>
                        </v-container>
                    </div>
                    <div v-else class="empty-chat">
                        <p>Select a chat to start messaging</p>
                    </div>
                    <v-text-field v-if="selectedChat" v-model="newMessage" label="Type your message" outlined
                        @keyup.enter="sendMessage" class="message-input">
                        <template v-slot:append-outer>
                            <v-btn icon @click="sendMessage">
                                <v-icon>mdi-send</v-icon>
                            </v-btn>
                        </template>
                    </v-text-field>
                </v-col>
            </v-row>
        </v-container>
    </v-app>
</template>

<script>
import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { format } from 'date-fns';
import BackBar from '@/components/navbar/BackBar.vue';

export default {
    components: {
        BackBar,
    },
    data() {
        return {
            chats: [],
            allUsers: [],
            selectedChat: null,
            userId: 0,
            stompClient: null,
            currentSubscription: null,
            newMessage: '',
            searchQuery: '',
            token: localStorage.getItem('token')
        };
    },
    computed: {
        filteredUsers() {
            if (this.searchQuery.trim() === '') {
                return this.chats.map(chat => this.getOtherParticipant(chat));
            } else {
                return this.allUsers.filter(user =>
                    user.name.toLowerCase().includes(this.searchQuery.toLowerCase()) && user.id !== parseInt(this.userId)
                );
            }
        }
    },
    methods: {
        selectChat(chat) {
            if (this.currentSubscription) {
                this.currentSubscription.unsubscribe();
            }
            this.selectedChat = chat;

            const url = 'http://localhost:3001/chat-socket';
            const socket = new SockJS(url);
            this.stompClient = Stomp.over(socket);
            Stomp.headers = {
                Authorization: `Bearer ${this.token}`,
            };

            this.stompClient.connect({ Authorization: `Bearer ${this.token}` }, () => {
                const chatId = this.selectedChat.id;
                this.currentSubscription = this.stompClient.subscribe(`/topic/chat/${chatId}`, message => {
                    console.log('Mensaje recibido:', JSON.parse(message.body));
                    this.selectedChat.messages.push(JSON.parse(message.body));
                    this.scrollToBottom();
                });
            });
            this.$nextTick(() => {
                this.scrollToBottom();
            });
        },
        getMessageClass(message) {
            const senderId = parseInt(message.sender.id);
            const currentUserId = parseInt(this.userId);
            return senderId === currentUserId ? 'sent-message' : 'received-message';
        },
        selectChatWithUser(user) {
            const existingChat = this.chats.find(chat => this.getOtherParticipant(chat)?.id === user.id);
            if (existingChat) {
                this.selectChat(existingChat);
            } else {
                this.createNewChat(user);
            }
        },
        createNewChat(user) {
            axios.post(`http://localhost:3001/api/chat/createChat/${this.userId}/${user.id}`, {
                headers: {
                    Authorization: `Bearer ${this.token}`,
                },
            })
                .then(response => {
                    console.log('Nuevo chat creado:', response.data);
                    this.chats.push(response.data);
                    this.selectChat(response.data);
                })
                .catch(error => {
                    console.error('Error creando nuevo chat!', error);
                });
        },
        scrollToBottom() {
            this.$nextTick(() => {
                const container = this.$refs.messageContainer;
                if (container) {
                    console.log('messageContainer is defined');
                    container.scrollTop = container.scrollHeight;
                } else {
                    console.log('messageContainer is undefined');
                }
            });
        },
        getOtherParticipant(chat) {
            return chat.participants?.find(p => p.id !== parseInt(this.userId));
        },
        getOtherParticipantName(chat) {
            const otherParticipant = this.getOtherParticipant(chat);
            return otherParticipant ? otherParticipant.name : 'Unknown';
        },
        getUserAvatar(user) {
            return `http://localhost:3001/api/file/download/${user.image}`;
        },
        getOtherParticipantAvatar(chat) {
            const otherParticipant = this.getOtherParticipant(chat);
            return otherParticipant ? this.getUserAvatar(otherParticipant) : '';
        },
        formatTimestamp(timestamp) {
            return format(new Date(timestamp), 'dd/MM/yyyy HH:mm');
        },
        sendMessage() {
            if (this.newMessage.trim() !== '') {
                const message = {
                    content: this.newMessage,
                    senderId: this.userId,
                    recipientId: this.selectedChat.participants.find(p => p.id !== parseInt(this.userId)).id,
                    timestamp: new Date().toISOString()
                };

                console.log('Enviando mensaje:', message);
                this.stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message));

                this.newMessage = '';
            }
        },
        searchUsers() {
            console.log('Searching users with query:', this.searchQuery);
        }
    },
    mounted() {
        this.userId = localStorage.getItem('userId');
        console.log('User ID:', this.userId);
        axios.get('http://localhost:3001/api/user/getAllUser', {
            headers: {
                Authorization: `Bearer ${this.token}`,
            },
        })
            .then(response => {
                console.log('All Users:', response.data);
                this.allUsers = response.data;
            })
            .catch(error => {
                console.error('Error fetching all users!', error);
            });

        axios.get(`http://localhost:3001/api/chat/getChats/${this.userId}`, {
            headers: {
                Authorization: `Bearer ${this.token}`,
            },
        })
            .then(response => {
                console.log('Chats:', response.data);
                this.chats = response.data;
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }
};
</script>

<style scoped>
.chat-list {
    height: 100vh;
    background-color: #f0f0f0;
    padding: 16px;
    border-right: 1px solid #e0e0e0;
}

.chat-window {
    height: 100vh;
    background: #ffffff;
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.messages-container {
    flex-grow: 1;
    overflow-y: auto;
    padding-right: 16px;
}

.messages-container::-webkit-scrollbar {
    width: 6px;
}

.messages-container::-webkit-scrollbar-thumb {
    background-color: #cccccc;
    border-radius: 4px;
}

.messages-container::-webkit-scrollbar-thumb:hover {
    background-color: #aaaaaa;
}

.messages-container::-webkit-scrollbar-track {
    background: #f5f5f5;
}

.sent-message {
    display: flex;
    justify-content: flex-end;
}

.received-message {
    display: flex;
    justify-content: flex-start;
}

.sent-message .message-card {
    background-color: #d1f8ff;
    color: #004d66;
    max-width: 90%;
    min-width: 300px;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.received-message .message-card {
    background-color: #fff5cc;
    color: #665500;
    max-width: 90%;
    min-width: 300px;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.sender-name {
    font-weight: bold;
    margin-bottom: 5px;
    font-size: 0.9em;
}

.message-content {
    margin-bottom: 5px;
}

.timestamp {
    font-size: 0.8em;
    color: #757575;
    text-align: right;
    float: right;
}

.selected-chat {
    background-color: #eeeeee;
    border-radius: 8px;
    padding: 8px;
}

.empty-chat {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    color: #9e9e9e;
    font-size: 1.2em;
    background: #f7f7f7;
    border-radius: 8px;
}

.message-input {
    position: sticky;
    display: flex;
    align-items: end;

}

</style>
