package com.jacksanger.mhassistant.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jacksanger.mhassistant.security.database.UserServiceImpl;
import com.jacksanger.mhassistant.security.models.User;
import com.jacksanger.mhassistant.security.models.UserRegistrationDtO;

/*
 * Controller to facilitate registration
 */

@Controller
@RequestMapping("/registration")
public class SecurityController {

   @Autowired
   private UserServiceImpl userService;

   @ModelAttribute("user")
   public UserRegistrationDtO userRegistrationDtO() {
       return new UserRegistrationDtO();
   }

   //Navigates user to the registration page
   @GetMapping
   public String showRegistrationForm(Model model) {
       return "registration";
   }

   //Adds the user's new information and registers them
   @PostMapping
   public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDtO userDto, BindingResult result){

       User existing = userService.findByUserName(userDto.getUserName());
       if (existing != null){
           result.rejectValue("username", null, "There is already an account registered with that username");
       }

       if (result.hasErrors()){
           return "registration";
       }

       userService.save(userDto);
       return "redirect:/registration?success";
   }
}