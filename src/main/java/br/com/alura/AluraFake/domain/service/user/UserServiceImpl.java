package br.com.alura.AluraFake.domain.service.user;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.application.mapper.UserMapper;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMappper;

    @Override
    public UserListItemDTO newStudent(NewUserDTO newUser) {
        LogUtils.info(logger, this, "newStudent", "Checking if email already exists: {}", newUser.getEmail());

        if (userRepository.existsByEmail(newUser.getEmail())) {
            LogUtils.warn(logger, this, "newStudent", "Email already registered: {}", newUser.getEmail());
            throw new IllegalArgumentException("Email already registered");
        }

        var user = userMappper.toUser(newUser);
        LogUtils.info(logger, this, "newStudent", "Saving new user: {}", user.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));

        var savedUser = userRepository.save(user);
        LogUtils.info(logger, this, "newStudent", "User saved with ID: {}", savedUser.getId());

        return userMappper.toUserListItemDto(savedUser);
    }

    @Override
    public UserListItemDTO getUserById(Long userId) {
        LogUtils.info(logger, this, "getUserById", "Fetching user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    LogUtils.error(logger, this, "getUserById", "User not found with ID: {}", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        if (!user.isInstructor()) {
            LogUtils.warn(logger, this, "getUserById", "User is not an instructor: {}", user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a instructor");
        }

        LogUtils.info(logger, this, "getUserById", "User is instructor: {}", user.getName());
        return userMappper.toUserListItemDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }
}