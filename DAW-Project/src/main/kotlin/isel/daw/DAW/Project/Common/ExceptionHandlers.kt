package isel.daw.DAW.Project.Common

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.Timestamp


@ControllerAdvice
class APIExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(ApiException::class)
    fun handleException(ex: ApiException) =
            ResponseEntity
                    .status(ex.status)
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                    .body(ProblemJson(
                            timestamp = ex.timestamp,
                            date = ex.date,
                            type = ex.type,
                            title = ex.title,
                            detail = ex.detail,
                            status = ex.status.value()
                    ))
}

@ControllerAdvice
class OtherExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Any> {
        val timestamp =  Timestamp(System.currentTimeMillis())
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ProblemJson(
                        timestamp =  Timestamp(System.currentTimeMillis()),
                        date = timestamp.toString(),
                        type = "/internal/problems/internal-error",
                        title = "Internal Error",
                        detail = ex.message?: "Internal Error",
                        status = HttpStatus.INTERNAL_SERVER_ERROR.value()
                ))
    }
}

/**
 * Class used for error models, based on the [Problem Json spec](https://tools.ietf.org/html/rfc7807)
 */
/*
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProblemJson(
        val timestamp: Timestamp,
        val date: String,
        val type: String,
        val title: String,
        val detail: String,
        val status: Int
)*/

@JsonInclude(JsonInclude.Include.NON_NULL)
class ProblemJson(timestamp: Timestamp, date: String, type: String, title: String, detail: String, status: Int) {

    inner class PropertiesJson(
            val timestamp: Timestamp,
            val date: String,
            val type: String,
            val title: String,
            val detail: String,
            val status: Int
    )

    var properties: PropertiesJson? = null

    init {
        properties = PropertiesJson(timestamp, date, type, title, detail, status)
    }

}

