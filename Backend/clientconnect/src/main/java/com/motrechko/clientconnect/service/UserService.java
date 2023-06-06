package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.payload.NfcScanMessageRequest;
import com.motrechko.clientconnect.dto.UserDTO;
import com.motrechko.clientconnect.exception.EmailExistException;
import com.motrechko.clientconnect.exception.UserNotFoundException;
import com.motrechko.clientconnect.mapper.UserMapper;
import com.motrechko.clientconnect.model.Card;
import com.motrechko.clientconnect.model.User;
import com.motrechko.clientconnect.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
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
    private final CardService cardService;

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));


        String userEmail = userDTO.getEmail();
        if (userEmail != null) {
            User existingUser = userRepository.findByEmail(userEmail).orElse(null);
            if (existingUser != null && !existingUser.getId().equals(id)) {
                throw new EmailExistException(userEmail);
            }
            user.setEmail(userEmail);
        }

        String userPassword = userDTO.getPassword();
        if (StringUtils.isNotBlank(userPassword)) {
            user.setPassword(bCryptPasswordEncoder.encode(userPassword));
        }

        user.setLanguageSettings(userDTO.getLanguageSettings());

        return userMapper.map(userRepository.save(user));
    }

    public UserDTO getUserDTO(Long id) {
        return userMapper.map(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByCardDetails(NfcScanMessageRequest nfcScanMessageRequest){
        Card card = cardService.getCardDetail(nfcScanMessageRequest.getCardId());
        return getUser(card.getUser().getId());
    }


}
