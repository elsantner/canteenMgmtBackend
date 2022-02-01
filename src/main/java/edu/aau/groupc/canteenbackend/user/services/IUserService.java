package edu.aau.groupc.canteenbackend.user.services;

import edu.aau.groupc.canteenbackend.mgmt.exceptions.CanteenNotFoundException;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserDto;
import edu.aau.groupc.canteenbackend.user.dto.UserUpdateDTO;
import edu.aau.groupc.canteenbackend.user.exceptions.UserNotFoundException;
import edu.aau.groupc.canteenbackend.user.exceptions.UsernameConflictException;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();
    Optional<User> findById(long id);
    boolean ownerExists();
    User create(User user);
    User updateUser(long id, UserUpdateDTO updateInfo) throws UsernameConflictException, UserNotFoundException, CanteenNotFoundException;
    User create(UserDto user, User.Type type) throws UsernameConflictException, CanteenNotFoundException;
}
