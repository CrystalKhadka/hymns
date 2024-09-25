package com.hymns.hymns.service;

import com.hymns.hymns.dto.UserDto;


public interface UserService {
    void register(UserDto userDto);

    String login(UserDto userDto);

    UserDto getUserDetails(String token);
}