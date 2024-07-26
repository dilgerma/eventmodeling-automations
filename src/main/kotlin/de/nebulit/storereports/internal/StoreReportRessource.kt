package de.nebulit.storereports.internal

import de.nebulit.common.CommandResult
import de.nebulit.domain.commands.storereports.StoreReportCommand
import java.util.UUID
import java.util.concurrent.CompletableFuture
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class StoreReportsPayload(var aggregateId: UUID, var country: String, var filename: String)

@RestController
class StoreReportRessource(private var commandGateway: CommandGateway) {

  var logger = KotlinLogging.logger {}

  @CrossOrigin
  @PostMapping("/debug/storereports")
  fun processDebugCommand(
      @RequestParam aggregateId: UUID,
      @RequestParam country: String,
      @RequestParam filename: String
  ): CompletableFuture<CommandResult> {
    return commandGateway.send(StoreReportCommand(aggregateId, country, filename))
  }

  @CrossOrigin
  @PostMapping("/storereports/{aggregateId}")
  fun processCommand(
      @PathVariable("aggregateId") aggregateId: UUID,
      @RequestBody payload: StoreReportsPayload
  ): CompletableFuture<CommandResult> {
    return commandGateway.send(
        StoreReportCommand(
            aggregateId = payload.aggregateId,
            country = payload.country,
            filename = payload.filename))
  }
}
