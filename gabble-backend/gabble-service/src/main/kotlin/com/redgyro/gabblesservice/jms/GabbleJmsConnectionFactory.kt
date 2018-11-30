package com.redgyro.gabblesservice.jms

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Connection
import javax.jms.ConnectionFactory

class GabbleJmsConnectionFactory : ConnectionFactory {
    private val connectionFactory = ActiveMQConnectionFactory("tcp://localhost:61616")

    override fun createConnection(userName: String?, password: String?): Connection =
        connectionFactory.createConnection(userName, password)

    override fun createConnection(): Connection = connectionFactory.createConnection()
}