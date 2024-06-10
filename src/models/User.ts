export default class User {
    name: string;
    lastNames: string;
    age: Date;
    email: string;
    password: string;
    enrollamentDate: Date;
    phoneNumber: string;
    address: string;
    rol: string;
    speciality: string;
    image: string;
    routinesCreated: any[];
    routinesAssociated: any[];
    communityCreated: any[];
    communityAssociated: any[];
    achievement: any[];
    post: any[];
    comment: any[];

    constructor(name: string, lastNames: string, age: Date, email: string, password: string, enrollamentDate: Date, phoneNumber: string, address: string, rol: string, speciality: string,  image: string) {
        this.name = name;
        this.lastNames = lastNames;
        this.age = age;
        this.email = email;
        this.password = password;
        this.enrollamentDate = enrollamentDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.rol = rol;
        this.speciality = speciality;
        this.image = image;
        this.routinesCreated = [];
        this.routinesAssociated = [];
        this.communityCreated = [];
        this.communityAssociated = [];
        this.achievement = [];
        this.post = [];
        this.comment = [];
    }
}
