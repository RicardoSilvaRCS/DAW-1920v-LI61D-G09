import React from 'react';
import { Container, Form, Header, Message} from 'semantic-ui-react'
import { Link } from 'react-router-dom'
import UserServices from '../UserServices'
import UserDataModels from '../UserDataModels'
import {AppContext, AppContextConsumer} from '../../../context/AppContext'

class Login extends React.Component {

    /**
     * Since setState is asynchronous we pass a callback for our login function soo that when we try and login the state is updated correctly
     * In this stage we dont need anything from the response content, we just need to know the status code
     */
    handleSubmit = () => {
        this.setState({ final: false }, this.handleLogin)
    }

    async handleLogin() {
        
        const loginResponse = await UserServices.loginUser(UserDataModels.loginDataModel(this.state.username, this.state.password)) 
        //If login was sucessfull, redirect to his projects, if it fails show message and keep in login page
        if(loginResponse.status === 202) {
            let authenticationHeader = loginResponse.headers.get("authorization")
            this.context.login(authenticationHeader, this.state.username)
            this.props.history.push('/projects')
        }else if(loginResponse.status >= 500) {
            this.setState({error: 'An error occured while trying to login please try again later'})
            
        }else {
            this.setState({error: 'Credentials are incorrect. Try again.'})
        }
        this.setState({final : true})
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
            final: true,
            message: '',
            error: ''
        }
    }


  render() {
    const {username, password} = this.state

      return (
        <AppContextConsumer>
            {({isAuth, authUserName}) => isAuth? (
            <Container text>
                <Header as="h1">Login</Header>
                <Header as='h3'>You are already logged in {authUserName}!</Header>
                <p>
                    Go manage your projects <Link to="/projects">here.</Link>
                </p>
            </Container>
            )
            :
            (<Container text>
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
                        {this.state.final &&
                            <Form.Button content='Login' />
                        }
                        {!this.state.final &&
                            <Form.Button basic loading disabled={true}/> 
                        }
                    </Form.Group>
                </Form>
            </Container>
            )}
        </AppContextConsumer>
      )
  }

}

Login.contextType = AppContext

export default Login;