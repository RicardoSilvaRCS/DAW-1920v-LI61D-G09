import React from 'react';
import { Comment,Form,Button,Message } from 'semantic-ui-react'

/*Services*/
import CommentServices from './CommentServices'

/*Auxiliar modules*/
import formatDate from '../AuxiliarModules/DateFormat'

class Comments extends React.Component {

    constructor(props) {
        super(props)

        const comments = this.props.comments
        const issueId = this.props.issueId

        this.state = {
            comment : '',
            comments : comments,
            issueId : issueId,
        }
    }

    render (){
        const {comment} = this.state
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
                    <Form.Button primary content="Add Comment"/>
                </Form>
            </Comment.Group>
        )
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
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    handleSubmit = () => {
        this.setState( {} , this.handleCommentCreation)
    }
}

export default Comments