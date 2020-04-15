package isel.daw.DAW.Project.Common

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.sql.Timestamp

class ResourceCreatedResponse(msg: String) {

    val message: String = msg
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val status: HttpStatus = HttpStatus.CREATED

    fun sendResponse() =
            ResponseEntity
                    .status(status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseJson(
                            timestamp = timestamp,
                            date = timestamp.toString(),
                            status = status.value(),
                            msg = message
                    ))
}

class ResourceUpdatedResponse(msg: String) {

    val message: String = msg
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val status: HttpStatus = HttpStatus.OK

    fun sendResponse() =
            ResponseEntity
                    .status(status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseJson(
                            timestamp = timestamp,
                            date = timestamp.toString(),
                            status = status.value(),
                            msg = message
                    ))
}

class ResourceDeletedResponse(msg: String) {

    val message: String = msg
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
    val status: HttpStatus = HttpStatus.OK

    fun sendResponse() =
            ResponseEntity
                    .status(status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseJson(
                            timestamp = timestamp,
                            date = timestamp.toString(),
                            status = status.value(),
                            msg = message
                    ))
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseJson(
        val timestamp: Timestamp,
        val date: String,
        val status: Int,
        val msg: String
)
