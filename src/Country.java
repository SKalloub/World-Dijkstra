import java.util.ArrayList;

public class Country {
    private String CountryName;
    private double X ;
    private double Y;


    private double longitude;
    private double latitude;
    public Country(String countryName) {
        CountryName = countryName;
    }

    public void Addxy(double x, double y) {
        X = x;
        Y = y;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
