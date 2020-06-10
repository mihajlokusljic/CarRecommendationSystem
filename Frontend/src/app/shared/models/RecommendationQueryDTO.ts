export interface RecommendationQueryDTO {
    beginner: boolean;
    budget: number;
    forCargoTransport: boolean;
    forCityTraffic: boolean;
    forOffroading: boolean;
    forSport: boolean;
    forTravelling: boolean;
    hasFamily: boolean;
    needsConnectivity: boolean;
    userCountryId: number;
}
