package de.nebulit.storereports.internal

import de.nebulit.common.Processor
import de.nebulit.domain.commands.storereports.StoreReportCommand
import de.nebulit.events.ReportFailedEvent
import de.nebulit.events.ReportRequestedEvent
import java.util.UUID
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.SagaLifecycle
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Saga
@Component
class ReportGeneratorProcessor :
    Processor {

    @Autowired
    private lateinit var pdfGenerator: PdfGenerator

    @Autowired
    private lateinit var brewApiConnector: BrewApiConnector

    @Autowired
    private lateinit var commandGateway: CommandGateway

    var aggregateId: UUID? = null

    var countries: MutableList<String> = mutableListOf()

    @StartSaga
    @SagaEventHandler(associationProperty = "aggregateId")
    fun on(event: ReportRequestedEvent) {
        aggregateId = event.aggregateId
        commandGateway.send<StoreReportCommand>(
            StoreReportCommand(event.aggregateId, event.country, event.country)
        )
        SagaLifecycle.end()
    }
}
