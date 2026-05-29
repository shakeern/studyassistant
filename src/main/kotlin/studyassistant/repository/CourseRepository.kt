package studyassistant.repository
import org.springframework.data.jpa.repository.JpaRepository
import studyassistant.model.Course

interface CourseRepository : JpaRepository<Course, Long>