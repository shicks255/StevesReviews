package com.steven.hicks.services;

import com.steven.hicks.models.User;
import com.steven.hicks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository m_userRepository;
    @Autowired
    BCrypt m_bCrypt;

    public User findById(int id) {
        return m_userRepository.findById(id).get();
    }

    public void registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashed);
        m_userRepository.save(user);
    }
}
