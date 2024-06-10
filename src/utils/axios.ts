import User from "@/models/User"
import axios from "axios"
const baseURL = 'http://localhost:3001/api/'

const api = axios.create({
    baseURL,
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}` // Incluye el token en el encabezado de autorización
    },
  })
  const apiAuth = axios.create({
    baseURL,
    headers: {
      'Content-Type': 'application/json'
    },
  })
  const apiMultipart = axios.create({
    baseURL,
    headers: {
      'Content-Type': 'multipart/form-data',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    },
  })
  export const getTokens = async (user:User) => {
      apiAuth.post("http://localhost:3001/api/user/login", user)
      .then((response) => {
        const token = response.data.token;
        localStorage.setItem("token", token);
      });
    }
  export const singUp = async (user:User) => {
    api.post("http://localhost:3001/api/user", user).then((response) => {
      localStorage.setItem("userId", response.data.id);
      console.log("Usuario autenticado:", response.data);
    });
  }
  
