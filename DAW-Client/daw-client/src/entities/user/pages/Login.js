import React from 'react';
import { Container, Form, Header, Message} from 'semantic-ui-react'
import UserServices from '../UserServices'
import UserDataModels from '../UserDataModels'

class Login extends React.Component {
    
    state = {
        final: false,
        username: '',
        password: '',
        error: ''
    }
    

    /**
     * Since setState is asynchronous we pass a callback for our login function soo that when we try and login the state is updated correctly
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
        console.log(loginResponse)
        //If login was sucessfull, redirect to his projects, if it fails show message and keep in login page
        if(loginResponse.properties.status === 202) {
            this.props.history.push('/projects')
        } else {
            this.setState({error: 'Credentials are incorrect. Try again.'})
        }
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

  render() {
    const { username, password} = this.state

      return (
        <Container text>
            <Header as="h1">Login</Header>
            <p>Login to have acess to your projects.</p>
            {this.state.error && 
                <Message negative>
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