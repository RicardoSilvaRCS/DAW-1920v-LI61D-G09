

import React from 'react';
import { Container, Header, Message } from 'semantic-ui-react'
import IssuesServices from '../../issue/IssueServices'

/*Components*/
import ContentList from '../../../components/ContentList'
import ProjectServices from '../ProjectServices';


class ProjectDetails extends React.Component {


    getProjectDetails = async(projName) =>{
        const getProjectDetails = await ProjectServices.getProjectsDetails(projName)
        if(getProjectDetails.status === 200) {
            const detailsContent = await getProjectDetails.json()
           
            const content = []
            content.push(detailsContent.properties)
            
           return content
          } else {
            return []
          }
    }

    getProjectIssues = async (pName) => {
      console.log("I have been her with pname = " + pName)
      const getProjectIssues = await IssuesServices.getProjectIssues("Ari")
      if(getProjectIssues.status === 200) {
        const issuesContent = await getProjectIssues.json()
        const  projectIssues = []
        issuesContent.forEach(issue => {
            projectIssues.push(issue.properties)
        })
        return projectIssues
      } else {
        return []
      }
    }

    async componentDidMount() {
      const {projName} = this.props.match.params
        const projectIssues = await this.getProjectIssues(projName)
        const projectDetails = await this.getProjectDetails(projName)
        this.setState({issues: projectIssues, project : projectDetails})
    }

    /**
     * Make renders for both non auth and auth pages
     */

  renderProjectDetails() {
    return (
      <Container text>

        <Header as='h1'>Details</Header>
        {this.state.project.map((project) => (
          <div className="details">
            <p>Name : {project.name}</p>
            <p>Description : {project.descr}</p>

            {project.labels.map((label) => (
              <p>Label : {label}</p>
            ))}

            {project.states.map((state) => (
              <p>State : {state}</p>
            ))}
          </div>
        ))}


        <Header as='h1'>Issues</Header>
        {this.state.issues.length <= 0 &&
          <Container>
            <Message info>
              <Message.Header>This project has no issues</Message.Header>
            </Message>
          </Container>
        }
        {this.state.issues.length > 0 &&
          <Container>
            <p>Here you can see all the choosed project issues.
            Access all the information of your issue, edit or delete them, and add new ones to your project issues list.
                  </p>
            <ContentList members={this.state.issues} icon='bell outline' />
          </Container>
        }
        <br />
      </Container>
    )
  }

    constructor(props) {
        super(props)
        this.state = {
            auth: true,
            issues: [],
            project:[]
          
        }
    }


  render() {
    return  this.renderProjectDetails()
  }

}

export default ProjectDetails;