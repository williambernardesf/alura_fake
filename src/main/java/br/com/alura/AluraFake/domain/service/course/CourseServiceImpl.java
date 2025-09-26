package br.com.alura.AluraFake.domain.service.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.application.mapper.CourseMapper;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.enums.Status;
import br.com.alura.AluraFake.domain.service.user.UserService;
import br.com.alura.AluraFake.persistence.repository.CourseRepository;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import br.com.alura.AluraFake.util.validation.CourseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{

    private final CourseValidator courseValidator;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public CourseListItemDTO createCourse(NewCourseDTO newCourse){
        User possibleAuthor = userRepository.findByEmail(newCourse.getEmailInstructor())
                .filter(User::isInstructor)
                .orElseThrow(() -> new IllegalArgumentException("User is not a instructor"));

        Course course = new Course(newCourse.getTitle(), newCourse.getDescription(), possibleAuthor);

        var savedCourse = courseRepository.save(course);

        return courseMapper.toCourseListItemDTO(savedCourse);
    }

    @Override
    public InstructorCoursesResponse getCoursesByInstructor(Long instructorId){
        var user = userService.getUserById(instructorId);

        var courses = courseRepository.findByInstructorId(instructorId);

        var courseDTOs = courses.stream()
                .map(courseMapper::toInstructorCourseDTO)
                .toList();

        long totalPublished = courses.stream()
                .filter(course -> "PUBLISHED".equalsIgnoreCase(course.getStatus().name()))
                .count();

        return courseMapper.toInstructorCoursesResponse(user, courseDTOs, totalPublished);

    }

    @Override
    public CourseListItemDTO publishCourse(Long courseId) {
        var course = getCourseById(courseId);

        courseValidator.validateForPublish(course.getTasks(), course.getStatus());

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        return courseMapper.toCourseListItemDTO(course);
    }


    public Course getCourseById(Long id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
}