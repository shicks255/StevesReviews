package com.steven.hicks.services;

import com.steven.hicks.models.User;
import com.steven.hicks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository m_userRepository;

    public User findById(int id) {
        return m_userRepository.getOne(id);
    }
}
