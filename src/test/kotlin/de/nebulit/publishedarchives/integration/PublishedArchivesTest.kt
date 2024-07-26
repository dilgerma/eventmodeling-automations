package de.nebulit.publishedarchives.integration

import de.nebulit.common.CommandResult
import de.nebulit.common.support.BaseIntegrationTest
import de.nebulit.common.support.RandomData
import de.nebulit.common.support.awaitUntilAssserted
import de.nebulit.domain.commands.storereports.StoreReportCommand
import de.nebulit.domain.commands.storearchive.StoreArchiveCommand
import de.nebulit.domain.commands.publisharchive.PublishArchiveCommand
import de.nebulit.domain.commands.requestreport.RequestReportCommand
import de.nebulit.publishedarchives.PublishedArchivesReadModelQuery
 import de.nebulit.publishedarchives.PublishedArchivesReadModel
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.assertj.core.api.Assertions.assertThat
import java.util.*

class PublishedArchivesTestIntegration : BaseIntegrationTest() {

    @Autowired
    private lateinit var commandGateway: CommandGateway

    @Autowired
    private lateinit var queryGateway: QueryGateway

    @Test
    fun `PublishedArchivesTest`() {



        val aggregateId = UUID.randomUUID()

        var requestReportCommand = RandomData.newInstance<RequestReportCommand>{
            this.aggregateId = aggregateId
        }

        var requestReportCommandResult = commandGateway.sendAndWait<CommandResult>(requestReportCommand)

        var storeReportCommand = RandomData.newInstance<StoreReportCommand>{
            this.aggregateId = aggregateId
        }

        var storeReportCommandResult = commandGateway.sendAndWait<CommandResult>(storeReportCommand)



        var storeArchiveCommand = RandomData.newInstance<StoreArchiveCommand>{
            this.aggregateId = aggregateId
        }

        var storeArchiveCommandResult = commandGateway.sendAndWait<CommandResult>(storeArchiveCommand)



        var publishArchiveCommand = RandomData.newInstance<PublishArchiveCommand>{
            this.aggregateId = aggregateId
        }

        var publishArchiveCommandResult = commandGateway.sendAndWait<CommandResult>(publishArchiveCommand)



        awaitUntilAssserted {
            var readModel = queryGateway.query(PublishedArchivesReadModelQuery(), PublishedArchivesReadModel::class.java)
            //TODO add assertions
            assertThat(readModel.get().data).isNotEmpty
        }


    }

}
