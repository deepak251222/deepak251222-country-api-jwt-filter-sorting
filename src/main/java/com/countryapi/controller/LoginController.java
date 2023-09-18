package com.countryapi.controller;
import com.countryapi.jwtconfig.JwtService;
import com.countryapi.model.JwtRequestModel;
import com.countryapi.model.JwtResponseModel;
import com.countryapi.securityconfig.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/creadital")
public class LoginController {
   @Autowired
   private JwtUserDetailsService userDetailsService;
   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private JwtService jwtService;
   @PostMapping("/login")
   public ResponseEntity<?> createToken(@RequestBody JwtRequestModel request) throws Exception {
      try {
         authenticationManager.authenticate(
            new
            UsernamePasswordAuthenticationToken(request.getUsername(),
            request.getPassword())
         );
      } catch (DisabledException e) {
         throw new Exception("USER_DISABLED", e);
      } catch (BadCredentialsException e) {
         throw new Exception("INVALID_CREDENTIALS", e);
      }
      final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
      final String jwtToken = jwtService.generateJwtToken(userDetails);
      return ResponseEntity.ok(new JwtResponseModel(jwtToken));
   }
}