package com.sb.blog.controller;

import com.sb.blog.entity.Role;
import com.sb.blog.entity.User;
import com.sb.blog.payload.JWTAuthResoponse;
import com.sb.blog.payload.LoginDto;
import com.sb.blog.payload.SignUpDto;
import com.sb.blog.repository.RoleRepository;
import com.sb.blog.repository.UserRepository;
import com.sb.blog.security.JWTTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@Api(value = "Auth Controller exposes singin and signup api." )
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenProvider tokenProvider;

    @ApiOperation(value = "Rest Api to authenticate user.")
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResoponse> authenticateUser(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResoponse(token));
    }

    @ApiOperation(value = "Rest Api to let users register or signup")
    @PostMapping("/signup")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignUpDto signUpDto) {

        //Check username in DB
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("UserId already taken.", HttpStatus.BAD_REQUEST);
        }
        //Check already existing email in db.
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email already exist.", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("user signedUp succesfully.", HttpStatus.OK);
    }
}
