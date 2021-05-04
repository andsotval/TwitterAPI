package com.twitterapi.twitterapi.entity.twitter;

import javax.persistence.Embeddable;

@Embeddable
public class Location {

    private Double longitude;
    private Double latitude;

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
