package isel.daw.DAW.Project.Issues.IssuesDal

import isel.daw.DAW.Project.Issues.IssuesDto.IssuesInputModel
import isel.daw.DAW.Project.Projects.ProjectsDal.CreateProject
import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInputModel
import java.sql.Connection
import java.sql.SQLException

/**
 *  TODO: Needs to be implemented :)
 *
 *  TODO: Decide what to do when an exception/error occurs.
 *
 *  TODO: We need to figure out what to return in this function.
 *
 *  TODO we need to make Transaction scopes
 */
class CreateIssue {

    companion object {

        fun execute(newIssue: IssuesInputModel, conn: Connection) {
            throw NotImplementedError("TODO!")
        }
    }
}