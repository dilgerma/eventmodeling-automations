package de.nebulit.domain.commands.requestreport

import de.nebulit.common.Command
import java.util.UUID
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RequestReportCommand(
    @TargetAggregateIdentifier override var aggregateId: UUID,
    var country: String,
    var reportname: String
) : Command
