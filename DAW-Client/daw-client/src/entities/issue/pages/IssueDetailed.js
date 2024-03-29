import React from 'react';
import { Container,Header ,Divider} from 'semantic-ui-react'
import { Link } from 'react-router-dom'

/*Services */
import IssueServices from '../../issue/IssueServices'
import CommentServices from '../../comment/CommentServices'

/*Components*/
import LoaderComponent from '../../../components/ContentLoader'
import IssueDetailsComponent from '../components/IssueDetail'
import ListCommentsComponent from '../../comment/Comments'
import {AppContext, AppContextConsumer} from '../../../context/AppContext'

class IssueDetailed extends React.Component {

    async componentDidMount() {
        const {issueId} = this.props.match.params
        this.setState({issueId: issueId})
        this.getIssueDetailedInfo(issueId)
    }

    async getIssueDetailedInfo(issueId) {
        if(!issueId) {
            this.setState({error: "Invalid issueId received. Please select a valid issue"})
            return
        }

        const getIssueDetailsResponse = await IssueServices.getIssueDetails(issueId, this.context.authToken)
    
        const getIssueDetailsContent = await getIssueDetailsResponse.json()
        
        if(getIssueDetailsResponse.status === 200) {
            const issueInfo = getIssueDetailsContent.properties

            const getIssueCommentsResponse = await CommentServices.getIssueComments(issueId, this.context.authToken)
            
            const getIssueCommentsContent = await getIssueCommentsResponse.json()

            if(getIssueCommentsResponse.status === 200) {
                const issueComments = []
                getIssueCommentsContent.forEach(comment => {
                    issueComments.push(comment.properties)
                });

                this.setState({issueInfo: {
                    details: issueInfo,
                    comments: issueComments
                }})
            } else {
                this.setState({issueInfo: {
                    details: issueInfo,
                    comments: []
                }})
                return
            }

        } else {
            this.setState({error: getIssueDetailsContent.properties.detail})
            return
        }
    }

    constructor(props) {
        super(props)
        this.state = {
            issueId: undefined,
            issueInfo: {
                details: undefined,
                comments: []
            },
            error: undefined,
            message: undefined
        }
    }

    render() {
        const issue = this.state.issueInfo.details 
        const comments = this.state.issueInfo.comments
        return (
            <AppContextConsumer>
                {({isAuth}) => isAuth ? (
                <Container text>
                    {!issue && 
                        <LoaderComponent/>
                    }
                    { issue &&
                        <Container>
                            <Header as='h1'>Issue {issue.name} details</Header>
                                    
                            <IssueDetailsComponent issue={issue}/>

                            <Divider horizontal>Comments</Divider>
                            <ListCommentsComponent comments = {comments} issueId ={issue.id}/>
                        
                        </Container>
                    }           
                </Container>
                )
                : (
                    <Container text>
                        <Header as="h1">Issue Details</Header>
                        <p>Here you will be able to see the complete details of an Issue.
                           For that you must <Link to="/register">have an account</Link> or be <Link to="/login">logged in</Link> if you already have one. 
                        </p>
                    </Container>

                )}
            </AppContextConsumer>
        )
    }
}

IssueDetailed.contextType = AppContext

export default IssueDetailed