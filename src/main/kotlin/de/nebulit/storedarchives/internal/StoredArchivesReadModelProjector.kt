package de.nebulit.storedarchives.internal

import de.nebulit.events.ReportArchiveStoredEvent
import de.nebulit.storedarchives.StoredArchivesReadModelEntity
import java.util.UUID
import org.axonframework.eventhandling.EventHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface StoredArchivesReadModelRepository : JpaRepository<StoredArchivesReadModelEntity, UUID>

@Component
class StoredArchivesReadModelProjector(var repository: StoredArchivesReadModelRepository) {

  @EventHandler
  fun on(event: ReportArchiveStoredEvent) {
    // throws exception if not available (adjust logic)
    val entity = this.repository.findById(event.aggregateId).orElse(StoredArchivesReadModelEntity())
    entity
        .apply {
          aggregateId = event.aggregateId
          reportname = event.reportname
        }
        .also { this.repository.save(it) }
  }
}
