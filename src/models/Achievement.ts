export default class Achievement {
  id: number;
  achievementName: string;
  achievementDescription: string;
  achievementURL: string;

  constructor(
    id: number,
    achievementName: string,
    achievementDescription: string,
    achievementURL: string
  ) {
    this.id = id;
    this.achievementName = achievementName;
    this.achievementDescription = achievementDescription;
    this.achievementURL = achievementURL;
  }
}