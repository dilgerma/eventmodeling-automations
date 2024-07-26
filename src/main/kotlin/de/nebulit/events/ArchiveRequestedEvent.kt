package de.nebulit.events

import de.nebulit.common.Event
import java.util.UUID

data class ArchiveRequestedEvent(var aggregateId: UUID, var reportname: String) : Event
