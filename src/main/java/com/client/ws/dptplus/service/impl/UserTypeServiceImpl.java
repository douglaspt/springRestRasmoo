package com.client.ws.dptplus.service.impl;

import com.client.ws.dptplus.model.jpa.UserType;
import com.client.ws.dptplus.repository.jpa.UserTypeRepository;
import com.client.ws.dptplus.service.UserTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    UserTypeServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }
}
