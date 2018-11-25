package com.redgyro

open class Event<T> {
    private var handlers = listOf<(T) -> Unit>()

    infix fun on(handler: (T) -> Unit) {
        handlers += handler
    }

    fun emit(event: T) {
        handlers.forEach { subscriber -> subscriber(event) }
    }
}

//data class ClickEvent(val x: Int, val y: Int) {
////    companion object : Event<ClickEvent>()
////
////    fun emit() = emit(this)
////}
////
////fun main(args: Array<String>) {
////    ClickEvent on {
////        println("CLICK: ${it.x} ${it.y}")
////    }
////
////    ClickEvent on {
////        println("CLICK AGAIN: ${it.x} ${it.y}")
////    }
////
////    ClickEvent(10, 20).emit()
////}
