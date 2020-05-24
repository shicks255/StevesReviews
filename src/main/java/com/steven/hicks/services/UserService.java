package com.steven.hicks.services;

import com.steven.hicks.models.User;
import com.steven.hicks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

    public String registerUser(User user) {
        String returnMessage = "Thanks for signing up!";

        if (user.getPassword().length() < 6)
            return "Password not long enough";
        if (user.getUsername().length() < 5)
            return "Username not long enough";

        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashed);
        try
        {
            m_userRepository.save(user);
        } catch (Exception e)
        {
            System.out.println(e);
            if (e instanceof DataIntegrityViolationException)
                returnMessage = "That username already exists.  Please choose another";
            else
                returnMessage = "Something went wrong";
        }

        return returnMessage;
    }
}
