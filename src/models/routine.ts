export default class Routine {
    id: number;
    description: string;
    category: string;
    status: boolean;
    start: string;
    end: string;
    coachsComments: string;
    exercicesCollection: any[];
  
    constructor(
      id: number,
      description: string,
      category: string,
      status: boolean,
      start: string,
      end: string,
      coachsComments: string,
      exercicesCollection: any[]
    ) {
      this.id = id;
      this.description = description;
      this.category = category;
      this.status = status;
      this.start = start;
      this.end = end;
      this.coachsComments = coachsComments;
      this.exercicesCollection = exercicesCollection;
    }
  }