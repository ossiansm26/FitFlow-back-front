export default class Community {
  id: number;
  name: string;
  description: string;
  post: any[];
  urlpicture: string;

  constructor(
    id: number,
    name: string,
    description: string,
    post: any[],
    urlpicture: string
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.post = post;
    this.urlpicture = urlpicture;
  }
}
