package com.jacksanger.mhassistant.security.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacksanger.mhassistant.security.models.User;

/*
 * Interface which extends the jpa repository and connections the java application to the user table
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  //Takes a user name and returns the relevant user
  User findByUserName(String userName);
}