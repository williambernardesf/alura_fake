package br.com.alura.AluraFake.api.rest.controller.task;

import br.com.alura.AluraFake.api.rest.controller.TaskController;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.facade.task.TaskFacade;
import br.com.alura.AluraFake.configuration.TokenFilterAccess;
import br.com.alura.AluraFake.domain.service.token.TokenService;
import br.com.alura.AluraFake.dummies.TaskDummyFactory;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import br.com.alura.AluraFake.util.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskFacade taskFacade;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private TokenFilterAccess tokenFilterAccess;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void newOpenTextExercise__should_return_created_when_request_is_valid() throws Exception {
        CreateTaskNewTextDTO request = TaskDummyFactory.createOpenTextTask();
        TaskResponseDTO response = TaskDummyFactory.taskResponseDTOOpenText();

        doReturn(response).when(taskFacade).createOpenTextExercise(any(CreateTaskNewTextDTO.class));

        mockMvc.perform(post("/task/new/opentext")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonReader.read("responses/task-opentext-response.json")));
    }

    @Test
    void newSingleChoice__should_return_created_when_request_is_valid() throws Exception {
        CreateTaskSingleChoiceDTO request = TaskDummyFactory.createSingleChoiceTask();
        TaskResponseDTO response = TaskDummyFactory.taskResponseDTO();

        doReturn(response).when(taskFacade).createSingleChoice(any(CreateTaskSingleChoiceDTO.class));

        mockMvc.perform(post("/task/new/singlechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonReader.read("responses/task-singlechoice-response.json")));
    }

    @Test
    void newMultipleChoice__should_return_created_when_request_is_valid() throws Exception {
        CreateTaskMultipleChoiceDTO request = TaskDummyFactory.createMultipleChoiceTask();
        TaskResponseDTO response = TaskDummyFactory.taskResponseDTOMultipleChoice();

        doReturn(response).when(taskFacade).createMultipleChoice(any(CreateTaskMultipleChoiceDTO.class));

        mockMvc.perform(post("/task/new/multiplechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonReader.read("responses/task-multiplechoice-response.json")));
    }
}