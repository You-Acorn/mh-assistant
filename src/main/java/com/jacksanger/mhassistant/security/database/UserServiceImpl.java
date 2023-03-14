package com.jacksanger.mhassistant.security.database;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jacksanger.mhassistant.security.models.Role;
import com.jacksanger.mhassistant.security.models.User;
import com.jacksanger.mhassistant.security.models.UserRegistrationDtO;

/*
 * Implementation of the UserService interface
 */

@Service
public class UserServiceImpl implements UserService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   public User findByUserName(String userName){
       return userRepository.findByUserName(userName);
   }

   public User save(UserRegistrationDtO registration){
       User user = new User();
       user.setUserName(registration.getUserName());
       user.setPassword(passwordEncoder.encode(registration.getPassword()));
       user.setRoles(Arrays.asList(new Role("ROLE_USER")));
       return userRepository.save(user);
   }

   //Takes a user name and returns the relevant user details
   @Override
   public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       User user = userRepository.findByUserName(userName);
       if (user == null){
           throw new UsernameNotFoundException("Invalid username or password.");
       }
       return new org.springframework.security.core.userdetails.User(user.getUserName(),
               user.getPassword(),
               mapRolesToAuthorities(user.getRoles()));
   }

   //Takes a collection of roles and returns a map of roles and their authority (determined from SecurityConfiguration.java)
   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
       return roles.stream()
               .map(role -> new SimpleGrantedAuthority(role.getName()))
               .collect(Collectors.toList());
   }
}