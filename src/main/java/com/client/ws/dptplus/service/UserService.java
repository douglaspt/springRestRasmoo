package com.client.ws.dptplus.service;

import com.client.ws.dptplus.dto.UserDto;
import com.client.ws.dptplus.model.User;

import java.util.List;

public interface UserService {
    User create(UserDto dto);

    List<User> findAll();
}
