package com.jsf.Roadies.request;

import lombok.Data;

import java.sql.Blob;

@Data
public class ProfileImageRequest {
    private Long id;
    private String fileName;
    private String fileType;
    private Blob image;
    private Long userId;
}
