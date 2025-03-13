package com.client.ws.dptplus.service.impl;

import com.client.ws.dptplus.dto.UserDto;
import com.client.ws.dptplus.exception.BadRequestException;
import com.client.ws.dptplus.exception.NotFoundException;
import com.client.ws.dptplus.mapper.UserMapper;
import com.client.ws.dptplus.model.jpa.SubscriptionType;
import com.client.ws.dptplus.model.jpa.User;
import com.client.ws.dptplus.model.jpa.UserType;
import com.client.ws.dptplus.repository.jpa.UserRepository;
import com.client.ws.dptplus.repository.jpa.UserTypeRepository;
import com.client.ws.dptplus.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String PNG = ".png";
    private static final String JPEG = ".jpeg";

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

    @Override
    public User uploadPhoto(Long id, MultipartFile file) throws IOException {
        String imgName = file.getOriginalFilename();
        String formatPNG =  imgName.substring(imgName.length() - 4);
        String formatJPEG =  imgName.substring(imgName.length() - 5);
        if (!(PNG.equalsIgnoreCase(formatPNG) || JPEG.equalsIgnoreCase(formatJPEG))) {
            throw new BadRequestException("Imagem deve possuir formato JPEG ou PNG.");
        }
        User user = findById(id);
        user.setPhotoName(file.getOriginalFilename());
        user.setPhoto(file.getBytes());
        return userRepository.save(user);
    }

    @Override
    public byte[] downloadPhoto(Long id) {
        User user = findById(id);
        if (Objects.isNull(user.getPhoto())) {
            throw new BadRequestException("Usuário não possui foto");
        }
        return user.getPhoto();
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }


}
