package isel.daw.DAW.Project

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import isel.daw.DAW.Project.Common.APPLICATION_TYPE
import isel.daw.DAW.Project.Common.JSON_HOME_SUBTYPE
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.sql.DataSource
import org.springframework.http.MediaType
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
class DatabaseConfig {

	companion object {

		private var USERNAME : String  = "iiohkbwzrckwbj"
		private var PASSWORD : String = "f0fd58143525668f986d1ea298ed9897a9eed23fe058873ecc7b47fe104d8b9c"
		private var DATABASE_NAME :String =  "dcf5mvm87oolo9"
		private var HOST : String  = "jdbc:postgresql://ec2-46-137-177-160.eu-west-1.compute.amazonaws.com:5432/"
				.plus(DATABASE_NAME)
				.plus("?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&sslmode=require")
		
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

class SampleInterceptor : HandlerInterceptor {
	private val logger = LoggerFactory.getLogger(SampleInterceptor::class.java)

	override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
		logger.info("preHandle() called")
		return true
	}
}

@Configuration
@EnableWebMvc
class ApiConfig : WebMvcConfigurer {

	override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
		val converter = converters.find {
			it is MappingJackson2HttpMessageConverter
		} as MappingJackson2HttpMessageConverter
		converter.objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		converter.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

		val jsonHomeConverter = MappingJackson2HttpMessageConverter()
		jsonHomeConverter.objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		jsonHomeConverter.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
		jsonHomeConverter.supportedMediaTypes = listOf(MediaType(APPLICATION_TYPE, JSON_HOME_SUBTYPE))
		converters.add(jsonHomeConverter)
	}

	override fun addInterceptors(registry: InterceptorRegistry) {
		registry.addInterceptor(SampleInterceptor())
	}
}

@SpringBootApplication
class DawProjectApplication

fun main(args: Array<String>) {
	runApplication<DawProjectApplication>(*args)
}
