package studyassistant.model
import jakarta.persistence.*
@Entity
class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String =""
}