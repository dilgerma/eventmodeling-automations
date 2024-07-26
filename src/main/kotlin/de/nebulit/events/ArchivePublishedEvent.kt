package de.nebulit.events

import de.nebulit.common.Event

import java.util.UUID;


data class ArchivePublishedEvent(
    var aggregateId:UUID
) : Event
