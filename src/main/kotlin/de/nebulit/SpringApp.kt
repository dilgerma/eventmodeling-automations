package de.nebulit

import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.PropagatingErrorHandler
import org.axonframework.messaging.MessageDispatchInterceptor
import org.axonframework.messaging.MessageHandlerInterceptor
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.modulith.Modulith
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class ValidatorConfig {
  @Bean
  fun localValidatorFactoryBean(): LocalValidatorFactoryBean {
    return LocalValidatorFactoryBean()
  }
}

@Configuration
class ValidationConfig {
  @Bean
  fun beanValidationInterceptor(
      validatorFactory: LocalValidatorFactoryBean
  ): BeanValidationInterceptor<*> {
    return BeanValidationInterceptor<CommandMessage<*>>(validatorFactory)
  }
}

@Configuration
class AxonConfig {

  @Autowired
  fun configurationEventHandling(config: EventProcessingConfigurer) {
    config.registerDefaultListenerInvocationErrorHandler { PropagatingErrorHandler.instance() }
  }

  @Bean
  fun commandGateway(
      commandBus: CommandBus?,
      dispatchInterceptors: List<MessageDispatchInterceptor<CommandMessage<*>>>,
      handlerInterceptor: List<MessageHandlerInterceptor<CommandMessage<*>>>
  ): CommandGateway {
    handlerInterceptor.forEach { it -> commandBus?.registerHandlerInterceptor(it) }
    return DefaultCommandGateway.builder()
        .commandBus(commandBus!!)
        .dispatchInterceptors(*dispatchInterceptors.toTypedArray())
        .build()
  }
}

@Modulith(
    systemName = "System",
    sharedModules = ["de.nebulit.common", "de.nebulit.domain"],
    useFullyQualifiedModuleNames = true)
@EnableJpaRepositories
@SpringBootApplication
@EnableScheduling
@EntityScan(
    basePackages =
        [
            "de.nebulit",
            "org.springframework.modulith.events.jpa",
            "org.axonframework.eventhandling.tokenstore",
            "org.axonframework.eventsourcing.eventstore.jpa",
            "org.axonframework.modelling.saga.repository.jpa"])
class SpringApp {
  companion object {
    fun main(args: Array<String>) {
      runApplication<SpringApp>(*args)
    }
  }
}

fun main(args: Array<String>) {
  runApplication<SpringApp>(*args)
}
