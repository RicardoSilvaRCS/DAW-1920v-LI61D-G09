import ServerInfo from '../../serverinfo/ServerInfo'
import CommentPaths from './CommentPaths'
import CommentModel from './CommentsModel'

const host = ServerInfo.serverHost

async function getIssueComments(issueId, authorizationToken) {
    console.log(`CommentServices.getIssueComments()`)
    const getIssueCommentsResponse = await fetch(`http://${host}${CommentPaths.getIssueCommentsPath(issueId)}`, {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        }
    })
    return getIssueCommentsResponse
} 

async function createComment(comment, issueId, authorizationToken) {
    console.log(`CommentServices.createComment()`)
    const newComment = CommentModel.commentsInputModel(comment)
    const createCommentResponse = await fetch(`http://${host}${CommentPaths.createCommentPath(issueId)}`, {
        method: 'POST',
        body : JSON.stringify(newComment),
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': authorizationToken
        }
    })
    return createCommentResponse
} 

async function updateComment() {
    throw(`CommentServices.updateComment()`)
}

async function deleteComment() {
    throw(`CommentServices.deleteComment()`)
} 

export default {
    getIssueComments,
    createComment,
    updateComment,
    deleteComment
}