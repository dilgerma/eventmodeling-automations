package de.nebulit.storedarchives.integration

import de.nebulit.common.CommandResult
import de.nebulit.common.support.BaseIntegrationTest
import de.nebulit.common.support.RandomData
import de.nebulit.common.support.awaitUntilAssserted
import de.nebulit.domain.commands.requestreport.RequestReportCommand
import de.nebulit.domain.commands.storearchive.StoreArchiveCommand
import de.nebulit.domain.commands.storereports.StoreReportCommand
import de.nebulit.storedarchives.StoredArchivesReadModel
import de.nebulit.storedarchives.StoredArchivesReadModelQuery
import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class StoredArchivesTestIntegration : BaseIntegrationTest() {

  @Autowired private lateinit var commandGateway: CommandGateway

  @Autowired private lateinit var queryGateway: QueryGateway

  @Test
  fun `StoredArchivesTest`() {

    val aggregateId = UUID.randomUUID()

    var requestReportCommand =
        RandomData.newInstance<RequestReportCommand> { this.aggregateId = aggregateId }

    var requestReportCommandResult = commandGateway.sendAndWait<CommandResult>(requestReportCommand)

    var storeReportCommand =
        RandomData.newInstance<StoreReportCommand> { this.aggregateId = aggregateId }

    var storeReportCommandResult = commandGateway.sendAndWait<CommandResult>(storeReportCommand)

    var storeArchiveCommand =
        RandomData.newInstance<StoreArchiveCommand> { this.aggregateId = aggregateId }

    var storeArchiveCommandResult = commandGateway.sendAndWait<CommandResult>(storeArchiveCommand)

    awaitUntilAssserted {
      var readModel =
          queryGateway.query(StoredArchivesReadModelQuery(), StoredArchivesReadModel::class.java)
      // TODO add assertions
      assertThat(readModel.get().data).isNotEmpty
    }
  }
}
