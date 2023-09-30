package io.nopecho.waiter.commons.contract

interface CommandHandler {
    fun canHandle(command: Command): Boolean
    suspend fun handle(command: Command): Event
}