package com.client.ws.dptplus.service;

import com.client.ws.dptplus.model.jpa.UserType;

import java.util.List;

public interface UserTypeService {
    List<UserType> findAll();
}
