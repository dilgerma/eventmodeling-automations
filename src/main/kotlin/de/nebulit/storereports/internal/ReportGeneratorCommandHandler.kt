package de.nebulit.storereports.internal

import de.nebulit.domain.ReportAggregate
import de.nebulit.domain.commands.storereports.StoreReportCommand
import mu.KotlinLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.modelling.command.Repository
import org.springframework.stereotype.Component

@Component
class ReportGeneratorCommandHandler(
    val connector: BrewApiConnector,
    val pdfGenerator: PdfGenerator,
    val repository: Repository<ReportAggregate>
) {

    var logger = KotlinLogging.logger {}


    @CommandHandler
    fun handle(command: StoreReportCommand) {
        logger.info("Processing Command")

        var aggregate = repository.load(command.aggregateId.toString())
        try {

            var fileName = command.filename
            var breweries = connector.requestBrewery(command.country)

            if (breweries.size == 0) {
                logger.info { "Ignoring Country Report: ${command.country}" }
                aggregate.execute {
                    it.failReport(command.country)
                }
            }
            var path = pdfGenerator.generate(fileName, command.country, breweries)
            aggregate.execute {
                it.storeReport(command.country, path)
            }
        } catch (ex: Exception) {
            aggregate.execute {
                it.failReport(command.country)
            }
        }


    }
}
