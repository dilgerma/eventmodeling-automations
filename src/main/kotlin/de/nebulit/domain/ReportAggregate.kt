package de.nebulit.domain

import de.nebulit.common.CommandResult
import de.nebulit.domain.commands.publisharchive.PublishArchiveCommand
import de.nebulit.domain.commands.requestreport.RequestReportCommand
import de.nebulit.domain.commands.storearchive.StoreArchiveCommand
import de.nebulit.domain.commands.storereports.StoreReportCommand
import de.nebulit.events.*
import java.util.UUID
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class ReportAggregate {

    @AggregateIdentifier
    lateinit var aggregateId: UUID

    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    @CommandHandler
    fun handle(command: RequestReportCommand): CommandResult {
        AggregateLifecycle.apply(ArchiveRequestedEvent(command.aggregateId, command.reportname))
        command.country.split(",").forEach {
            AggregateLifecycle.apply(ReportRequestedEvent(command.aggregateId, it))
        }
        return CommandResult(command.aggregateId, AggregateLifecycle.getVersion())
    }

    @CommandHandler
    fun handle(command: StoreReportCommand): CommandResult {
        AggregateLifecycle.apply(
            ReportStoredEvent(command.aggregateId, command.filename, command.country)
        )
        return CommandResult(command.aggregateId, AggregateLifecycle.getVersion())
    }

    @CommandHandler
    fun handle(command: StoreArchiveCommand): CommandResult {
        AggregateLifecycle.apply(ReportArchiveStoredEvent(command.aggregateId, command.reportname))
        return CommandResult(command.aggregateId, AggregateLifecycle.getVersion())
    }

    @CommandHandler
    fun handle(command: PublishArchiveCommand) {
        AggregateLifecycle.apply(ArchivePublishedEvent(command.aggregateId))
    }

    fun storeReport(country: String, path: String): ReportAggregate? {
        AggregateLifecycle.apply(ReportStoredEvent(this.aggregateId, path, country))
        return this
    }

    fun failReport(country: String) {
        AggregateLifecycle.apply(ReportFailedEvent(this.aggregateId, country))
    }

    @EventSourcingHandler
    fun on(event: ArchiveRequestedEvent) {
        this.aggregateId = event.aggregateId
    }
}
