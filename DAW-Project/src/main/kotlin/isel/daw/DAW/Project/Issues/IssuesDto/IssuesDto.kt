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
        var id: Int = -1,
        var name: String = "",
        var projname: String = "",
        var descr: String = "",
        val labels: MutableList<String> = arrayListOf(),
        var state: String = "",
        var creationDate: Timestamp? = null,
        var updateDate: Timestamp? = null,
        var closeDate: Timestamp? = null
)

/**
 * Data model for the update state of an Issue
 */
class IssuesStateInputModel(val state: String)
