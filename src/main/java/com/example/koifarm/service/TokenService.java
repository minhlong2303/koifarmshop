package com.example.koifarm.service;

import com.example.koifarm.entity.User;
import com.example.koifarm.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {
    @Autowired
    @Lazy
    UserRepository userRepository;

    public final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407bb";

    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    //tao ra token
    public String generateToken(User user){
        String token = Jwts.builder()
                .subject(user.getId()+"")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24)) //token het han trong 1 ngay
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    //verify token
    public User getUserByToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String idString = claims.getSubject();
        long id = Long.parseLong(idString);

        return userRepository.findUserById(id);
    }
}
