import React from 'react';
import { Container } from 'semantic-ui-react'
import IssuesServices from '../../issue/IssueServices'
import IssueServices from '../../issue/IssueServices'
import CommentServices from '../../comment/CommentServices'

/*Components*/

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
        return (
            <Container text>
                <p>Loading...</p>
            </Container>
        )
    }
}

export default IssueDetailed