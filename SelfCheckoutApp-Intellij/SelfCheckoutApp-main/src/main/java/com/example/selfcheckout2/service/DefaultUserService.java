package com.example.selfcheckout2.service;
import com.example.selfcheckout2.data.User;
import com.example.selfcheckout2.data.UserData;
import com.example.selfcheckout2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service("userService")
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserData saveUser(UserData user) {
        User userModel = populateUserEntity(user);
        return populateUserData(userRepository.save(userModel));
    }

    @Override
    public UserData getUserById(Long userId) {
        User user = userRepository.getById(userId);
        return populateUserData(user);
    }

    private UserData populateUserData(User userEntityToObject) {
        UserData userData = new UserData();
        userData.setId(userEntityToObject.getIdUser());
        userData.setFirstName(userEntityToObject.getFirstName());
        userData.setLastName(userEntityToObject.getLastName());
        userData.setEmail(userEntityToObject.getEmail());
        return userData;
    }

    private User populateUserEntity(UserData userData) {
        User user = new User();
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        return user;
    }
}
