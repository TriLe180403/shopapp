package com.project.shopapp.services;

import com.project.shopapp.Dto.UserDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.User;



public interface IUserService {
    User createUser(UserDto userDto) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
