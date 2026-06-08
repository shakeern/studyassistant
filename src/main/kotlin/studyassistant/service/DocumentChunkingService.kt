package studyassistant.service

import org.springframework.stereotype.Service

@Service
class DocumentChunkingService {

    fun chunkText(text: String, chunkSize: Int = 1000): List<String> {
        return text.chunked(chunkSize)
    }
}