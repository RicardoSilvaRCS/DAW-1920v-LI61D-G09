package isel.daw.DAW.Project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig {

	companion object {

		private var DATABASE_NAME :String =  "dcf5mvm87oolo9"
		private var HOST : String  = "jdbc:postgres://ec2-46-137-177-160.eu-west-1.compute.amazonaws.com:5432/".plus(DATABASE_NAME)
		private var USERNAME : String  = "iiohkbwzrckwbj"
		private var PASSWORD : String = "f0fd58143525668f986d1ea298ed9897a9eed23fe058873ecc7b47fe104d8b9c"
	}

	@Bean
	fun getDataSource(): DataSource {
		val dataSourceBuilder = DataSourceBuilder.create()
		dataSourceBuilder.driverClassName("org.postgresql.Driver")
		dataSourceBuilder.url(HOST)
		dataSourceBuilder.username(USERNAME)
		dataSourceBuilder.password(PASSWORD)
		return dataSourceBuilder.build()
	}

}
@SpringBootApplication
class DawProjectApplication

fun main(args: Array<String>) {
	runApplication<DawProjectApplication>(*args)
}
