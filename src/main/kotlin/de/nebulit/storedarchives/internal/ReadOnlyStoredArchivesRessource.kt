package de.nebulit.storedarchives.internal

import de.nebulit.storedarchives.StoredArchivesReadModel
import de.nebulit.storedarchives.StoredArchivesReadModelQuery
import java.util.concurrent.CompletableFuture
import mu.KotlinLogging
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StoredarchivesRessource(private var queryGateway: QueryGateway) {

  var logger = KotlinLogging.logger {}

  @CrossOrigin
  @GetMapping("/storedarchives")
  fun findReadModel(): CompletableFuture<StoredArchivesReadModel> {
    return queryGateway.query(StoredArchivesReadModelQuery(), StoredArchivesReadModel::class.java)
  }
}
