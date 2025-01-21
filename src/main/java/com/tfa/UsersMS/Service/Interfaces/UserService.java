package com.tfa.UsersMS.Service.Interfaces;

import com.tfa.UsersMS.Dtos.UserDTO;
import com.tfa.UsersMS.Dtos.UserDTOResponse;

public interface UserService {
    public UserDTOResponse registerUser(UserDTO userDTO);
    public UserDTOResponse getUserById(Long id);
    public UserDTOResponse updateUser(Long id, UserDTO userDTO);

}
