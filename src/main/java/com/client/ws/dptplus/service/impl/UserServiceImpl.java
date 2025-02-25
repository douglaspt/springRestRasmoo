package com.client.ws.dptplus.service.impl;

import com.client.ws.dptplus.dto.UserDto;
import com.client.ws.dptplus.exception.BadRequestException;
import com.client.ws.dptplus.exception.NotFoundException;
import com.client.ws.dptplus.mapper.UserMapper;
import com.client.ws.dptplus.model.SubscriptionType;
import com.client.ws.dptplus.model.User;
import com.client.ws.dptplus.model.UserType;
import com.client.ws.dptplus.repository.UserRepository;
import com.client.ws.dptplus.repository.UserTypeRepository;
import com.client.ws.dptplus.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;


    UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public User create(UserDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("id deve ser nulo");
        }

        var userTypeOpt = userTypeRepository.findById(dto.getUserTypeId());

        if (userTypeOpt.isEmpty()) {
            throw new NotFoundException("userTypeId não encontrado");
        }

        UserType userType = userTypeOpt.get();
        User user = UserMapper.fromDtoToEntity(dto, userType, null);
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
