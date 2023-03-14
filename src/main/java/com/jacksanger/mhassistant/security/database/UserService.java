package com.jacksanger.mhassistant.security.database;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jacksanger.mhassistant.security.models.User;
import com.jacksanger.mhassistant.security.models.UserRegistrationDtO;

/*
 * Interface for user services
 */

public interface UserService extends UserDetailsService {
   //Takes a user name and returns the relevant user
   User findByUserName(String userName);
   //Takes a UserRegistrationDtO, saves the registration as a user and returns the user
   User save(UserRegistrationDtO registration);
}