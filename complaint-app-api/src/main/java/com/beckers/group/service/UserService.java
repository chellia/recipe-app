package com.beckers.group.service;

import com.beckers.group.model.User;

import java.util.List;

public interface UserService {

  List<User> getByComplaintId(Long id);

  User getUserById(Long id);

  void deleteUserById(Long id);

  User createUser(User user);

  User updateUser(Long userId, User user);

}
