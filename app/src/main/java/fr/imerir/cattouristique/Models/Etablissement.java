package fr.imerir.cattouristique.Models;

import java.io.Serializable;

/**
 * Created by rcdsm on 18/05/15.
 */
public class Etablissement implements Serializable /**extends RealmObject**/{
    public long id;
    public  String name;
    public  String type;
    public  String adresse;
    public  String phone;
    public  String photo_link;

    public  double latitude;
    public  double longitude;


    public Etablissement(String _name, String _type, String _adresse, String _phone, String _photo_link, double _latitude, double _longitude, long _id) {
        this.name = _name;
        this.type = _type;
        this.adresse = _adresse;
        this.phone = _phone;
        this.photo_link = _photo_link;
        this.latitude = _latitude;
        this.longitude = _longitude;
        this.id = _id;
    }


    public Etablissement() {
        this.name = "";
        this.type = "";
        this.adresse = "";
        this.phone = "";

        this.photo_link = "";
        this.latitude = 0.0f;
        this.longitude = 0.0f;
        this.id = 0;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

}
