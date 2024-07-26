package de.nebulit.storedarchives.internal

import de.nebulit.storedarchives.StoredArchivesReadModel
import de.nebulit.storedarchives.StoredArchivesReadModelQuery
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class StoredArchivesReadModelQueryHandler(
    private val repository: StoredArchivesReadModelRepository
) {

  @QueryHandler
  fun handleQuery(query: StoredArchivesReadModelQuery): StoredArchivesReadModel? {
    return StoredArchivesReadModel(repository.findAll())
  }
}
