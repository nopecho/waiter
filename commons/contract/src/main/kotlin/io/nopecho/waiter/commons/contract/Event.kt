package io.nopecho.waiter.commons.contract

interface Event {
    fun isType(clazz: Class<out Event>): Boolean {
        return this.javaClass == clazz
    }
}