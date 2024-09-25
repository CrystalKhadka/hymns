package com.hymns.hymns.service.impl;

import com.hymns.hymns.config.PasswordEncoderUtil;
import com.hymns.hymns.dto.UserDto;
import com.hymns.hymns.entity.User;
import com.hymns.hymns.repository.UserRepo;
import com.hymns.hymns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepo userRepo;
    private final JWTService jwtService;


    @Override
    public void register(UserDto userDto) {
        try {
            // Check if user already exists by email
            Optional<User> existingUser = userRepo.findByEmail(userDto.getEmail());
            if (existingUser.isPresent()) {
                throw new RuntimeException("User already exists");
            }

            // Create a new user
            User user = new User();


            // Set user details from DTO
            user.setEmail(userDto.getEmail());
            user.setPassword(PasswordEncoderUtil.getInstance().encode(
                    userDto.getPassword()
            )); // Encrypt the password
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setUsername(userDto.getUsername());
            user.setRole("U"); // User role "U"

            // Save the user to the database
            userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving user due to constraint violation", e);
            throw new RuntimeException("Email already exists or other database constraints violated");
        } catch (Exception e) {
            logger.error("An error occurred while saving the user", e);
            throw new RuntimeException("Error in saving user");
        }
    }

    @Override
    public String login(UserDto userDto) {
        try {
            // Check if user exists by email
            User user = userRepo.findByActiveEmail(userDto.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

            // Check if password matches
            boolean pass = PasswordEncoderUtil.getInstance().matches(userDto.getPassword(), user.getPassword());

            if (!pass) {
                throw new RuntimeException("Invalid password");
            }

            // generate token and return to user
//            use jwt token
            return jwtService.generateToken(user);


        } catch (RuntimeException e) {
            logger.error("Error logging in user", e);
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while logging in the user", e);
            throw new RuntimeException("Error in logging in user");
        }
    }

    @Override
    public UserDto getUserDetails(String token) {
        try {
            // Get user from token
            String email = jwtService.extractUsername(token);
            User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            // Create a new UserDto object
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setUsername(user.getUsername());

            return userDto;
        } catch (Exception e) {
            logger.error("Error getting user details", e);
            throw new RuntimeException("Error in getting user details");
        }
    }
}
