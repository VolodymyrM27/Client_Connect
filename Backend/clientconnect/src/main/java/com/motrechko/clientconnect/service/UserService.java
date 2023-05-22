package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.UserDTO;
import com.motrechko.clientconnect.exception.EmailExistException;
import com.motrechko.clientconnect.exception.UserNotFoundException;
import com.motrechko.clientconnect.mapper.UserMapper;
import com.motrechko.clientconnect.model.User;
import com.motrechko.clientconnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        User updatedUser = userMapper.map(userDTO);
        updatedUser.setId(user.getId()); // Keep the same ID

        if (userDTO.getEmail() != null) {
            if (userRepository.findByEmail(updatedUser.getEmail()).isPresent() &&
                    !userRepository.findByEmail(updatedUser.getEmail()).get().getId().equals(user.getId()))
                throw new EmailExistException(updatedUser.getEmail());
            user.setEmail(updatedUser.getEmail());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        }
        if (userDTO.getLanguageSettings() != null) {
            user.setLanguageSettings(updatedUser.getLanguageSettings());
        }

        user.setLastLoginDate(user.getLastLoginDate()); // these fields should not be updated
        user.setRegistrationDate(user.getRegistrationDate());

        return userMapper.map(userRepository.save(user));
    }

    public UserDTO getUserDTO(Long id){
        return userMapper.map(userRepository.findById(id).orElseThrow( () -> new UserNotFoundException(id)));
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow( () -> new UserNotFoundException(id));
    }

}
