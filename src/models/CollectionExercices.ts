export default class collectionExercices {
  id: number;
  collectionName: string;
  difficultyLevel: number;
  totalExercises: number;
  urlExplanatoryVideo: string;
  exercises: any[];

  constructor(
    id: number,
    collectionName: string,
    difficultyLevel: number,
    totalExercises: number,
    urlExplanatoryVideo: string,
    exercises: any[]
  ) {
    this.id = id;
    this.collectionName = collectionName;
    this.difficultyLevel = difficultyLevel;
    this.totalExercises = totalExercises;
    this.urlExplanatoryVideo = urlExplanatoryVideo;
    this.exercises = exercises;
  }
}