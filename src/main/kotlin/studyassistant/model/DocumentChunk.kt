package studyassistant.model

import jakarta.persistence.*

@Entity
class DocumentChunk (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(columnDefinition = "TEXT")
    val content: String,

    val chunkIndex: Int,

    @ManyToOne
    val document: Document
)