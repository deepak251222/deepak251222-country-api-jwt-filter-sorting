package com.countryapi.jwtconfig;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap; 
import java.util.Map;
import java.util.function.Function;

import com.countryapi.exception.ResourceNotFoundException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component 
public class JwtService {

      public static final long TOKEN_VALIDITY = 10 * 60 * 60;
      private String jwtSecret = "5367566B59703373367639742352363263563476474685gdg5gsdgrtwertwet3425326536fdgdg54353465gdfg";

      private Claims extractAllClaims(String token) {
         try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
         } catch (SignatureException se) {
            throw new BadCredentialsException("Invalid Token received!");
         }
      }

      public String generateJwtToken(UserDetails userDetails) {
         Map<String, Object> claims = new HashMap<>();
         return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                 .signWith(getSignKey(), io.jsonwebtoken.SignatureAlgorithm.HS512).compact();
      }

      public Boolean validateJwtToken(String token, UserDetails userDetails) {
         String username = getUsernameFromToken(token);
         extractAllClaims(token);

         try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            Boolean isTokenExpired = claims.getExpiration().before(new Date());

            return (username.equals(userDetails.getUsername()) && !isTokenExpired);
         } catch (ExpiredJwtException ex) {
            throw new ResourceNotFoundException("JWT expired");
         } catch (SignatureException ex) {
            throw new ResourceNotFoundException("Token is invalid");
         }
      }

      public String getUsernameFromToken(String token) {
         final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
         return claims.getSubject();
      }

      private Key getSignKey() {
         byte[] keyBytes = java.util.Base64.getDecoder().decode(jwtSecret);
         return Keys.hmacShaKeyFor(keyBytes);
      }
   }
