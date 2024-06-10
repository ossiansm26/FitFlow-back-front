export default class Comments{
    id: number;
    content: string;
    creationDate: Date;
    constructor(id: number, content: string, creationDate: Date){
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
    }
}