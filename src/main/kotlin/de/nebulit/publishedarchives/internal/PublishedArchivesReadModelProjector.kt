package de.nebulit.publishedarchives.internal

import org.axonframework.eventhandling.EventHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.util.UUID
import de.nebulit.events.ArchivePublishedEvent
import de.nebulit.events.ReportArchiveStoredEvent
import de.nebulit.publishedarchives.PublishedArchivesReadModelEntity


import mu.KotlinLogging

interface PublishedArchivesReadModelRepository : JpaRepository<PublishedArchivesReadModelEntity, UUID>

@Component
class PublishedArchivesReadModelProjector(
    var repository: PublishedArchivesReadModelRepository
) {

    
@EventHandler
fun on(event: ArchivePublishedEvent) {
    //throws exception if not available (adjust logic)
    val entity = this.repository.findById(event.aggregateId).orElse(PublishedArchivesReadModelEntity())
    entity.apply {
        			aggregateId=event.aggregateId
    }.also { this.repository.save(it) }
}

@EventHandler
fun on(event: ReportArchiveStoredEvent) {
    //throws exception if not available (adjust logic)
    val entity = this.repository.findById(event.aggregateId).orElse(PublishedArchivesReadModelEntity())
    entity.apply {
        			aggregateId=event.aggregateId
			reportname=event.reportname
    }.also { this.repository.save(it) }
}

}
