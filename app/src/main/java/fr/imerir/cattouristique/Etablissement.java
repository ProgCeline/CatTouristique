package fr.imerir.cattouristique;

/**
 * Created by rcdsm on 18/05/15.
 */
public class Etablissement {
    public  String name;
    public  String type;
    public  String adresse;
    public  String photo_link;

    public  double latitude;
    public  double longitude;

    public Etablissement(String _name, String _type, String _adresse, String _photo_link, double _latitude, double _longitude) {
        this.name = _name;
        this.type = _type;
        this.adresse = _adresse;
        this.photo_link = _photo_link;
        this.latitude = _latitude;
        this.longitude = _longitude;
    }

    public Etablissement() {
        this.name = "";
        this.type = "";
        this.adresse = "";
        this.photo_link = "";
        this.latitude = 0.0f;
        this.longitude = 0.0f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public void setPhoto_link(String photo_link) {
        this.photo_link = photo_link;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Etablissement{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", adresse='" + adresse + '\'' +
                ", photo_link='" + photo_link + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
