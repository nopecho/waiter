package io.nopecho.waiter.infra.reactive.redis.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate

@Configuration
class ReactiveRedisConfig(
    private val redisProperties: RedisProperties,
) {

    @Primary
    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        val configuration = RedisStandaloneConfiguration()
        configuration.hostName = redisProperties.host
        configuration.port = redisProperties.port
        configuration.username = redisProperties.username
        configuration.setPassword(redisProperties.password)
        return LettuceConnectionFactory(configuration)
    }

    @Bean
    fun reactiveStringRedisTemplate(): ReactiveStringRedisTemplate {
        return ReactiveStringRedisTemplate(reactiveRedisConnectionFactory())
    }
}