package com.example.movierestapi.config;

import com.example.restapimongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }


    //authorization
    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        // super.configure(http);
      http.csrf().disable().authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/auth").permitAll()
             .antMatchers(HttpMethod.GET,"/**").permitAll()
              .antMatchers(HttpMethod.POST,"/**").permitAll()
              .antMatchers(HttpMethod.DELETE,"/**").permitAll()
              .antMatchers(HttpMethod.PUT,"/**").permitAll()
                .anyRequest().authenticated();


    }
  @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}




