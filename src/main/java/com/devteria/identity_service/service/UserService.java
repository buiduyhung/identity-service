package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.UserMapper;
import com.devteria.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public User createRequest(UserCreationRequest request) {
        //User user = new User();

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXITED);
        }
        User user = userMapper.toUser(request); //dùng mapper để lấy dữ liệu

        //user.setUsername(request.getUsername());
        //user.setPassword(request.getPassword());
        //user.setFirstName(request.getFirstName());
        //user.setLastName(request.getLastName());
        //user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public User updateRequest(String userId, UserUpdateRequest request) {
        User user = getUser(userId);
        userMapper.updateUser(user, request);

        //user.setPassword(request.getPassword());
        //user.setFirstName(request.getFirstName());
        //user.setLastName(request.getLastName());
        //user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
