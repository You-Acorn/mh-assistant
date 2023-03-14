package com.jacksanger.mhassistant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jacksanger.mhassistant.security.database.UserService;

/*
 * Configuration for security
 */

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
   @Lazy
   @Autowired
   private UserService userService;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http
               .authorizeRequests()
               		.antMatchers("/admin**", "/admin/**").hasAnyAuthority("ADMIN")
	               .antMatchers("/", "/about").permitAll()
                   .antMatchers(
                           "/registration**",
                           "/js/**",
                           "/css/**",
                           "/style**",
                           "/img/**",
                           "/webjars/**").permitAll()
                   .anyRequest().authenticated()
               .and()
                   .formLogin()
                       .loginPage("/login")
                       .defaultSuccessUrl("/", true)
                           .permitAll()
               .and()
                   .logout()
                       .invalidateHttpSession(true)
                       .clearAuthentication(true)
                       .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                       .logoutSuccessUrl("/login?logout")
               .permitAll();
   }

   //Returns the password encoder
   @Bean
   public BCryptPasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }

   //Returns configured DaoAuthenticationProvider
   @Bean
   public DaoAuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
       auth.setUserDetailsService(userService);
       auth.setPasswordEncoder(passwordEncoder());
       return auth;
   }

   //Configures AuthenticationManagerBuilder
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.authenticationProvider(authenticationProvider());
   }

}