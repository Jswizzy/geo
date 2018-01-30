package com.book.geo.repository

import com.book.geo.db.Messages
import com.book.geo.db.within
import com.book.geo.domain.Message
import com.book.geo.utils.getMessage
import com.book.geo.utils.insertQuery
import org.jetbrains.exposed.sql.*
import org.postgis.PGbox2d
import org.postgis.Point
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Basic CRUD operations related to Geospatial
 */
interface CrudRepository<T, K> {
    /**
     * Creates the table
     */
    fun createTable()

    /**
     * Insert the item
     */
    fun insert(t: T): T

    /**
     * Get list of all the items
     */
    fun findAll(): Iterable<T>

    /**
     * Delete all the items
     */
    fun deleteAll(): Int

    /**
     * Get list of items in the specified box
     */
    fun findByBoundingBox(box: PGbox2d): Iterable<T>

    /**
     * Update the location of the user
     */
    fun updateLocation(userName: K, location: Point)
}

/**
 * @inheritDoc
 */
interface MessageRepository: CrudRepository<Message, Int>

/**
 * @inheritDoc
 */
@Repository
@Transactional
class DefaultMessageRepository : MessageRepository {

    /**
     * @inheritDoc
     */
    override fun createTable() = SchemaUtils.create(Messages)

    /**
     * @inheritDoc
     */
    override fun insert(t: Message): Message {
        t.id = Messages.insert(insertQuery(t))[Messages.id]
        return t
    }

    /**
     * @inheritDoc
     */
    override fun findAll() = Messages.selectAll().map {
        it.getMessage() }

    /**
     * @inheritDoc
     */
    override fun findByBoundingBox(box: PGbox2d) = Messages.select {
        Messages.location within box }.map { it.getMessage() }

    /**
     * @inheritDoc
     */
    override fun updateLocation(id:Int, location: Point) {
        location.srid = 4326
        Messages.update({ Messages.id eq id}) { it[Messages.location] = location}
    }

    /**
     * @inheritDoc
     */
    override fun deleteAll() = Messages.deleteAll()

}