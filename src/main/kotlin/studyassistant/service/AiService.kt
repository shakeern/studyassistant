package studyassistant.service

import org.springframework.boot.ssl.pem.PemContent
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class AiService {

    private val restClient = RestClient.create("http://localhost:11434")

    fun answerQuestion(documentContent: String, question: String): String {
        val prompt = """
            You are a study assistant.
            Answer ONLY using information found in the document below.
            If the answer is not in the document, say you could not find it in the document. 
            
            Document: 
            $documentContent
            
            Question: 
            $question
        """.trimIndent()

        val request = OllamaRequest(
            model = "llama3.2",
            prompt = prompt,
            stream = false
        )

        val response = restClient.post()
            .uri ("/api/generate")
            .body(request)
            .retrieve()
            .body(OllamaResponse::class.java)

        return response?.response ?: "No response from AI"


    }
}

data class OllamaRequest(
    var model: String,
    val prompt: String,
    val stream: Boolean = false
)

data class OllamaResponse(
    val response: String
)