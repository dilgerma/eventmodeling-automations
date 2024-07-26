package de.nebulit.storearchive.internal

import de.nebulit.common.Processor
import de.nebulit.domain.commands.storearchive.StoreArchiveCommand
import de.nebulit.events.*
import java.io.File
import java.util.UUID
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Saga
@Component
class ArchiveGeneratorProcessor : Processor {

    @Autowired
    private lateinit var commandGateway: CommandGateway

    @Autowired
    private lateinit var zipper: Zipper

    lateinit var aggregateId: UUID

    var filename: String? = null

    var processedCountries: MutableList<String> = mutableListOf()

    var requestedCountries: MutableList<String> = mutableListOf()

    var reportname: String? = null

    var filenames: MutableList<String> = mutableListOf()

    @SagaEventHandler(associationProperty = "aggregateId")
    fun on(event: ReportFailedEvent) {
        aggregateId = event.aggregateId
        processedCountries.add(event.country)
        processArchive()
    }

    @SagaEventHandler(associationProperty = "aggregateId")
    fun on(event: ReportStoredEvent) {
        aggregateId = event.aggregateId
        processedCountries.add(event.country)
        filenames.add(event.filename)
        processArchive()
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "aggregateId")
    fun on(event: ArchiveRequestedEvent) {
        aggregateId = event.aggregateId
        reportname = event.reportname
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "aggregateId")
    fun on(event: ReportArchiveStoredEvent) {
    }

    private fun processArchive() {
        if (requestedCountries.size == processedCountries.size) {
            // all countries processed
            val fileName =
                zipper.zipFiles(filenames, File.createTempFile(reportname, ".zip").absolutePath)
            commandGateway.send<StoreArchiveCommand>(
                StoreArchiveCommand(aggregateId = this.aggregateId, reportname = fileName)
            )
        }
    }

    @SagaEventHandler(associationProperty = "aggregateId")
    fun on(event: ReportRequestedEvent) {
        aggregateId = event.aggregateId
        requestedCountries.add(event.country)
    }

    /*

    )*/

}
