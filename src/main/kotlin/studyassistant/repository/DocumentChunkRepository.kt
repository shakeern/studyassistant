package studyassistant.repository

import org.springframework.data.jpa.repository.JpaRepository
import studyassistant.model.DocumentChunk


interface DocumentChunkRepository : JpaRepository<DocumentChunk, Long>{
    fun findByDocumentId(documentId: Long): List<DocumentChunk>
}