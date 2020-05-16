import React from 'react';
import { Container, Form, Header, Message} from 'semantic-ui-react'
import UserServices from '../UserServices'
import UserDataModels from '../UserDataModels'

class Login extends React.Component {

    /**
     * Since setState is asynchronous we pass a callback for our login function soo that when we try and login the state is updated correctly
     * In this stage we dont need anything from the response content, we just need to know the status code
     */
    handleSubmit = () => {
        this.setState({ final: true }, this.handleLogin)
    }

    async handleLogin() {
        if(!this.state.final) {
            this.setState({error: 'Unexpected error occured. Sorry for the inconvenience please try again later.'})
            return
        }
        const loginResponse = await UserServices.loginUser(UserDataModels.loginDataModel(this.state.username, this.state.password)) 
        console.log("[LoginPage] Response received on the Login request:")
        console.log(loginResponse)
        //If login was sucessfull, redirect to his projects, if it fails show message and keep in login page
        if(loginResponse.status === 202) {
            this.props.history.push('/projects')
        } else {
            this.setState({error: 'Credentials are incorrect. Try again.'})
        }
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    handleDismissMessage = () => {
        this.setState({ message: null })
    }

    handleDismissError = () => {
        this.setState({ error: null })
    }

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: '',
            final: false,
            message: '',
            error: ''
        }
    }


  render() {
    const {username, password} = this.state

      return (
        <Container text>
            <Header as="h1">Login</Header>
            <p>Login to have acess to your projects.</p>
            {this.state.error && 
                <Message negative onDismiss={this.handleDismissError}>
                    <Message.Header>{this.state.error}</Message.Header>
                </Message>
            }
            <Form onSubmit={this.handleSubmit}>
                <Form.Group>
                    <Form.Input required icon='user' iconPosition='left' placeholder='Username' name='username' value={username} onChange={this.handleChange}/>
                    <Form.Input required icon='key' iconPosition='left' placeholder='Password' name='password' value={password} type="password" onChange={this.handleChange}/>
                    <Form.Button content='Login' />
                </Form.Group>
            </Form>
        </Container>
      )
  }

}

export default Login;