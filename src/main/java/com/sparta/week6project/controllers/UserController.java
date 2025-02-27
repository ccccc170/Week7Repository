package com.sparta.week6project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.week6project.daos.impl.UserDAO;
import com.sparta.week6project.dtos.UserDTO;
import com.sparta.week6project.repositories.ApikeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOError;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ApikeyRepository apikeyRepository;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Integer id) {
        return userDAO.findById(id).get();
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody UserDTO userDTO, @RequestParam String key) {
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("content-type", "application/json");
        ResponseEntity<String> response = null;
        try {
            response = new ResponseEntity<>(objectMapper.writeValueAsString(userDTO), headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (userDAO.isUpdateUser(key) || userDAO.isAdminUser(key)) {
            userDAO.save(userDTO);
            return response;
        } else {
            response = new ResponseEntity<>("{\"message\":\"You are not authorized\"}", headers, HttpStatus.UNAUTHORIZED);
        }
        return response;
    }


    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody UserDTO userDTO, String key) {
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("content-type", "application/json");
        ResponseEntity<String> response = null;
        Optional<UserDTO> originalOptional = userDAO.findById(userDTO.getId());
        try {
            response = new ResponseEntity<>(objectMapper.writeValueAsString(userDTO), headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (originalOptional.isPresent() && (userDAO.isUpdateUser(key) || userDAO.isAdminUser(key))) {
            UserDTO original = originalOptional.get();
            if (userDTO.getFirstName() != null) {
                original.setFirstName(userDTO.getFirstName());
            }
            if (userDTO.getLastName() != null) {
                original.setLastName(userDTO.getLastName());
            }
            if (userDTO.getEmail() != null) {
                original.setEmail(userDTO.getEmail());
            }
            if (userDTO.getRole() != null) {
                original.setRole(userDTO.getRole());
            }

            userDAO.save(original);
            return response;
        } else {
            response = new ResponseEntity<>("{\"message\":\"You are not authorized\"}", headers, HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteById(@RequestParam Integer id, @RequestParam String key) {
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("content-type", "application/json");
        ResponseEntity<String> response = null;
        try {
            response = new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (IOError e) {
            throw new RuntimeException(e);
        }
        if ((userDAO.isAdminUser(key))) {
            userDAO.deleteById(id);
            return response;
        } else {
            response = new ResponseEntity<>("{\"message\":\"You are not authorized\"}", headers, HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @GetMapping("/getApiKey/{email}")
    public String getApiKey(@PathVariable String email) {
        return userDAO.getApiKey(email);
    }

    @GetMapping("/getApiKey/regenerate/{email}")
    public String regenerateApiKey(@PathVariable String email) {
        return userDAO.regenerateApiKey(email);
    }

}
