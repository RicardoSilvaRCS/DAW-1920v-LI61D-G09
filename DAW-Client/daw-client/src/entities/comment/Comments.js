import React from 'react';
import { Comment,Form,Button,Message } from 'semantic-ui-react'

import commentServices from './CommentServices'

class Comments extends React.Component {

    constructor(props) {
        super(props)
        //If something changes here you must check the ProjectDataModels.projectCreationDataModel() to see if u need to change something
        const projDetails = this.props.project
        this.state = {
            issueId :'',
            comments :[]
        }
    }

    render (){
        const issueId = this.state.issueId
        const comments = this.state.comments
        return(
            <Comment.Group>
                {comments.map((comment) => (
                    <Comment>
                        <Comment.Avatar as='a' src='https://react.semantic-ui.com/images/avatar/small/joe.jpg' />
                        <Comment.Content>
                            <Comment.Author>Joe Henderson</Comment.Author>
                                <Comment.Metadata>
                                    <div>{comment.creationdate}</div>
                                </Comment.Metadata>
                                <Comment.Text>
                                    <p>{comment.text}</p>
                                </Comment.Text>
                        </Comment.Content>
                    </Comment>
                ))}

                <Form reply>
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
                    <Form.TextArea placeholder="Write your comment"/>
                    <Button content='Add Comment' labelPosition='left' icon='edit' primary onClick={this.handleInsertNewComment} />
                </Form>
            </Comment.Group>
        )
    }

    handleInsertNewComment () {
        
    }
}

export default Comments