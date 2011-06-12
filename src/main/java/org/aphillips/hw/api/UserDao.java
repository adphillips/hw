package org.aphillips.hw.api;

import org.aphillips.hw.domain.User;

public interface UserDao {

  void saveUser(User user);

  User readUser(Long id);

}