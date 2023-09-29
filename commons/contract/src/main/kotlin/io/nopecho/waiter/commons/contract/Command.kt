package io.nopecho.waiter.commons.contract

interface Command {
    fun isType(clazz: Class<out Command>): Boolean {
        return this.javaClass == clazz
    }
}