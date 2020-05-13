import React from 'react';
import { Container, Header, Message } from 'semantic-ui-react'
import IssuesServices from '../IssueServices'

/*Components*/
import ContentList from '../../../components/ContentList'

class ProjectIssues extends React.Component {

    getProjectIssues = async (pName) => {
      console.log("I have been her with pname = "+pName)
      const getProjectIssues = await IssuesServices.getProjectIssues(pName)
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
        this.setState({issues: projectIssues})
    }

    /**
     * Make renders for both non auth and auth pages
     */

    renderIssuesList() {
        return (
            <Container text>      
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
                  <ContentList members={this.state.issues} icon='bell outline'/> 
                </Container>      
              }
              <br/>
            </Container>
          )
    }

    constructor(props) {
        super(props)
        this.state = {
            auth: true,
            issues: []
        }
    }


  render() {
    return  this.renderIssuesList()
  }

}

export default ProjectIssues;