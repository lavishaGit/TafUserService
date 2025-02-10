package com.tfa.UsersMS.Controller;


import com.tfa.UsersMS.Dtos.UserDTO;
import com.tfa.UsersMS.Dtos.UserDTOResponse;
import com.tfa.UsersMS.Service.ServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController 
    @CrossOrigin(origins = "*")
@RequestMapping("/users")
//@AllArgsConstructor
public class UserController {
    @Autowired
    private ServiceClient serviceClient;


    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTOResponse addedUser = serviceClient.registerUser(userDTO); // Wrap the result in Optional
            // If the user was not found, return BAD REQUEST
            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add user: "+e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTOResponse> getUser(@PathVariable Long id) throws Exception {
        // Use Optional to safely handle user retrieval
        try {
            UserDTOResponse user = serviceClient.getUserById(id);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Return NOT_FOUND if the user does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws Exception {
        try {
            UserDTOResponse updatedUser = serviceClient.updateUser(id, userDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user: Invalid ID or user details");

            // If user was successfully updated, return OK

        }

    }
}

//    @ExceptionHandler
//   public ResponseEntity<?> respondWithError(Exception e) {
//    return new ResponseEntity<>("Exception Occurred. More Info :"
//                + e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//    // Handler for UserNotFoundException
//    @ExceptionHandler
//    public ResponseEntity<?> handleUserNotFoundException(Exception e) {
//        return new ResponseEntity<>("User not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
//    }


