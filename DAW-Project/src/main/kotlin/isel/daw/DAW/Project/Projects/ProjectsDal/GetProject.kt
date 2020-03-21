package isel.daw.DAW.Project.Projects.ProjectsDal

import isel.daw.DAW.Project.Projects.ProjectsDto.ProjectsInfoOutputModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class GetProject {

    companion object{

        private const val GET_GROUP_QUERY : String = "select projname,projdescr,projinitstate from project where projname=?"

        fun execute(projectName : String , con : Connection): ProjectsInfoOutputModel {

            var project : ProjectsInfoOutputModel;
            
            try{
                val ps : PreparedStatement = con.prepareStatement(GET_GROUP_QUERY);
                ps.use {

                    ps.setString(1,projectName)

                    var rs = ps.executeQuery()

                    rs.use {
                        while(rs.next()){
                            //perguntar se ele quer ir buscar apenas a info de um prjeto aqui e ter mais endpoints para o resto ou devolver logo a info toda (Acho melhor separar)
                            project = ProjectsInfoOutputModel(rs.getString("projname") , rs.getString("projdescr"),rs.);
                        }
                    }

                }
            }catch ( ex : SQLException){

            }

            return project;
        }

    }

}