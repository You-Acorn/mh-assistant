package com.jacksanger.mhassistant.security.models;

import javax.persistence.*;

import java.util.Collection;

/*
 * Java object for user information (user name, password, and roles)
 * Contains getters and setters
 */

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String userName;
   private String password;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(
           name = "users_roles",
           joinColumns = @JoinColumn(
                   name = "user_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(
                   name = "role_id", referencedColumnName = "id"))
   private Collection<Role> roles;

   //No arg contructor
   public User() {
   }

   //User name and password constructor
   public User(String userName, String password) {
	   this.userName = userName;
       this.password = password;
   }

   public User(String userName, String password, Collection<Role> roles) {
	   this.userName = userName;
       this.password = password;
       this.roles = roles;
   }

   public Long getId() {
       return id;
   }

   public void setId(Long id) {
       this.id = id;
   }

   public String getUserName() {
       return userName;
   }

   public void setUserName(String userName) {
       this.userName = userName;
   }
   
   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }

   public Collection<Role> getRoles() {
       return roles;
   }

   public void setRoles(Collection<Role> roles) {
       this.roles = roles;
   }
}