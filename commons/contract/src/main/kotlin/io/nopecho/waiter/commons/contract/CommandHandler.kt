package io.nopecho.waiter.commons.contract

interface CommandHandler {
    fun canHandle(command: Command): Boolean
    fun handle(command: Command): Event
}