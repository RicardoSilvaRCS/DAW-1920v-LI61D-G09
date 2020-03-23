package isel.daw.DAW.Project.Issues.IssuesDto

import com.fasterxml.jackson.annotation.JsonCreator
import java.sql.Timestamp

/**
 * Data model for the creation of a new Issue
 */
class IssuesInputModel @JsonCreator constructor(
        val name: String,
        val projname: String,
        val descr: String,
        val labels: Array<String>
)

/**
 * Data model for the representation of an Issue
 */
class IssuesOutputModel(val name: String, val descr: String, val state: String)

/**
 * Data model for the full detailed representation of an Issue
 */
class IssuesInfoOutputModel(
        val id: Int = -1,
        val name: String = "",
        val projname: String = "",
        val descr: String = "",
        val labels: Array<String> = emptyArray(),
        val state: String = "",
        val creationDate: Timestamp? = null,
        val updateDate: Timestamp? = null,
        val closeDate: Timestamp? = null
)
