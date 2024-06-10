export default class Material {
    id: number;
    materialName: string;
    avalibilityStatus: boolean;
    lastMaintence: Date;

    constructor(id: number, materialName: string, avalibilityStatus: boolean, lastMaintence: Date) {
        this.id = id;
        this.materialName = materialName;
        this.avalibilityStatus = avalibilityStatus;
        this.lastMaintence = lastMaintence;
    }
}