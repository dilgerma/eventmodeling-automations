package de.nebulit.storearchive.internal

import de.nebulit.common.CommandResult
import de.nebulit.domain.commands.storearchive.StoreArchiveCommand
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

data class StoreArchivePayload(var aggregateId: UUID, var reportname: String)

@RestController
class StoreArchiveRessource(private var commandGateway: CommandGateway) {

  var logger = KotlinLogging.logger {}

  @CrossOrigin
  @PostMapping("/debug/storearchive")
  fun processDebugCommand(
      @RequestParam aggregateId: UUID,
      @RequestParam reportname: String
  ): CompletableFuture<CommandResult> {
    return commandGateway.send(StoreArchiveCommand(aggregateId, reportname))
  }

  @CrossOrigin
  @PostMapping("/storearchive/{aggregateId}")
  fun processCommand(
      @PathVariable("aggregateId") aggregateId: UUID,
      @RequestBody payload: StoreArchivePayload
  ): CompletableFuture<CommandResult> {
    return commandGateway.send(
        StoreArchiveCommand(aggregateId = payload.aggregateId, reportname = payload.reportname))
  }
}
