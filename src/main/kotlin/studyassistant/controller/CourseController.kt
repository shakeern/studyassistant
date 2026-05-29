package studyassistant.controller
import org.springframework.web.bind.annotation.*
import studyassistant.model.Course
import studyassistant.repository.CourseRepository


@RestController
@RequestMapping("/courses")
class CourseController(val courseRepository: CourseRepository) {
    @GetMapping
    fun getCourses(): List<Course> {
        return courseRepository.findAll()
    }

    @PostMapping
    fun addCourse(@RequestBody course: Course): Course {
        return courseRepository.save(course)
    }
}