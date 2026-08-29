package Myproject.FINTRACK.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import Myproject.FINTRACK.DTO.UserDTO;
import Myproject.FINTRACK.entity.User;
import Myproject.FINTRACK.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private Logger log = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // add user
    public UserDTO addUser(UserDTO userDTO) {
        log.info("Adding user: {}", userDTO.getName());
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        log.info("User added successfully with ID: {}", savedUser.getId());
        return convertToDTO(savedUser);
    }

    // get user
    public List<UserDTO> getUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).toList();
    }
    //get user by id
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> { log.warn("User with ID {} not found", id); return new RuntimeException("User not found"); });
        log.info("Retrieved user with ID: {}", id);
        return convertToDTO(user);
    }
    //update user
    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        if(userRepository.findById(id).isPresent()) {
            User existingUser = userRepository.findById(id).get();
            existingUser.setName(updatedUserDTO.getName());
            existingUser.setEmail(updatedUserDTO.getEmail());
            User updatedUser = userRepository.save(existingUser);
            log.info("User with ID {} updated", id);
            return convertToDTO(updatedUser);
        }
        else {
            log.warn("User with ID {} not found for update", id);
            throw new RuntimeException("User not found");
        }
    }
    //delete user
    public void deleteUser(Long id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            log.info("User with ID {} deleted", id);
        }
        else {
            log.warn("User with ID {} not found for deletion", id);
            throw new RuntimeException("User not found");
        }
    }

    //convert userDTO to user entity
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setId(userDTO.getId());
        return user;
    }
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        return userDTO;
    }
}
