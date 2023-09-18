package com.countryapi.securityconfig;
import com.countryapi.jwtconfig.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
   private JwtAuthenticationEntryPoint authenticationEntryPoint;
   @Autowired
   private UserDetailsService userDetailsService;
   @Autowired
   private JwtFilter filter;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
              .authorizeRequests().antMatchers("/api/creadital/login").permitAll()
              .anyRequest().authenticated()
              .and()
              .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
              .and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
   }
   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      //  I added user into separate class, we can add credential details here also InMemoryUserDetailsManager correct this sentance
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
   }
   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws
   Exception {
      return super.authenticationManagerBean();
   }

}