package com.tfa.UsersMS.Service;
import com.tfa.UsersMS.Dtos.UserDTO;
import com.tfa.UsersMS.Dtos.UserDTOResponse;
import com.tfa.UsersMS.Service.Interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class ServiceClient  implements UserService {
    private static final Logger logger = LogManager.getLogger(ServiceClient.class);

    @Autowired
    private RestTemplate restTemplate;
    @Value("${datastore.service.url}")
    private String dataStoreURL;

    public UserDTOResponse registerUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User fields are missing");
        }

        //return restTemplate.postForObject(dataStoreURL, userDTO, UserDTO.class);
        UserDTOResponse response = restTemplate.postForObject(dataStoreURL, userDTO, UserDTOResponse.class);
        System.out.println(response);

        if (response == null) {
            throw new RuntimeException("Failed to register user, received null response");
        }
        // Log successful registration
        logger.info("User registered successfully: " + userDTO.getEmail());
        return response  ;

    }
    public UserDTOResponse getUserById(Long id) {
        UserDTOResponse response = restTemplate.getForObject(dataStoreURL + "/" + id, UserDTOResponse.class);
        if (response == null) {
            logger.error("Could not retrieve response");
            throw new RuntimeException("Failed to retrieve user, received null response");

        }
        logger.info("Received User by Id");
        return response;
    }
    public UserDTOResponse updateUser(Long id, UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User  Data is null");
        }
        try {
            UserDTOResponse responseUser = restTemplate.getForObject(dataStoreURL + "/" + id, UserDTOResponse.class);

            if (!responseUser.getId().equals(id)) {
                throw new NoSuchElementException("User with ID " + id + " does not exist");
            } // Log the update attempt
            logger.info("Updating user with ID: " + id);


            restTemplate.put(dataStoreURL + "/" + id, userDTO, UserDTOResponse.class);
            // Log success
            logger.info("User with ID: " + id + " successfully updated");
        } catch (RestClientException e) {
            // Handle HTTP/communication errors
            logger.error("Error during user update", e);
            throw new RuntimeException("Failed to update user due to network issues", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Unexpected error during user update", e);
            throw new RuntimeException("Unexpected error occurred while updating the user", e);
        }

      return restTemplate.getForObject(dataStoreURL + "/" + id, UserDTOResponse.class);  // Getting the updated user info

    }

}
