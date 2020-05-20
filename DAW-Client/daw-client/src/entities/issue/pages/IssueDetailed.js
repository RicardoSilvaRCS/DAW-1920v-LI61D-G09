import React from 'react';
import { Container,Header ,Divider} from 'semantic-ui-react'

/*Services */
import IssueServices from '../../issue/IssueServices'
import CommentServices from '../../comment/CommentServices'

/*Components*/
import UpdateEntityModal from '../../../components/UpdateEntityModal'
import UpdateIssueStateForm from '../../issue/pages/UpdateIssueStateForm'
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
        console.log("[IssueDetailsPage] Response received on Issue Details Request:")
        console.log(getIssueDetailsResponse)

        const getIssueDetailsContent = await getIssueDetailsResponse.json()
        console.log("[IssueDetailsPage] Content of Get Issue Details response:")
        console.log(getIssueDetailsContent)

        if(getIssueDetailsResponse.status === 200) {
            const issueInfo = getIssueDetailsContent.properties

            const getIssueCommentsResponse = await CommentServices.getIssueComments(issueId)
            console.log("[IssueDetailsPage] Response received on Issue Comments Request:")
            console.log(getIssueCommentsResponse)

            const getIssueCommentsContent = await getIssueCommentsResponse.json()
            console.log("[IssueDetailsPage] Content of Get Issue Comments response:")
            console.log(getIssueCommentsContent)

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
                                    
                        <p> In this area you will be able to see the detailed information of {issue.name}.</p>
                        <p> It's properties and coments, you will also be able to edit it, delete it and add more comments.</p>
                        <IssueDetailsComponent issue={issue}/>
                        <UpdateEntityModal entity="Issue State">
                                <UpdateIssueStateForm issue={issue}/>
                        </UpdateEntityModal>
                        <Divider horizontal>Comments</Divider>
                        <ListCommentsComponent comments = {comments} issueId ={issue.id}/>
                        
                    </Container>
                }
            </Container>
        )
    }
}

export default IssueDetailed