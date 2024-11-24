// UserLocation.java
package com.jsf.Roadies.model;

import lombok.Data;

@Data
public class UserLocation {
    private String userId;
    private double latitude;
    private double longitude;
    private String timestamp;
}