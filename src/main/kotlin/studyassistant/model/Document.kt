package studyassistant.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Document(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var fileName: String = "",

    @Lob
    var content: String = "",

    var uploadedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "course_id")
    var course: Course? = null
)