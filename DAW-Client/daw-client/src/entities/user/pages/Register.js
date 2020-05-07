import React from 'react';
import { Container, Form, Header} from 'semantic-ui-react'
import UserServices from '../UserServices'

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
        final: false
    }
    

    /**
     * Since setState is asynchronous we pass a callback for our login function soo that when we try and login the state is updated correctly
     */
    handleSubmit = () => {
        this.setState({ final: true}, this.handleRegister)
    }

    async handleRegister() {
        if(this.state.final) {
            console.log(this.state)
            const registerResponse = await UserServices.registerUser(this.state) 
            console.log(registerResponse)
        }
    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

  render() {
    const { username, fullname, email, age, gender, phonenumber, password} = this.state

      return (
        <Container>
            <Header as="h1">Registo</Header>
            <p>Cria uma conta para poderes gerir os teus projetos pessoais e partilhá-los com os teus amigos.</p>
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