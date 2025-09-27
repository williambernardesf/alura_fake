package br.com.alura.AluraFake.domain.service.user;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.application.mapper.UserMapper;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMappper;

    @Override
    public UserListItemDTO newStudent(NewUserDTO newUser){
        if(userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado no sistema");
        }
        var user = userMappper.toUser(newUser);
        var savedUser = userRepository.save(user);

        return userMappper.toUserListItemDto(savedUser);
    }

    @Override
    public UserListItemDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!user.isInstructor()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a instructor");
        }

        return userMappper.toUserListItemDto(user);
    }
}