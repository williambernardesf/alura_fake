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
import br.com.alura.AluraFake.util.LogUtils;
import br.com.alura.AluraFake.util.validation.CourseValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseValidator courseValidator;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public CourseListItemDTO createCourse(NewCourseDTO newCourse) {
        LogUtils.info(logger, this, "createCourse", "Validating instructor with email: {}", newCourse.getEmailInstructor());

        User possibleAuthor = userRepository.findByEmail(newCourse.getEmailInstructor())
                .filter(User::isInstructor)
                .orElseThrow(() -> new IllegalArgumentException("User is not a instructor"));

        Course course = new Course(newCourse.getTitle(), newCourse.getDescription(), possibleAuthor);
        LogUtils.info(logger, this, "createCourse", "Saving course: {}", course.getTitle());

        Course savedCourse = courseRepository.save(course);
        LogUtils.info(logger, this, "createCourse", "Course saved with ID: {}", savedCourse.getId());

        return courseMapper.toCourseListItemDTO(savedCourse);
    }

    @Override
    public InstructorCoursesResponse getCoursesByInstructor(Long instructorId) {
        LogUtils.info(logger, this, "getCoursesByInstructor", "Fetching instructor with ID: {}", instructorId);
        var user = userService.getUserById(instructorId);

        LogUtils.info(logger, this, "getCoursesByInstructor", "Fetching courses for instructor ID: {}", instructorId);
        List<Course> courses = courseRepository.findByInstructorId(instructorId);

        var courseDTOs = courses.stream()
                .map(courseMapper::toInstructorCourseDTO)
                .toList();

        long totalPublished = courses.stream()
                .filter(course -> Status.PUBLISHED.equals(course.getStatus()))
                .count();

        LogUtils.info(logger, this, "getCoursesByInstructor", "Instructor: {}, Published courses: {}", user.getName(), totalPublished);

        return courseMapper.toInstructorCoursesResponse(user, courseDTOs, totalPublished);
    }

    @Override
    public CourseListItemDTO publishCourse(Long courseId) {
        LogUtils.info(logger, this, "publishCourse", "Publishing course with ID: {}", courseId);
        Course course = getCourseById(courseId);

        courseValidator.validateForPublish(course.getTasks(), course.getStatus());

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        Course saved = courseRepository.save(course);
        LogUtils.info(logger, this, "publishCourse", "Course published: {}, PublishedAt: {}", saved.getTitle(), saved.getPublishedAt());

        return courseMapper.toCourseListItemDTO(saved);
    }

    public Course getCourseById(Long id) {
        LogUtils.info(logger, this, "getCourseById", "Fetching course with ID: {}", id);
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
}