package de.nebulit.publisharchive.internal

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import de.nebulit.domain.commands.publisharchive.PublishArchiveCommand
import de.nebulit.common.CommandResult

import java.util.UUID;

import java.util.concurrent.CompletableFuture


data class PublishArchivePayload(	var aggregateId:UUID)

@RestController
class PublishArchiveRessource(private var commandGateway: CommandGateway) {

    var logger = KotlinLogging.logger {}

    
    @CrossOrigin
    @PostMapping("/debug/publisharchive")
    fun processDebugCommand(@RequestParam aggregateId:UUID):CompletableFuture<CommandResult> {
        return commandGateway.send(PublishArchiveCommand(aggregateId))
    }
    

    
       @CrossOrigin
       @PostMapping("/publisharchive/{aggregateId}")
    fun processCommand(
        @PathVariable("aggregateId") aggregateId: UUID,
        @RequestBody payload: PublishArchivePayload
    ):CompletableFuture<CommandResult> {
         return commandGateway.send(PublishArchiveCommand(			aggregateId=payload.aggregateId))
        }
       

}
