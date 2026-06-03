package studyassistant.service


import org.springframework.stereotype.Service

import org.springframework.ai.chat.client.ChatClient



@Service
class AiService(
    private val chatClientBuilder: ChatClient.Builder,
){

    private val chatClient = chatClientBuilder.build()

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


        return chatClient.prompt()
            .user(prompt)
            .call()
            .content() ?: "No Response from AI"


    }
}

