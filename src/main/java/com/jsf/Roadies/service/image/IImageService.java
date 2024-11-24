package com.jsf.Roadies.service.image;

import com.jsf.Roadies.model.Image;
import com.jsf.Roadies.request.ProfileImageRequest;

public interface IImageService {
    void createProfileImage(ProfileImageRequest request);
    byte[] getProfileImage(Long userId);
    void deleteProfileImage(Long userId);
}
