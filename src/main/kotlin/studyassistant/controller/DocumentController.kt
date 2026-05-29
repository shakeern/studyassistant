package studyassistant.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import studyassistant.model.Document
import studyassistant.repository.CourseRepository
import studyassistant.repository.DocumentRepository

@RestController
@RequestMapping("/documents")
class DocumentController(
    private val documentRepository: DocumentRepository,
    private val courseRepository: CourseRepository
) {

    @GetMapping
    fun getDocuments(): List<Document> {
        return documentRepository.findAll()
    }

    @GetMapping("/course/{courseId}")
    fun getDocumentsByCourse(@PathVariable courseId: Long): List<Document> {
        return documentRepository.findByCourseId(courseId)
    }

    @PostMapping("/course/{courseId}")
    fun createDocument(
        @PathVariable courseId: Long,
        @RequestBody request: CreateDocumentRequest
    ): Document {
        val course = courseRepository.findById(courseId)
            .orElseThrow { RuntimeException("Course not found") }

        val document = Document(
            fileName = request.fileName,
            content = request.content,
            course = course
        )

        return documentRepository.save(document)
    }

    @PostMapping("/upload/{courseId}")
    fun uploadDocument(
        @PathVariable courseId: Long,
        @RequestParam("file") file: MultipartFile,
    ): Document {
        val course = courseRepository.findById(courseId)
            .orElseThrow { RuntimeException("Course not found") }
        val content = String(file.bytes)
        val fileName = file.originalFilename ?: "uploaded-file"
        val document = Document(
            fileName = fileName,
            content = content,
            course = course
        )

        return documentRepository.save(document)
    }
}

data class CreateDocumentRequest(
    val fileName: String,
    val content: String
)