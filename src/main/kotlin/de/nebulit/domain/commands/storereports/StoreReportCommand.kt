package de.nebulit.domain.commands.storereports

import de.nebulit.common.Command
import java.util.UUID
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class StoreReportCommand(
    @TargetAggregateIdentifier override var aggregateId: UUID,
    var country: String,
    var filename: String
) : Command
