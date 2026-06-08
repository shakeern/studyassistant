package studyassistant.service

import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class DocumentTextExtractorService {

    fun extractText(file: MultipartFile): String {
        val fileName = file.originalFilename ?: ""

        return if (fileName.endsWith(".pdf", ignoreCase = true)){
            val pdf = Loader.loadPDF(file.bytes)
            PDFTextStripper().getText(pdf)
        } else {
            String(file.bytes)
        }
    }
}