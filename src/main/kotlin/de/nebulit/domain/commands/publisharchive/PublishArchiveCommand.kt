package de.nebulit.domain.commands.publisharchive

import org.axonframework.modelling.command.TargetAggregateIdentifier
import de.nebulit.common.Command
import java.util.UUID;


data class PublishArchiveCommand(
    @TargetAggregateIdentifier override var aggregateId:UUID
): Command
