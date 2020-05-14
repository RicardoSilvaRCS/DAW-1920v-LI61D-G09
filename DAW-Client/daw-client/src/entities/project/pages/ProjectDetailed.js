

import React from 'react';
import { Container, Header, Icon, Message, List } from 'semantic-ui-react'
import ProjectServices from '../ProjectServices';
import IssuesServices from '../../issue/IssueServices'

/*Components*/
import CreateEntityModal from '../../../components/CreateEntityModal'
import CreateIssueForm from '../../issue/pages/CreateIssueForm'



class ProjectDetailed extends React.Component {


    async componentDidMount() {
      const {projName} = this.props.match.params
      this.setState({projName: projName})
       this.getProjectDetailedInfo(projName)
    }

    async getProjectDetailedInfo(projName) {
        if(!projName) {
            this.setState({erro: "Invalid project name."})
            return
        }

        const getProjDetailsResponse = await ProjectServices.getProjectDetails(projName)
        console.log("Response received on Project Details:")
        console.log(getProjDetailsResponse)

        const getProjDetailsContent = await getProjDetailsResponse.json()
        console.log("Content of Get Project Details response:")
        console.log(getProjDetailsContent)

        if(getProjDetailsResponse.status === 200) {
            const projInfo = getProjDetailsContent.properties

            const getProjIssuesResponse = await IssuesServices.getProjectIssues(projName)
            console.log("Response received on Project Issues:")
            console.log(getProjIssuesResponse)

            const getProjIssuesContent = await getProjIssuesResponse.json()
            console.log("Content of Get Project Issues response:")
            console.log(getProjIssuesContent)

            if(getProjIssuesResponse.status === 200) {
                const projIssues = []
                getProjIssuesContent.forEach(issue => {
                    projIssues.push(issue.properties)
                });

                this.setState({projInfo: {
                    details: projInfo,
                    issues: projIssues
                }})
            } else {
                this.setState({projInfo: {
                    details: projInfo,
                    issues: []
                }})
                return
            }
        } else {
            this.setState({error: getProjDetailsContent.properties.detail})
            return
        }

    }


    renderAuthUserInfo() {
        const {projName, projInfo} = this.state
        console.log({projName, projInfo})
        

        return (           
            <Container text>
                <Header as='h1'>Project {projName} details</Header>
                <p>In this area you will be able to see the detailed information of {projName}.
                    It's properties and issues, you will also be able to edit it, delete it and add more issues. 
                </p>

                {this.state.message && 
                <Message>
                    <Message.Header>{this.state.message}</Message.Header>
                </Message>
                }
                {this.state.error && 
                    <Message negative>
                        <Message.Header>{this.state.error}</Message.Header>
                    </Message>
                }
                <Header as='h2'>Details</Header>
                {projInfo.details && (
                <List>
                    <List.Item>
                        <List.Icon name="keyboard"/>
                        <List.Content>
                            <List.Header>Description:</List.Header>
                            <List.Description>{projInfo.details.descr}</List.Description>
                        </List.Content>
                    </List.Item>
                    <List.Item>
                        <List.Icon name="tags"/>
                        <List.Content>
                            <List.Header>Labels:</List.Header>
                            {projInfo.details.labels.length <= 0 && (
                               <List.Description>No labels.</List.Description>     
                            )}
                            {projInfo.details.labels.length > 0 && (
                               <List.List>
                                   {projInfo.details.labels.map((label) => (
                                       <List.Item>
                                            <List.Content>
                                                <List.Description>{label}</List.Description>
                                            </List.Content>
                                        </List.Item>
                                   ))}
                               </List.List>   
                            )}
                        </List.Content>
                    </List.Item>
                    <List.Item>
                        <List.Icon name='caret right'/>
                        <List.Content>
                            <List.Header>Initial State:</List.Header>
                            <List.Description>{projInfo.details.initstate}</List.Description>
                        </List.Content>
                    </List.Item>
                    <List.Item>
                        <List.Icon name="angle double right"/>
                        <List.Content>
                            <List.Header>Possible States:</List.Header>
                            {projInfo.details.states.length <= 0 && (
                               <List.Description>No states.</List.Description>     
                            )}
                            {projInfo.details.states.length > 0 && (
                               <List.List>
                                   {projInfo.details.states.map((state) => (
                                       <List.Item>
                                            <List.Content>
                                                <List.Description>{state}</List.Description>
                                            </List.Content>
                                        </List.Item>
                                   ))}
                               </List.List>   
                            )}
                        </List.Content>
                    </List.Item>
                    <List.Item>
                        <List.Icon name="exchange"/>
                        <List.Content>
                            <List.Header>Possible State Transitions:</List.Header>
                            {projInfo.details.transitions.length <= 0 && (
                               <List.Description>No transitions.</List.Description>     
                            )}
                            {projInfo.details.transitions.length > 0 && (
                               <List.List>
                                   {projInfo.details.transitions.map((trans) => (
                                       <List.Item>
                                            <List.Content>
                                            <List.Description>
                                                {trans.first}
                                                <Icon name="caret right"/>
                                                {trans.second}
                                            </List.Description>
                                            </List.Content>
                                        </List.Item>
                                   ))}
                               </List.List>   
                            )}
                        </List.Content>
                    </List.Item>
                </List>
                )
                }
                <Header as='h2'>Issues of {projName}</Header>
                {projInfo.issues.length > 0 &&   
                <Container>
                  <p>Here you can see {projName} issues.
                    Click on them to see their details.
                  </p>
                  <br></br>
                  <CreateEntityModal entity="Issue">
                    <CreateIssueForm />
                  </CreateEntityModal>
                  <br></br>
                  <List size='large' divided>
                    {projInfo.issues.map( (it) => (
                      <List.Item key={it.name}>
                      <List.Icon name='file' size='big' verticalAlign='middle' />
                      <List.Content>
                          <List.Header as='a' style={{fontSize: "20px"}} href={`/issues/${it.id}/details`}>{it.name}</List.Header>
                          <List.Description>
                            {it.descr}
                          </List.Description>  
                          <List.Description>
                            Current State: {it.state}
                          </List.Description> 
                      </List.Content>
                      </List.Item>
                    ))}
                  </List>
                </Container>      
              }
            </Container>
          )
    }

    renderNonAuthInfo() {
        return (
            <Container text>
              <Header as='h1'>Project Information</Header>
              <p>In this area you will be able to see the detailed information of a specific project.
                 It's properties and issues, you will also be able to edit it, delete it and add more issues. 
              </p>
              <p>In order to acess this area you need to <a href="/login">login</a> if
               you already have an account, or <a href="/register">register</a> if you don't.</p>
            </Container>
          )
    }


    constructor(props) {
        super(props)
        this.state = {
            projName: '',
            projInfo: {
                details: null,
                issues: []
            },
            error: null,
            message: null,
            auth: true
        }
    }


  render() {
    return this.state.auth ? this.renderAuthUserInfo() : this.renderNonAuthInfo()
  }

}

export default ProjectDetailed;