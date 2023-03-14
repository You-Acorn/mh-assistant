package com.jacksanger.mhassistant.security.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Java object to store role types and their primary key
 * Contains getters and setters
 */

@Entity
public class Role {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String name;

   //No arg constructor
   public Role() {
   }

   //Role name contructor
   public Role(String name) {
       this.name = name;
   }
   public Long getId() {
       return id;
   }
   public void setId(Long id) {
       this.id = id;
   }

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }
}