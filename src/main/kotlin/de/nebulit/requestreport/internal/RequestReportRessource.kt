package de.nebulit.requestreport.internal

import de.nebulit.common.CommandResult
import de.nebulit.domain.commands.requestreport.RequestReportCommand
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

data class RequestReportPayload(var aggregateId: UUID, var country: String, var reportname: String)

@RestController
class RequestReportRessource(private var commandGateway: CommandGateway) {

  var logger = KotlinLogging.logger {}

  @CrossOrigin
  @PostMapping("/debug/requestreport")
  fun processDebugCommand(
      @RequestParam aggregateId: UUID,
      @RequestParam country: String,
      @RequestParam reportname: String
  ): CompletableFuture<CommandResult> {
    return commandGateway.send(RequestReportCommand(aggregateId, country, reportname))
  }

  @CrossOrigin
  @PostMapping("/requestreport/{aggregateId}")
  fun processCommand(
      @PathVariable("aggregateId") aggregateId: UUID,
      @RequestBody payload: RequestReportPayload
  ): CompletableFuture<CommandResult> {
    return commandGateway.send(
        RequestReportCommand(
            aggregateId = payload.aggregateId,
            country = payload.country,
            reportname = payload.reportname))
  }
}
