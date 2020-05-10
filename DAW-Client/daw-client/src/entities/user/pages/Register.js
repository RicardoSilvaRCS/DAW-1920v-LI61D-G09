import React from 'react';
import { Container, Form, Header, Message} from 'semantic-ui-react'
import UserServices from '../UserServices'
import UserDataModels from '../UserDataModels'

class Register extends React.Component {

    userGenderOptions = [
        { key: 'm', text: 'Male', value: 'male' },
        { key: 'f', text: 'Female', value: 'female' },
        { key: 'o', text: 'Other', value: 'other' },
    ]
    
    state = {
        username: '',
        fullname: '',
        email: '',
        age: '',
        gender: '',
        phonenumber: '',
        password: '',
        final: false,
        error: ''
    }
    

    /**
     * Since setState is asynchronous we pass a callback for our login function soo that when we try and login the state is updated correctly
     */
    handleSubmit = () => {
        this.setState({ final: true}, this.handleRegister)
    }

    async handleRegister() {
        if(!this.state.final) {
            this.setState({error: 'Unexpected error occured. Sorry for the inconvenience please try again later.'})
            return
        }
        const registerResponse = await UserServices.registerUser(
            UserDataModels.registerDataModel(
                this.state.username,
                this.state.fullname,
                this.state.email,
                this.state.age,
                this.state.gender,
                this.state.phonenumber,
                this.state.password
            )
        )
        console.log(registerResponse)
        //If registration was sucessfull, log the user in, if it fails show a message of error
        if(registerResponse.properties.status === "CREATED") {
            const loginResponse = await UserServices.loginUser(
                UserDataModels.loginDataModel(
                    this.state.username,
                    this.state.password
                )
            )
            console.log(loginResponse)
            //If user is logged in sucessfully redirect to projects, if not send him to home page
            if(loginResponse.properties.status === 202) {
                this.props.history.push('/projects')
            } else {
                //TODO: Show a message of failed login error
                this.props.history.push('/')
            }
        } else {
            if(registerResponse.properties.status === 500) {
                this.setState({error: `Username "${this.state.username}" already taken.`})
            } else {
                this.setState({error: "Registration failed please try again."})
            }
            
        }
        

    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

  render() {
    const { username, fullname, email, age, gender, phonenumber, password} = this.state

      return (
        <Container text>
            <Header as="h1">Sign Up</Header>
            <p>Create an account to manage your projects and share them with friends.</p>
            {this.state.error && 
                <Message negative>
                    <Message.Header>{this.state.error}</Message.Header>
                </Message>
            }
            <Form onSubmit={this.handleSubmit}>
                <Form.Group>
                    <Form.Input required icon='user' iconPosition='left' placeholder='Username' name='username' value={username} onChange={this.handleChange}/>               
                </Form.Group>
                <Form.Group>
                    <Form.Input required icon='users' iconPosition='left' placeholder='Fullname' name='fullname' value={fullname} onChange={this.handleChange}/>               
                </Form.Group>
                <Form.Group>
                    <Form.Input required icon='at' iconPosition='left' placeholder='Email' name='email' value={email} onChange={this.handleChange}/>               
                </Form.Group>
                <Form.Group>
                    <Form.Input required icon='birthday' iconPosition='left' placeholder='Age' name='age' value={age} onChange={this.handleChange} type="numbers"/>               
                </Form.Group>
                <Form.Group>
                    <Form.Select required placeholder='Gender' name='gender' value={gender} options={this.userGenderOptions} onChange={this.handleChange}/>               
                </Form.Group>
                <Form.Group>
                    <Form.Input required icon='phone' iconPosition='left' placeholder='Phone Number' name='phonenumber' value={phonenumber} onChange={this.handleChange}/>
                </Form.Group>
                <Form.Group>
                    <Form.Input required icon='key' iconPosition='left' placeholder='Password' name='password' value={password} type="password" onChange={this.handleChange}/>
                </Form.Group>
                <Form.Group>
                    <Form.Button content='Registo' />
                </Form.Group>

            </Form>
        </Container>
      )
  }

}

export default Register;