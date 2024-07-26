package de.nebulit.events

import de.nebulit.common.Event
import java.util.UUID

data class ReportRequestedEvent(var aggregateId: UUID, var country: String) : Event
