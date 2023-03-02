package com.example.selfcheckout2.service;


import com.example.selfcheckout2.data.UserData;

public interface UserService {
    UserData saveUser(UserData user);

    UserData getUserById(final Long userId);

}
