package de.nebulit.domain.commands.storearchive

import de.nebulit.common.Command
import java.util.UUID
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class StoreArchiveCommand(
    @TargetAggregateIdentifier override var aggregateId: UUID,
    var reportname: String
) : Command
