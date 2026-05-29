package studyassistant.repository

import org.springframework.data.jpa.repository.JpaRepository
import studyassistant.model.Document

interface DocumentRepository : JpaRepository<Document, Long> {
    fun findByCourseId(courseId: Long): List<Document>
}