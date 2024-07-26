package de.nebulit.publisharchive

import de.nebulit.common.Event
import de.nebulit.common.support.RandomData
import de.nebulit.domain.ReportAggregate
import de.nebulit.domain.commands.publisharchive.PublishArchiveCommand
import de.nebulit.events.ArchivePublishedEvent
import de.nebulit.events.ArchiveRequestedEvent
import de.nebulit.events.ReportArchiveStoredEvent
import de.nebulit.events.ReportRequestedEvent
import de.nebulit.events.ReportStoredEvent
import java.util.UUID
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PublishArchiveTest {

  private lateinit var fixture: FixtureConfiguration<ReportAggregate>

  @BeforeEach
  fun setUp() {
    fixture = AggregateTestFixture(ReportAggregate::class.java)
  }

  @Test
  fun `PublishArchiveTest`() {
    // GIVEN
    val events = mutableListOf<Event>()

    events.add(
        RandomData.newInstance<ArchiveRequestedEvent> {
          aggregateId = UUID.fromString("22ff9c60-d7ed-4e89-9df9-29298b727c8a")
          reportname = RandomData.newInstance {}
        })
    events.add(
        RandomData.newInstance<ReportRequestedEvent> {
          aggregateId = UUID.fromString("22ff9c60-d7ed-4e89-9df9-29298b727c8a")
          country = RandomData.newInstance {}
        })
    events.add(
        RandomData.newInstance<ReportStoredEvent> {
          aggregateId = UUID.fromString("22ff9c60-d7ed-4e89-9df9-29298b727c8a")
          filename = RandomData.newInstance {}
          country = RandomData.newInstance {}
        })
    events.add(
        RandomData.newInstance<ReportArchiveStoredEvent> {
          aggregateId = UUID.fromString("22ff9c60-d7ed-4e89-9df9-29298b727c8a")
          reportname = RandomData.newInstance {}
        })

    // WHEN
    val command =
        PublishArchiveCommand(
            aggregateId = UUID.fromString("22ff9c60-d7ed-4e89-9df9-29298b727c8a"))

    // THEN
    val expectedEvents = mutableListOf<Event>()

    expectedEvents.add(
        RandomData.newInstance<ArchivePublishedEvent> {
          this.aggregateId = command.aggregateId
        })

    fixture
        .given(events)
        .`when`(command)
        .expectSuccessfulHandlerExecution()
        .expectEvents(*expectedEvents.toTypedArray())
  }
}
