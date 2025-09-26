package br.com.alura.AluraFake.api.rest.controller.course;

import br.com.alura.AluraFake.api.rest.controller.CourseController;
import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.application.facade.course.CourseFacade;
import br.com.alura.AluraFake.dummies.CourseDummyFactory;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseFacade courseFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void newCourse__should_return_created_when_request_is_valid() throws Exception {
        NewCourseDTO newCourseDTO = CourseDummyFactory.newCourseDTO();
        CourseListItemDTO response = CourseDummyFactory.courseListItemDTO();

        doReturn(response).when(courseFacade).createCourse(any(NewCourseDTO.class));

        mockMvc.perform(post("/course/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCourseDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonReader.read("responses/new-course-response.json")));
    }

    @Test
    void getCoursesByInstructor__should_return_ok() throws Exception {
        InstructorCoursesResponse response = CourseDummyFactory.instructorCoursesResponse();
        doReturn(response).when(courseFacade).getCoursesByInstructor(1L);

        mockMvc.perform(get("/instructor/1/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonReader.read("responses/instructor-courses-response.json")));
    }

    @Test
    void publishCourse__should_return_ok() throws Exception {
        CourseListItemDTO dto = CourseDummyFactory.courseListItemDTO();
        doReturn(dto).when(courseFacade).publishCourse(1L);

        mockMvc.perform(post("/course/1/publish")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(JsonReader.read("responses/new-course-response.json")));
    }

}