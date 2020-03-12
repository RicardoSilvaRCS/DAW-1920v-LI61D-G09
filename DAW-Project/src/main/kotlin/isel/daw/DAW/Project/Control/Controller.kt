package isel.daw.DAW.Project.Control

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

class Controller () {

    //Projects
    @GetMapping("/projects/getprojects")
    fun getProjects () {

    }

    @GetMapping("/projects/getproject")
    fun getProject( name : String ) {

    }

    @PostMapping("/projects/createproject")
    fun createProject( name : String , description : String , labels : List<String> , state : String , transitions : List<String> ) {

    }

    @PutMapping("/projects/updateproject")
    fun updateProject( description: String ) {

    }

    @DeleteMapping("/projects/deleteproject")
    fun deleteProject( name: String ) {

    }

    //----------------------------//---------------------------------

    //issue
    @GetMapping("/issues/getissues/{projectName}")
    fun getissues() {

    }

    @GetMapping("/issues/getissue/{projectName}")
    fun getissue( id : Int ) {

    }

    @PostMapping("/issues/createissue/{projectName}")
    fun createissue() {

    }

    @PutMapping("/issues/updateissue/{id}")
    fun updateissueInfo() {

    }

    @PutMapping("/issues/updateissue/{id}/updatestate")
    fun updateissuetate() {

    }

    @DeleteMapping("/issues/deleteissue/{id}")
    fun deleteissue() {

    }

    //----------------------------//---------------------------------

    //Comments

    @GetMapping("/comments/getcomments/{taskId}")
    fun getComments() {

    }

    @PostMapping("/comments/createcomment/{taskId}")
    fun createComment() {

    }

    @DeleteMapping("/comments/deletecomment/{commentId}")
    fun deleteComment () {

    }

}