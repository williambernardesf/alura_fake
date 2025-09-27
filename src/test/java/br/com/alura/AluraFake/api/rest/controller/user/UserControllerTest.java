package br.com.alura.AluraFake.api.rest.controller.user;

import br.com.alura.AluraFake.api.rest.controller.UserController;
import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.application.facade.user.UserFacade;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import br.com.alura.AluraFake.util.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void newStudent__should_return_created_when_request_is_valid() throws Exception {
        NewUserDTO request = UserDummyFactory.newUserDTO();
        UserListItemDTO response = UserDummyFactory.instructorDto();

        doReturn(response).when(userFacade).newStudent(any(NewUserDTO.class));

        mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonReader.read("responses/new-user-response.json")));
    }
}