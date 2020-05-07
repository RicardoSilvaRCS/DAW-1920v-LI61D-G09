import React from 'react';
import { Container, Form, Header} from 'semantic-ui-react'
import UserServices from '../../../services/UserServices'

class Login extends React.Component {
    
    state = {
        final: false,
        username: '',
        password: ''
    }
    

    /**
     * Since setState is asynchronous we pass a callback for our login function soo that when we try and login the state is updated correctly
     */
    handleSubmit = () => {
        this.setState({ final: true }, this.handleLogin)
    }

    async handleLogin() {
        if(this.state.final) {
            const loginResponse = await UserServices.loginUser(this.state.username, this.state.password) 
            console.log(loginResponse)
        }
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

  render() {
    const { username, password} = this.state

      return (
        <Container>
            <Header as="h1">Login</Header>
            <p>Faz login para acederes aos teus projetos.</p>
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