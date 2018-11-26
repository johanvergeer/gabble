package com.redgyro.gabblesservice.events

/**
 * Class for creating Events
 *
 * First create the event class
 * ```kotlin
 * data class ClickEvent(val x: Int, val y: Int) {
 *      companion object : Event<ClickEvent>()
 *      fun emit() = emit(this)
 * }
 * ```
 *
 * Next create an event handler
 * ```kotlin
 *  println("CLICK: ${it.x} ${it.y}")
 * ```
 *
 * And call the events emit() function
 * ```kotlin
 * ClickEvent(10, 20).emit()
 * ```
 */
open class Event<T> {
    private var handlers = listOf<(T) -> Unit>()

    infix fun on(handler: (T) -> Unit) {
        handlers += handler
    }

    fun emit(event: T) {
        handlers.forEach { subscriber -> subscriber(event) }
    }
}
