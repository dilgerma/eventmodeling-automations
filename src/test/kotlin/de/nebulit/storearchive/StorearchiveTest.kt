package de.nebulit.storearchive

import de.nebulit.common.Event
import de.nebulit.common.support.RandomData
import de.nebulit.domain.ReportAggregate
import de.nebulit.domain.commands.storearchive.StoreArchiveCommand
import de.nebulit.events.ArchiveRequestedEvent
import de.nebulit.events.ReportArchiveStoredEvent
import de.nebulit.events.ReportRequestedEvent
import de.nebulit.events.ReportStoredEvent
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StorearchiveTest {

  private lateinit var fixture: FixtureConfiguration<ReportAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(ReportAggregate::class.java)
  }

  @Test
  fun `StorearchiveTest`() {
    // GIVEN
    val events = mutableListOf<Event>()

    events.add(
        RandomData.newInstance<ArchiveRequestedEvent> {
          aggregateId = UUID.fromString("f760cc6b-b17a-44d8-bfbc-1b73db02032c")
          reportname = RandomData.newInstance {}
        })
    events.add(
        RandomData.newInstance<ReportRequestedEvent> {
          aggregateId = UUID.fromString("f760cc6b-b17a-44d8-bfbc-1b73db02032c")
          country = RandomData.newInstance {}
        })
    events.add(
        RandomData.newInstance<ReportStoredEvent> {
          aggregateId = UUID.fromString("f760cc6b-b17a-44d8-bfbc-1b73db02032c")
          filename = RandomData.newInstance {}
          country = RandomData.newInstance {}
        })

    // WHEN
    val command =
        StoreArchiveCommand(
            aggregateId = UUID.fromString("f760cc6b-b17a-44d8-bfbc-1b73db02032c"),
            reportname = RandomData.newInstance {})

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
        RandomData.newInstance<ReportArchiveStoredEvent> {
          this.aggregateId = command.aggregateId
          this.reportname = command.reportname
        })

    fixture
        .given(events)
        .`when`(command)
        .expectSuccessfulHandlerExecution()
        .expectEvents(*expectedEvents.toTypedArray())
  }
}
