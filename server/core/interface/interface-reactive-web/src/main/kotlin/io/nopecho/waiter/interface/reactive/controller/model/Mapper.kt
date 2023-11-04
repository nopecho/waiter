package io.nopecho.waiter.`interface`.reactive.controller.model

import io.nopecho.waiter.application.handlers.command.CreateMangerCommand
import io.nopecho.waiter.application.handlers.command.RegisterWaitingCommand

fun WaitingMangerCreateRequestModel.toCreateCommand(): CreateMangerCommand {
    return CreateMangerCommand(
        destinationUrl = this.destination?.url!!,
        throughput = this.throughput!!,
        openDate = this.period?.startDate!!,
        startDate = this.period.processingDate!!,
        closeDate = this.period.endDate!!
    )
}

fun WaitingRegisterRequestModel.toRegisterCommand(): RegisterWaitingCommand {
    return RegisterWaitingCommand(
        destinationUrl = this.destination?.url!!
    )
}