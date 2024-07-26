package de.nebulit.publishedarchives.internal

import de.nebulit.publishedarchives.PublishedArchivesReadModel
import org.springframework.stereotype.Component
import de.nebulit.publishedarchives.internal.PublishedArchivesReadModelRepository
import org.axonframework.queryhandling.QueryHandler
import de.nebulit.publishedarchives.PublishedArchivesReadModelQuery
import java.util.UUID;


@Component
class PublishedArchivesReadModelQueryHandler(private val repository:PublishedArchivesReadModelRepository) {

  @QueryHandler
  fun handleQuery(query: PublishedArchivesReadModelQuery): PublishedArchivesReadModel? {
      return PublishedArchivesReadModel(repository.findAll())
  }

}
