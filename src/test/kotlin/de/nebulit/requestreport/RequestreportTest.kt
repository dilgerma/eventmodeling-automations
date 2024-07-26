package de.nebulit.requestreport

import de.nebulit.common.Event
import de.nebulit.common.support.RandomData
import de.nebulit.domain.ReportAggregate
import de.nebulit.domain.commands.requestreport.RequestReportCommand
import de.nebulit.events.ArchiveRequestedEvent
import de.nebulit.events.ReportRequestedEvent
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RequestreportTest {

  private lateinit var fixture: FixtureConfiguration<ReportAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(ReportAggregate::class.java)
  }

  @Test
  fun `RequestreportTest`() {
    // GIVEN
    val events = mutableListOf<Event>()

    // WHEN
    val command =
        RequestReportCommand(
            aggregateId = UUID.fromString("e923ebef-4cf8-4ccb-bb40-23b21cb02ad5"),
            country = RandomData.newInstance {},
            reportname = RandomData.newInstance {})

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
        RandomData.newInstance<ArchiveRequestedEvent> {
          this.aggregateId = command.aggregateId
          this.reportname = command.reportname
        })

    expectedEvents.add(
        RandomData.newInstance<ReportRequestedEvent> {
          this.aggregateId = command.aggregateId
          this.country = command.country
        })

    fixture
        .given(events)
        .`when`(command)
        .expectSuccessfulHandlerExecution()
        .expectEvents(*expectedEvents.toTypedArray())
  }

  @Test
  fun `request several countries`() {
    // GIVEN
    val events = mutableListOf<Event>()

    // WHEN
    val command =
        RequestReportCommand(
            aggregateId = UUID.fromString("e923ebef-4cf8-4ccb-bb40-23b21cb02ad5"),
            country = "austria,germany",
            reportname = RandomData.newInstance {})

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
        RandomData.newInstance<ArchiveRequestedEvent> {
          this.aggregateId = command.aggregateId
          this.reportname = command.reportname
        })

    expectedEvents.add(
        RandomData.newInstance<ReportRequestedEvent> {
          this.aggregateId = command.aggregateId
          this.country = "austria"
        })
    expectedEvents.add(
        RandomData.newInstance<ReportRequestedEvent> {
          this.aggregateId = command.aggregateId
          this.country = "germany"
        })

    fixture
        .given(events)
        .`when`(command)
        .expectSuccessfulHandlerExecution()
        .expectEvents(*expectedEvents.toTypedArray())
  }
}
