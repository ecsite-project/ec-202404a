package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void insert(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.insert(user);
    }

    public boolean checkEmail(String email){
        if (userRepository.findByEmail(email) == null) {
            return false;
        }
        return true;
    }
}
