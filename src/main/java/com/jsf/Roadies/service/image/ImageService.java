package com.jsf.Roadies.service.image;

import com.jsf.Roadies.Exceptions.EntityAlreadyExists;
import com.jsf.Roadies.Exceptions.NoEntityFound;
import com.jsf.Roadies.Exceptions.UserNotFoundException;
import com.jsf.Roadies.model.Image;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.repository.ImageRepository;
import com.jsf.Roadies.repository.UserRepository;
import com.jsf.Roadies.request.ProfileImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    final private ImageRepository imageRepository;
    final private UserRepository userRepository;
    @Override
    public void createProfileImage(ProfileImageRequest req) {

        if(!imageRepository.existsByUserId(req.getUserId())) {
            throw new EntityAlreadyExists("Already has a profile image");
        }
        User user = userRepository.findById(req.getUserId()).orElseThrow(()-> new UserNotFoundException("User Not Found"));

        Image image = new Image();
        image.setUser(user);
        image.setFileType(req.getFileType());
        image.setFileName(req.getFileName());
        image.setImage(req.getImage());

        imageRepository.save(image);
    }

    @Override
    public byte[] getProfileImage(Long userId) {
        Image img =imageRepository.findByUserId(userId).orElseThrow(()-> new NoEntityFound("Profile Image Not Found"));
        Blob imageBlob = img.getImage();
        try {
            int n = (int) imageBlob.length();
            return imageBlob.getBytes(1,n);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteProfileImage(Long userId) {
        if (imageRepository.existsByUserId(userId)) {
            imageRepository.deleteByUserId(userId);
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }
}
