package com.invillia.springoauth2rest.resource;

import com.invillia.springoauth2rest.model.entity.User;
import com.invillia.springoauth2rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserResource {

    final private UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(userRepository.findAll());

    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return ResponseEntity.ok(userRepository.save(user));

    }
}
