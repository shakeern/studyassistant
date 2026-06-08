package studyassistant.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import studyassistant.dto.AskRequest
import studyassistant.dto.AskResponse
import studyassistant.model.Document
import studyassistant.model.DocumentChunk
import studyassistant.repository.CourseRepository
import studyassistant.repository.DocumentChunkRepository
import studyassistant.repository.DocumentRepository
import studyassistant.service.AiService
import studyassistant.service.DocumentChunkingService
import studyassistant.service.DocumentTextExtractorService

@RestController
@RequestMapping("/documents")
class DocumentController(
    private val documentRepository: DocumentRepository,
    private val courseRepository: CourseRepository,
    private val aiService: AiService,
    private val documentTextExtracotrService: DocumentTextExtractorService,
    private val documentChunkingService: DocumentChunkingService,
    private val documentChunkRepository: DocumentChunkRepository
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
        val content = documentTextExtracotrService.extractText(file)
        val fileName = file.originalFilename ?: "uploaded-file"
        val document = Document(
            fileName = fileName,
            content = content,
            course = course
        )

        val savedDocument = documentRepository.save(document)

        //chunking
        val chunks = documentChunkingService.chunkText(content)
            .mapIndexed { index, chunk ->
                DocumentChunk(
                    content = chunk,
                    chunkIndex = index,
                    document = savedDocument
                )
            }

        documentChunkRepository.saveAll(chunks)

        return savedDocument
    }

    @PostMapping("/{documentId}/ask")
    fun askDocument(
        @PathVariable documentId: Long,
        @RequestBody request: AskRequest
    ): AskResponse {

        val document = documentRepository.findById(documentId)
            .orElseThrow { RuntimeException("Document not found") }


        val answer = aiService.answerQuestion(
            documentContent = document.content,
            question = request.question,
        )

        return AskResponse(
            answer = answer
        )
    }
}

data class CreateDocumentRequest(
    val fileName: String,
    val content: String
)

