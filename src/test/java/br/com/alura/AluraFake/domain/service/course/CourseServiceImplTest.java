package br.com.alura.AluraFake.domain.service.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.application.mapper.CourseMapper;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.enums.Status;
import br.com.alura.AluraFake.domain.service.user.UserService;
import br.com.alura.AluraFake.dummies.CourseDummyFactory;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import br.com.alura.AluraFake.persistence.repository.CourseRepository;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import br.com.alura.AluraFake.util.validation.CourseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseValidator courseValidator;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CourseServiceImpl courseService;

    private User instructor;
    private UserListItemDTO instructorDto;
    private Course course;
    private NewCourseDTO newCourseDTO;

    @BeforeEach
    void setup() {
        instructorDto = UserDummyFactory.instructorDto();
        instructor = UserDummyFactory.instructor();
        course = CourseDummyFactory.courseWithInstructor(instructor);
        newCourseDTO = CourseDummyFactory.newCourseDTO();
    }

    @Test
    void createCourse_should_return_courseListItemDTO() {
        when(userRepository.findByEmail(newCourseDTO.getEmailInstructor())).thenReturn(Optional.of(instructor));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseMapper.toCourseListItemDTO(course)).thenReturn(CourseDummyFactory.courseListItemDTO());

        CourseListItemDTO dto = courseService.createCourse(newCourseDTO);

        assertNotNull(dto);
        assertEquals("Java Completo", dto.getTitle());
        verify(userRepository).findByEmail(newCourseDTO.getEmailInstructor());
        verify(courseRepository).save(any(Course.class));
        verify(courseMapper).toCourseListItemDTO(course);
    }

    @Test
    void createCourse_should_throw_exception_if_user_not_instructor() {
        User notInstructor = UserDummyFactory.notInstructor();

        when(userRepository.findByEmail(newCourseDTO.getEmailInstructor()))
                .thenReturn(Optional.of(notInstructor));

        assertThrows(IllegalArgumentException.class, () -> courseService.createCourse(newCourseDTO));
    }


    @Test
    void getCoursesByInstructor_should_return_instructorCoursesResponse() {
        when(userService.getUserById(1L)).thenReturn(instructorDto);
        when(courseRepository.findByInstructorId(1L)).thenReturn(List.of(course));
        when(courseMapper.toInstructorCourseDTO(course)).thenReturn(CourseDummyFactory.instructorCourseDTO());
        when(courseMapper.toInstructorCoursesResponse(eq(instructorDto), anyList(), eq(0L)))
                .thenReturn(CourseDummyFactory.instructorCoursesResponse());

        InstructorCoursesResponse response = courseService.getCoursesByInstructor(1L);

        assertNotNull(response);
        assertEquals(1L, response.getInstructorId());
        assertEquals("Paulo", response.getInstructorName());
        verify(userService).getUserById(1L);
        verify(courseRepository).findByInstructorId(1L);
        verify(courseMapper).toInstructorCoursesResponse(eq(instructorDto), anyList(), eq(0L));
    }

    @Test
    void publishCourse_should_set_status_and_publishedAt() {
        course.setStatus(Status.BUILDING);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toCourseListItemDTO(course)).thenReturn(CourseDummyFactory.courseListItemDTO());

        CourseListItemDTO dto = courseService.publishCourse(1L);

        assertEquals(Status.PUBLISHED, course.getStatus());
        assertNotNull(course.getPublishedAt());
        assertEquals("Java Completo", dto.getTitle());
        verify(courseMapper).toCourseListItemDTO(course);
    }

    @Test
    void getCourseById_should_throw_exception_if_not_found() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> courseService.getCourseById(99L));
        assertEquals("Course not found", ex.getMessage());
    }
}