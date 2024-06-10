export default class Exercices {
  id: number;
  exerciseName: string;
  duration: number;
  description: string;
  urlImage: string;
  material: any[];
  muscleGroup: any[];

  constructor(
    id: number,
    exerciseName: string,
    duration: number,
    description: string,
    urlImage: string,
    material: any[],
    muscleGroup: any[]
  ) {
    this.id = id;
    this.exerciseName = exerciseName;
    this.duration = duration;
    this.description = description;
    this.urlImage = urlImage;
    this.material = material;
    this.muscleGroup = muscleGroup;
  }
}