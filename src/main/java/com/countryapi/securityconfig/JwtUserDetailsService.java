package com.countryapi.securityconfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class JwtUserDetailsService implements UserDetailsService {

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      if (users.containsKey(username)) {
         return users.get(username);
      } else {
         throw new UsernameNotFoundException("User not found with username: " + username);
      }
   }
   private Map<String, UserDetails> users = new HashMap<>();
// i add only user user we can add more user like that
   @PostConstruct
   public void init() {
      users.put("deepak123", new User("deepak123", passwordEncoder.encode("deepak"), new ArrayList<>()));
      users.put("kumar123", new User("kumar123", passwordEncoder.encode("kumar"), new ArrayList<>()));
   }

}
