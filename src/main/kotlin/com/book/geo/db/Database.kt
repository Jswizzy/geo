package com.book.geo.db

import org.jetbrains.exposed.sql.Table

/**
 * Message table structure
 */
object Messages : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val content = text("content")
    val location = point("location").nullable()
}