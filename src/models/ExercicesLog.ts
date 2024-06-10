import Exercices from "./Exercices";

class ExercicesLog {
  id: number;
  date: Date;
  exercice: Exercices;
  sets: any[];

  constructor(id: number, date: Date, exercice: Exercices, sets: any[]) {
    this.id = id;
    this.date = date;
    this.exercice = exercice;
    this.sets = sets;
  }
}