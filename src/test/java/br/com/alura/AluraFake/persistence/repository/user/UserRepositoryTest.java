package br.com.alura.AluraFake.persistence.repository.user;

import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.enums.Role;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("github")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail__should_return_existis_user() {
        User caio = new User("Caio", "caio@alura.com.br", Role.STUDENT);
        userRepository.save(caio);

        Optional<User> result = userRepository.findByEmail("caio@alura.com.br");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Caio");

        result = userRepository.findByEmail("sergio@alura.com.br");
        assertThat(result).isEmpty();
    }

    @Test
    void existsByEmail__should_return_true_when_user_existis() {
        User caio = new User("Caio", "caio@alura.com.br", Role.STUDENT);
        userRepository.save(caio);

        assertThat(userRepository.existsByEmail("caio@alura.com.br")).isTrue();
        assertThat(userRepository.existsByEmail("sergio@alura.com.br")).isFalse();
    }

}