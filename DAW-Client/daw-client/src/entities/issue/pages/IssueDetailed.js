import React from 'react';
import { Container,Header ,Divider} from 'semantic-ui-react'

/*Services */
import IssueServices from '../../issue/IssueServices'
import CommentServices from '../../comment/CommentServices'

/*Components*/
import LoaderComponent from '../../../components/ContentLoader'
import IssueDetailsComponent from '../components/IssueDetail'
import ListCommentsComponent from '../../comment/Comments'

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

        const getIssueDetailsResponse = await IssueServices.getIssueDetails(issueId)
    
        const getIssueDetailsContent = await getIssueDetailsResponse.json()
       
        if(getIssueDetailsResponse.status === 200) {
            const issueInfo = getIssueDetailsContent.properties

            const getIssueCommentsResponse = await CommentServices.getIssueComments(issueId)
            
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
    }
}

export default IssueDetailed