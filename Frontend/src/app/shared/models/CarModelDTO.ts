import { FuelType } from './FuelType';
import { Transmission } from './Transmission';
import { CarType } from './CarType';

export interface CarModelDTO {
    id?: number;
    basePriceEuros: number;
    bluetoothConnective: boolean;
    bootCapacityLitres: number;
    doorsNumber: number;
    engineDisplacementCcm: number;
    enginePowerBhp: number;
    fuelType: string;
    havingNavigationSystem: boolean;
    havingParkingSensors: boolean;
    havingPassangerAirbags: boolean;
    havingRearCamera: boolean;
    manufacturerId: number;
    manufacturerName?: string;
    mileage: number;
    name: string;
    seatsNumber: number;
    supportingChildSeatMounts: boolean;
    topSpeedKmh: number;
    transmission: string;
    type: string;
}
