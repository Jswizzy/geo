package com.book.geo.domain

/**
 * It represents the message shown on the maps
 */
data class Message(
        // The message
        var content: String,
        // Location of the message
        var location: Point? = null,
        var id: Int? = null
)