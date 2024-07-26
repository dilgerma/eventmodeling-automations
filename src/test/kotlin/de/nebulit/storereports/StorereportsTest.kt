package de.nebulit.storereports

import de.nebulit.common.Event
import de.nebulit.common.support.RandomData
import de.nebulit.domain.ReportAggregate
import de.nebulit.domain.commands.storereports.StoreReportCommand
import de.nebulit.events.ArchiveRequestedEvent
import de.nebulit.events.ReportRequestedEvent
import de.nebulit.events.ReportStoredEvent
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StorereportsTest {

  private lateinit var fixture: FixtureConfiguration<ReportAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(ReportAggregate::class.java)
  }

  @Test
  fun `StorereportsTest`() {
    // GIVEN
    val events = mutableListOf<Event>()

    events.add(
        RandomData.newInstance<ArchiveRequestedEvent> {
          aggregateId = UUID.fromString("997d8c6b-9392-471b-89b8-69a33136b0ba")
          reportname = RandomData.newInstance {}
        })
    events.add(
        RandomData.newInstance<ReportRequestedEvent> {
          aggregateId = UUID.fromString("997d8c6b-9392-471b-89b8-69a33136b0ba")
          country = RandomData.newInstance {}
        })

    // WHEN
    val command =
        StoreReportCommand(
            aggregateId = UUID.fromString("997d8c6b-9392-471b-89b8-69a33136b0ba"),
            country = RandomData.newInstance {},
            filename = RandomData.newInstance {})

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
        RandomData.newInstance<ReportStoredEvent> {
          this.aggregateId = command.aggregateId
          this.filename = command.filename
          this.country = command.country
        })

    fixture
        .given(events)
        .`when`(command)
        .expectSuccessfulHandlerExecution()
        .expectEvents(*expectedEvents.toTypedArray())
  }
}
