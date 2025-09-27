package br.com.alura.AluraFake.application.facade.user;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.domain.service.user.UserService;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserFacadeImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacadeImpl userFacade;

    @Test
    void newStudent__should_delegate_to_service_and_return_response() {
        NewUserDTO newUserDTO = UserDummyFactory.newUserDTO();
        UserListItemDTO expectedResponse = UserDummyFactory.instructorDto();

        doReturn(expectedResponse).when(userService).newStudent(any(NewUserDTO.class));

        UserListItemDTO actualResponse = userFacade.newStudent(newUserDTO);

        // Assert
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}