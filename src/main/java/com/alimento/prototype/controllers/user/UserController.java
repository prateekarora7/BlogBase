package com.alimento.prototype.controllers.user;

import com.alimento.prototype.dtos.user.UpdatePasswordRequest;
import com.alimento.prototype.entities.user.User;
import com.alimento.prototype.exceptions.UsernameNotFoundException;
import com.alimento.prototype.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody User user){  // EndPoint to save a User
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){ //Endpoint to get User using its username
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){ //Endpoint to get User using its email
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/created-on/{date}")
    public ResponseEntity<List<User>> getUsersByCreationDate(@PathVariable LocalDate date){ //Endpoint to get User using its registration date
        List<User> users = userService.getUsersByCreationDate(date);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update/username/{oldUsername}")
    public ResponseEntity updateUsername(@PathVariable String oldUsername, @RequestParam(required = true) String newUsername){ //Endpoint to get User update username. Using old username to find the user first
        userService.updateUsername(oldUsername, newUsername);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update/phone-number/{username}")
    public ResponseEntity updatePhoneNumber(@PathVariable String username, @RequestParam(required = true)String newPhoneNumber){ //Endpoint to update user's Phone Number. Using username to find the user
        userService.updatePhoneNumber(username, newPhoneNumber);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("update/name/{username}")
    public ResponseEntity updateName(@PathVariable String username, @RequestParam(required = true) String firstName, @RequestParam(required = true) String lastName) { //Endpoint to update Name of a user. Using username to find the user
        userService.updateName(username, firstName, lastName);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("update/password/{username}")
    public ResponseEntity updatePassword(@PathVariable String username, @RequestBody UpdatePasswordRequest request) { //EndPoint to Update the password for an account. Finding user using username
        userService.updatePassword(username, request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/username/{username}")
    public ResponseEntity deleteUserByUsername(@PathVariable String username) { // Endpoint to delete a User using its username
        userService.deleteUserByUsername(username);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity deleteUserByEmail(@PathVariable String email) { // Endpoint to delete a user using its email
       userService.deleteUserByEmail(email);
        return new ResponseEntity(HttpStatus.OK);
    }
}
