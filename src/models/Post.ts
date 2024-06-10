export default class Post {
  id: number;
  title: string;
  content: string;
  creationDate: Date;
  category: string;
  comments: any[];

  constructor(
    id: number,
    title: string,
    content: string,
    creationDate: Date,
    category: string,
    comments: any[]
  ) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.creationDate = creationDate;
    this.category = category;
    this.comments = comments;
  }
}