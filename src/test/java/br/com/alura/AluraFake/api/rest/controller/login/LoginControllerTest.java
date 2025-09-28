package br.com.alura.AluraFake.api.rest.controller.login;

import br.com.alura.AluraFake.api.rest.controller.LoginController;
import br.com.alura.AluraFake.api.rest.dto.request.login.LoginDTO;
import br.com.alura.AluraFake.api.rest.dto.response.token.TokenDTO;
import br.com.alura.AluraFake.application.facade.login.LoginFacade;
import br.com.alura.AluraFake.configuration.SecurityConfigurations;
import br.com.alura.AluraFake.configuration.TokenFilterAccess;
import br.com.alura.AluraFake.domain.service.token.TokenService;
import br.com.alura.AluraFake.dummies.LoginDummyFactory;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import br.com.alura.AluraFake.util.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginFacade loginFacade;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private TokenFilterAccess tokenFilterAccess;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SecurityConfigurations securityConfigurations;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "steve.jobs@apple.com", roles = "INSTRUCTOR")
    void login_should_return_created_when_request_is_valid() throws Exception {
        LoginDTO request = LoginDummyFactory.createLoginDto();
        TokenDTO response = LoginDummyFactory.createTokenDto();

        when(loginFacade.login(request)).thenReturn(response);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonReader.read("responses/login-response.json")));
    }
}