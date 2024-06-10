import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import UsersView from '@/views/user/UsersView.vue'
import UserRegister from '@/views/user/UserRegister.vue'
import EditUser from '../views/user/EditUser.vue'
import MaterialView from '@/views/material/MaterialView.vue'
import EditMaterial from '@/views/material/EditMaterial.vue'
import CreateMaterial from '@/views/material/CreateMaterial.vue'
import MuscleGroupView from '@/views/muscleGroup/MuscleGroupView.vue'
import CreateMuscleGroup from '@/views/muscleGroup/CreateMuscleGroup.vue'
import ExercicesView from '@/views/exercice/ExercicesView.vue'
import ExercicesCollectionView from '@/views/collectionExercices/ExercicesCollectionView.vue'
import RoutinesView from '@/views/routines/RoutinesView.vue'
import UserDetails from '@/views/user/UserDetails.vue'
import PostView from '@/views/post/PostView.vue'
import CommunityView from '@/views/community/CommunityView.vue'
import AchievementView from '@/views/achievements/AchievementView.vue'
import UserLogin from '@/views/user/UserLogin.vue'
import CreateRoutines from '@/views/routines/CreateRoutines.vue'
import RoutinesDetails from '@/views/routines/RoutinesDetails.vue'
import EditRoutine from '@/views/routines/EditRoutine.vue'
import EditCommunity from '@/views/community/EditCommunity.vue'
import CommunityDetails from '@/views/community/CommunityDetails.vue'
import CreateCommunity from '@/views/community/CreateCommunity.vue'
import AchievementDetails from '@/views/achievements/AchievementDetails.vue'
import CreateAchievement from '@/views/achievements/CreateAchievement.vue'
import EditAchievement from '@/views/achievements/EditAchievement.vue'
import EditMuscleGroup from '@/views/muscleGroup/EditMuscleGroup.vue'
import MuscleGroupDetails from '@/views/muscleGroup/MuscleGroupDetails.vue'
import CreateExercice from '@/views/exercice/CreateExercice.vue'
import ExercicesDetails from '@/views/exercice/ExercicesDetails.vue'
import CreateExercicesCollection from '@/views/collectionExercices/CreateExercicesCollection.vue'
import ExercicesLogView from '@/views/exercicesLog/ExercicesLogView.vue'
import ExerciceEdit from '@/views/exercice/ExerciceEdit.vue'
import ExercicesLogCreate from '@/views/exercicesLog/ExercicesLogCreate.vue'
import EditExercicesCollection from '@/views/collectionExercices/EditExercicesCollection.vue'
import RoutinesSearch from '@/views/routines/RoutinesSearch.vue'
import CommunitySearch from '@/views/community/CommunitySearh.vue'
import chat from '@/views/chats/Chat.vue'
import axios from 'axios'
Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    meta: { requiresAuth: true }
  },{
    path: '/',
    name: 'login',
    component: UserLogin
  },{
    path: '/ReguistrarUsuario',
    name: 'reguistrarUsuario',
    component: UserRegister
  },
  {
    path: '/Usuarios',
    name: 'usuarios',
    component: UsersView,
    meta: { requiresAuth: true }

  },
  {
    path: '/userDetails',
    name: 'UserDetails',
    component: UserDetails,
    meta: { requiresAuth: true }
  },
  {
    path: '/EditarUsuario',
    name: 'editarUsuario',
    component: EditUser,
    meta: { requiresAuth: true }
  },
  {
    path: '/Material',
    name: 'material',
    component: MaterialView,
    meta: { requiresAuth: true }
  },
  {
    path: '/EditMaterial',
    name: 'editMaterial',
    component: EditMaterial,
    meta: { requiresAuth: true }
  },
  {
    path: '/CrearMaterial',
    name: 'crearMaterial',
    component: CreateMaterial,
    meta: { requiresAuth: true }
  },
  {
    path: '/MuscleGroup',
    name: 'muscleGroup',
    component: MuscleGroupView,
    meta: { requiresAuth: true }
  },
  {
    path: '/EditRoutine',
    name: 'editRoutine',
    component: EditRoutine,
    meta: { requiresAuth: true }
  },
  {
    path: '/CreateMuscleGroup',
    name: 'createMuscleGroup',
    component: CreateMuscleGroup,
    meta: { requiresAuth: true }
  },
  {
    path: '/EditMuscleGroup',
    name: 'editMuscleGroup',
    component: EditMuscleGroup,
    meta: { requiresAuth: true }
  },
  {
    path: '/MuscleGroupDetails',
    name: 'muscleGroupDetails',
    component: MuscleGroupDetails,
    meta: { requiresAuth: true }
  },
  {
    path: '/Exercices',
    name: 'exercices',
    component: ExercicesView,
    meta: { requiresAuth: true }
  },
  {
    path: '/ExercicesDetails',
    name: 'exercicesDetails',
    component: ExercicesDetails,
    meta: { requiresAuth: true }
  },
  {
    path: '/ExerciceEdit',
    name: 'exerciceEdit',
    component: ExerciceEdit,
    meta: { requiresAuth: true }
  },
  {
    path: '/CreateExercice',
    name: 'createExercice',
    component: CreateExercice,
    meta: { requiresAuth: true }
  },
  {
    path: '/ExercicesCollection',
    name: 'exercicesCollection',
    component: ExercicesCollectionView,
    meta: { requiresAuth: true }
  },
  {
    path: '/EditExercicesCollection',
    name: 'editExercicesCollection',
    component: EditExercicesCollection,
    meta: { requiresAuth: true }
  },
  {
    path: '/CreateExercicesCollection',
    name: 'createExercicesCollection',
    component: CreateExercicesCollection,
    meta: { requiresAuth: true }
  },
  {
    path: '/Routine',
    name: 'routine',
    component: RoutinesView,
    meta: { requiresAuth: true }
  },{
    path: '/RouterDetails',
    name: 'routerDetails',
    component: RoutinesDetails,
    meta: { requiresAuth: true }
  },
  {
    path: '/SearchRoutine',
    name: 'searchRoutine',
    component: RoutinesSearch,
    meta: { requiresAuth: true }
  },
  {
    path: '/CreateRoutine',
    name: 'createRoutine',
    component: CreateRoutines,
    meta: { requiresAuth: true }
  },
  {
    path: '/Post',
    name: 'post',
    component: PostView,
    meta: { requiresAuth: true }
  },
  {
    path: '/Community',
    name: 'community',
    component: CommunityView,
    meta: { requiresAuth: true }
  },
  {
    path: '/CommunitySearch',
    name: 'communitySearch',
    component: CommunitySearch,
    meta: { requiresAuth: true }
  },
  {
    path: '/EditCommunity',
    name: 'editCommunity',
    component: EditCommunity,
    meta: { requiresAuth: true }
  },
  {
    path: '/CommunityDetails',
    name: 'communityDetails',
    component: CommunityDetails,
    meta: { requiresAuth: true }
  },
  {
    path: '/CommunityCreate',
    name: 'communityCreate',
    component: CreateCommunity,
    meta: { requiresAuth: true }
  },
  {
    path: '/Achievement',
    name: 'achievement',
    component: AchievementView,
    meta: { requiresAuth: true }
  },
  {
    path: '/AchievementDetails',
    name: 'achievementDetails',
    component: AchievementDetails,
    meta: { requiresAuth: true }
  },
  {
    path: '/CreateAchievement',
    name: 'createAchievement',
    component: CreateAchievement,
    meta: { requiresAuth: true }
  },
  {
    path: '/EditAchievement',
    name: 'editAchievement',
    component: EditAchievement,
    meta: { requiresAuth: true }
  },
  {
    path: '/ExercicesLogView',
    name: 'exercicesLogView',
    component: ExercicesLogView,
    meta: { requiresAuth: true }
  },
  {
    path: '/ExercicesLogCreate',
    name: 'exercicesLogCreate',
    component: ExercicesLogCreate,
    meta: { requiresAuth: true }
  },
  {
    path: '/Chats',
    name: 'chats',
    component: chat,
    meta: { requiresAuth: true }
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach(async (to, from, next) => {
  if (to.path === from.path && to.path !== '/') {
    next(false);
  } else if (to.matched.some(record => record.meta.requiresAuth)) {
    const token = localStorage.getItem('token');

    if (!token) {
      next({ name: 'login' });
    } else {
      try {
        await axios.get(`http://localhost:3001/api/auth/verifyToken`, { params: { token } });
        next();
      } catch {
        localStorage.removeItem('token'); 
        next({ name: 'login' });
      }
    }
  } else {
    next();
  }
});

export default router;
