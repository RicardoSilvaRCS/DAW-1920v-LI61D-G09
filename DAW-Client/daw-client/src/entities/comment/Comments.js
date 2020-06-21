import React from 'react';
import { Comment,Form,Message, Container } from 'semantic-ui-react'

/*Services*/
import CommentServices from './CommentServices'

/*Auxiliar modules*/
import formatDate from '../AuxiliarModules/DateFormat'

/*Components*/
import LoaderComponent from '../../components/ContentLoader'

class Comments extends React.Component {

    constructor(props) {
        super(props)

        const comments = this.props.comments
        const issueId = this.props.issueId

        this.state = {
            comment : '',
            comments : comments,
            issueId : issueId,
            isAtualizated : true,
            final: false
        }
    }

    representIssueComments () {
        const {comment,final} = this.state
        return(
            <Comment.Group>
                {this.state.message && 
                    <Message onDismiss={this.handleDismissMessage}>
                        <Message.Header>{this.state.message}</Message.Header>
                    </Message>
                }
                {this.state.error && 
                    <Message negative onDismiss={this.handleDismissError}>
                        <Message.Header>{this.state.error}</Message.Header>
                    </Message>
                }

                {this.state.comments && 
                    this.state.comments.map((comment) => (
                        <Comment>
                            <Comment.Avatar as='a' src='https://react.semantic-ui.com/images/avatar/small/matt.jpg' />
                            <Comment.Content>
                                <Comment.Author>O Maior Grande</Comment.Author>
                                    <Comment.Metadata>
                                        <div>{formatDate(comment.creationdate)}</div>
                                    </Comment.Metadata>
                                    <Comment.Text>
                                        <p>{comment.text}</p>
                                    </Comment.Text>
                            </Comment.Content>
                        </Comment>
                    ))
                }
                <Form onSubmit={this.handleSubmit}>
                    <Form.TextArea placeholder="Write your comment" name='comment' value={comment} onChange={this.handleChange}/>
                    {final && 
                        <Form.Button primary loading/>
                    }{!final && 
                        <Form.Button primary icon="plus" content="Add Comment"/>
                    }
                    
                </Form>
            </Comment.Group>
        )
    }

    waitingIssueComments () {
        return (
            <Container>
                <LoaderComponent/>
            </Container>
        )
    }

    render (){
       return this.state.isAtualizated ? this.representIssueComments() : this.waitingIssueComments()
    }

    async handleCommentCreation () {
        const comment = this.state.comment
        const issueId = this.state.issueId
        if(!comment) {
            this.setState({error: 'Please insert a text to create a comment.'})
            return
        }
        const createCommentResponse = await CommentServices.
            createComment(comment,issueId)

        const createIssueContent = await createCommentResponse.json()
        
        if(createCommentResponse.status != 200) {
            this.setState({error: createIssueContent.properties.detail})
        }
        else{
            const comments = await this.getIssueComments(issueId)
            this.setState({
                comments : comments,
                comment : '',
                final : false
            })
        }
    }

    async getIssueComments(issueId) {

        const getCommentResponse = await CommentServices.
            getIssueComments(issueId)

        const getIssueCommentsContent = await getCommentResponse.json()
        
        if(getCommentResponse.status != 200) {
            this.setState({error: getCommentResponse.properties.detail})
            return []
        }
    
        const issueComments = []
        getIssueCommentsContent.forEach(comment => {
            issueComments.push(comment.properties)
        });
        return issueComments
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    handleSubmit = () => {
        this.setState( {final:true} , this.handleCommentCreation)
    }

}

export default Comments