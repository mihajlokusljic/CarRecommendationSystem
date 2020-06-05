package kusljic.mihajlo.sbnz.spring.backend.util;


public class DistanceCalculator {
	
	/**
	 * Calculates the distance between two points on Earth, in kilometers.
	 * @param lat1 Latitude coordinate of first point
	 * @param long1 Longitude coordinate of first point
	 * @param lat2 Latitude coordinate of second point
	 * @param long2 Longitude coordinate of second point
	 * @return Distance between points, in kilometers
	 */
	public static double kilometerDistance(double lat1, double long1, double lat2, double long2) {
		double theta = long1 - long2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return dist;
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

}
