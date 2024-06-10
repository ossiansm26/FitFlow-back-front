export default class MuscleGroup {
  id: number;
  groupName: string;
  muscleImage: string;
  bodyPart: string;

  constructor(
    id: number,
    groupName: string,
    muscleImage: string,
    bodyPart: string
  ) {
    this.id = id;
    this.groupName = groupName;
    this.muscleImage = muscleImage;
    this.bodyPart = bodyPart;
  }
}