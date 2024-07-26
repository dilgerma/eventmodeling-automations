package de.nebulit.publishedarchives.internal

import de.nebulit.publishedarchives.PublishedArchivesReadModel
import de.nebulit.publishedarchives.PublishedArchivesReadModelQuery
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import mu.KotlinLogging
import org.axonframework.queryhandling.QueryGateway
import java.util.concurrent.CompletableFuture
import java.util.UUID;




@RestController
class PublishedarchivesRessource(
    private var queryGateway: QueryGateway
    ) {

    var logger = KotlinLogging.logger {}

    @CrossOrigin
    @GetMapping("/publishedarchives")
                    fun findReadModel():CompletableFuture<PublishedArchivesReadModel> {
                         return queryGateway.query(PublishedArchivesReadModelQuery(), PublishedArchivesReadModel::class.java)  
                    }

}
