package com.redgyro.gabblesservice

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.redgyro.gabblesservice.config.LocalDateTimeConverter
import java.time.LocalDateTime
import java.util.*

fun randomUUID() = UUID.randomUUID().toString()

fun gabbleGson(): Gson = GsonBuilder().apply {
    setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
    setPrettyPrinting()
}.create()