package com.book.geo.utils

import com.book.geo.db.Messages
import com.book.geo.domain.Message
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

/**
 * Builds the insert query for the specified message
 */
fun insertQuery(m: Message): Messages.(UpdateBuilder<*>) -> Unit =
        {
            if (m.id != null) it[id] = m.id
            it[content] = m.content
            it[location] = m.location
        }

/**
 * Create the message object from Result row
 * @return message
 */
fun ResultRow.getMessage() =
        Message(this[Messages.content], this[Messages.location],
                this[Messages.id])

// Other extension functions