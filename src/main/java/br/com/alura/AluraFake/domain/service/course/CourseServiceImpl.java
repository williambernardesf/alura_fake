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
import br.com.alura.AluraFake.util.ErrorItemDTO;
import br.com.alura.AluraFake.util.validation.CourseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{

    private final CourseValidator courseValidator;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public ResponseEntity createCourse(NewCourseDTO newCourse){
        Optional<User> possibleAuthor = userRepository
                .findByEmail(newCourse.getEmailInstructor())
                .filter(User::isInstructor);

        if(possibleAuthor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("emailInstructor", "Usuário não é um instrutor"));
        }

        Course course = new Course(newCourse.getTitle(), newCourse.getDescription(), possibleAuthor.get());

        var savedCourse = courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.CREATED).build();
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