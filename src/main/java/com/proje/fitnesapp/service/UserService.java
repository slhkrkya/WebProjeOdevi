package com.proje.fitnesapp.service;

import com.proje.fitnesapp.dto.PasswordChangeDto;
import com.proje.fitnesapp.dto.UserRegisterDto;
import com.proje.fitnesapp.dto.UserUpdateDto;
import com.proje.fitnesapp.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface UserService {
    void register(UserRegisterDto dto) throws IOException;
    User findByEmail(String email);
    User findById(Long id);
    void updateProfile(String email, UserUpdateDto dto) throws IOException;
    boolean changePassword(String email, PasswordChangeDto dto) throws IOException;
}
