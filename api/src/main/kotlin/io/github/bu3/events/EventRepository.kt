package io.github.bu3.events

import org.springframework.data.mongodb.repository.MongoRepository

interface EventRepository : MongoRepository<Aggregate, String>