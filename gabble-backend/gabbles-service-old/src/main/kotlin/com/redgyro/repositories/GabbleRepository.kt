package com.redgyro.repositories

import com.redgyro.models.Gabble
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GabbleRepository : CrudRepository<Gabble, String> {
    fun findByCreatedById(userId: String): List<Gabble>
}