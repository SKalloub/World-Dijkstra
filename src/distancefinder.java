public class distancefinder {
   public static int radiusOfEarth = 6371;
    public static double fromLongAndLat(double long1,double lat1, double long2, double lat2) {
        double dlat = deg2rad(lat2-lat1);
        double dlong = deg2rad(long2-long1);
        double d = Math.sin(dlat/2) * Math.sin(dlat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dlong/2) * Math.sin(dlong/2);
        double d2 = 2*Math.atan2(Math.sqrt(d),Math.sqrt(1-d));
        double distance = radiusOfEarth*d2;

        distance = (double) (((int) (distance*100.0))/100.0);
        return distance;
    }

    private static double deg2rad(double v) {
        return (Math.PI/180)*v;
    }
}
