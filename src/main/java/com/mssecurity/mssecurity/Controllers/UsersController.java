package com.mssecurity.mssecurity.Controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.mssecurity.mssecurity.services.EncryptionService;
import com.mssecurity.mssecurity.services.jwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Models.Role;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Repositories.RoleRepository;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private RoleRepository theRoleRepository;
    @Autowired
    private jwtService jwtService;

    @Autowired
    private EncryptionService encryptionService;
    @GetMapping("")
    public List<User> index() {
        return this.theUserRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User store(@RequestBody User newUser) {
        newUser.setPassword(encryptionService.convertirSHA256(newUser.getPassword()));
        return this.theUserRepository.save(newUser);
    }

    @GetMapping("{id}")
    public User show(@PathVariable String id) {
        User theUser = this.theUserRepository.findById(id).orElse(null);
        return theUser;
    }

    @PutMapping("{id}")
    public User udpate(@PathVariable String id, @RequestBody User theNewUser) {
        User theActualUSer = this.theUserRepository.findById(id).orElse(null);
        if (theActualUSer != null) {
            theActualUSer.setName(theNewUser.getName());
            theActualUSer.setEmail(theNewUser.getEmail());
            theActualUSer.setPassword(encryptionService.convertirSHA256(theActualUSer.getPassword()));
            return this.theUserRepository.save(theActualUSer);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        User theUser = this.theUserRepository.findById(id).orElse(null);
        if (theUser != null) {
            this.theUserRepository.delete(theUser);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{user_id}/role/{role_id}")
    public User matchUserRol(@PathVariable String user_id, @PathVariable String role_id) {
        User theActualUSer = this.theUserRepository.findById(user_id).orElse(null);
        Role theActualRole = this.theRoleRepository.findById(role_id).orElse(null);
        if (theActualUSer != null && theActualRole != null) {
            theActualUSer.setRole(theActualRole);
            return this.theUserRepository.save(theActualUSer);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{user_id}/role")
    public User unMatchUserRol(@PathVariable String user_id) {
        User theActualUSer = this.theUserRepository.findById(user_id).orElse(null);
        if (theActualUSer != null) {
            theActualUSer.setRole(null);
            return this.theUserRepository.save(theActualUSer);
        } else {
            return null;
        }
    }




}
