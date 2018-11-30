package com.redgyro.config

import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType

@Configuration
@EnableJms
class JmsConfig {

    @Bean
    fun jmsConnectionFactory() = ActiveMQConnectionFactory().apply {
        brokerURL = "tcp://localhost:61616"
        userName = "admin"
        password = "admin"
    }

    @Bean
    fun jmsTemplate() = JmsTemplate().apply {
        connectionFactory = jmsConnectionFactory()
        messageConverter = jacksonJmsMessageConverter()
        isPubSubDomain = true
    }

    @Bean
    fun jacksonJmsMessageConverter(): MessageConverter = MappingJackson2MessageConverter()
        .apply {
            setTargetType(MessageType.TEXT)
            setTypeIdPropertyName("_type")
        }
}