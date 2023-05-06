package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.entity.User;
import com.team9ookie.dangdo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }
}