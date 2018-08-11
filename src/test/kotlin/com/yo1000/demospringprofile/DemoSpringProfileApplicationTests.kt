package com.yo1000.demospringprofile

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.runApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.test.context.junit4.SpringRunner

@SpringBootApplication
class DemoSpringProfileApplication

fun main(args: Array<String>) {
	runApplication<DemoSpringProfileApplication>(*args)
}

@RunWith(SpringRunner::class)
@SpringBootTest
class DemoSpringProfileApplicationTests {
	@Autowired
	lateinit var user: User

	@Test
	fun contextLoads() {
		println("""
    		==========
    		${user.name}
    		==========
		""".trimIndent())
	}
}

class User(val name: String)

@ConditionalOnExpression("""
    '${'$'}{spring.profiles.active}' != 'alice' &&
    '${'$'}{spring.profiles.active}' != 'allie' &&
    '${'$'}{spring.profiles.active}' != 'elsie' &&
    '${'$'}{spring.profiles.active}' != 'bob'
""")
//@Profile("default")
@Configuration
class DefaultConfiguration {
	@Bean
	fun user(): User = User("Anonymous")
}

@Profile("alice", "allie", "elsie")
@Configuration
class AliceConfiguration {
	@Bean
	fun user(): User = User("Alice")
}

@Profile("bob")
@Configuration
class BobConfiguration {
	@Bean
	fun user(): User = User("Bob")
}
