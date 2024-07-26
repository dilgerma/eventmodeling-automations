package de.nebulit.events

import de.nebulit.common.Event
import java.util.UUID

data class ReportStoredEvent(var aggregateId: UUID, var filename: String, var country: String) :
    Event
