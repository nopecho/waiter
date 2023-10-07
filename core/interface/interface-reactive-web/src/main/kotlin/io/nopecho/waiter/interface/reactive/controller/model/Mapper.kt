package io.nopecho.waiter.`interface`.reactive.controller.model

import io.nopecho.waiter.application.handlers.command.CreateMangerCommand
import io.nopecho.waiter.application.handlers.command.RegisterWaitingCommand

fun WaitingMangerCreateRequestModel.toCreateCommand(): CreateMangerCommand {
    return CreateMangerCommand(
        destinationUrl = this.destination?.url!!,
        throughput = this.throughput!!,
        startDate = this.period?.startDate!!,
        processingDate = this.period.processingDate!!,
        endDate = this.period.endDate!!
    )
}

fun WaitingRegisterRequestModel.toRegisterCommand(): RegisterWaitingCommand {
    return RegisterWaitingCommand(
        destinationUrl = this.destination?.url!!
    )
}