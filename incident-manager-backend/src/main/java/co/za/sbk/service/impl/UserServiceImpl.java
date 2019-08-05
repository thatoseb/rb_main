package co.za.sbk.service.impl;

import co.za.sbk.domain.User;
import co.za.sbk.repository.UserRepository;
import co.za.sbk.service.UserService;
import co.za.sbk.service.dto.UserDTO;
import co.za.sbk.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserRepository userRepository;
    
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save user: {}", userDTO);
        User user = userMapper.userDTOToUser(userDTO);
        user = userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        log.debug("Request to find all users");
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserByIdNumber(UserDTO userDTO) {
        log.debug("Request to find a user with id number {}", userDTO.getIdnumber());
        return userRepository.findByIdnumber(userDTO.getIdnumber())
                .map(userMapper::userToUserDTO);
    }
}
