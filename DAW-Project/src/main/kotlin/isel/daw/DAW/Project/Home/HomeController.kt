package isel.daw.DAW.Project.Home

import isel.daw.DAW.Project.Common.*
import isel.leic.daw.hvac.home.ApiInfo
import isel.leic.daw.hvac.home.Navigation
import isel.leic.daw.hvac.home.NavigationLink
import isel.leic.daw.hvac.home.Resources
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI


@RestController
class Home {

    @GetMapping(value = [PROJECT_MANAGER_PATH], produces = [JSON_HOME_MEDIA_TYPE])
    fun getNavigation(): Navigation {
        return Navigation(
                ApiInfo("Project Manager Web API", mapOf(
                        "author" to URI("mailto:linhabenfica@slbenfica.pt"),
                        "describedBy" to URI("https://github.com/RicardoSilvaRCS/DAW-1920v-LI61D-G09/wiki/API-Documentation"))
                ),
                Resources(
                        projects = NavigationLink(PROJECTS_PATH),
                        issues = NavigationLink(ISSUES_PATH),
                        comments = NavigationLink(COMMENTS_PATH)
                )
        )
    }
}