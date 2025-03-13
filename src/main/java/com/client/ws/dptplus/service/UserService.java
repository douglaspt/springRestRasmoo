package com.client.ws.dptplus.service;

import com.client.ws.dptplus.dto.UserDto;
import com.client.ws.dptplus.model.jpa.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User create(UserDto dto);

    User uploadPhoto(Long id, MultipartFile file) throws IOException;

    List<User> findAll();

    byte[] downloadPhoto(Long id);
}
