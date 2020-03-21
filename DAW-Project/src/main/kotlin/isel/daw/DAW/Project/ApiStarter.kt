package isel.daw.DAW.Project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig {

	@Bean
	fun getDataSource(): DataSource {
		val dataSourceBuilder = DataSourceBuilder.create()
		dataSourceBuilder.driverClassName("org.postgresql.Driver")
		dataSourceBuilder.url("jdbc:postgresql://localhost:5432/IssueManagement")
		dataSourceBuilder.username("postgres")
		dataSourceBuilder.password("1q2w3e4r5t")
		return dataSourceBuilder.build()
	}

}
@SpringBootApplication
class DawProjectApplication

fun main(args: Array<String>) {
	runApplication<DawProjectApplication>(*args)
}
