import React from 'react';
import { Container, Form, Header, Message} from 'semantic-ui-react'
import { Link } from 'react-router-dom'
import UserServices from '../UserServices'
import UserDataModels from '../UserDataModels'
import {AppContext, AppContextConsumer} from '../../../context/AppContext'
import HomeServices from '../../misc/HomeServices'

/**
 * Resposta á questao da discussao:
 * A partir do componente Form nao é possivel enviar pedidos POST, portanto seria necessário fazer á posteriori o pedido ao servidor, o form seria apenas a ponte para notificar o cliente que é suposto fazer o pedido
 * a partir da funçao handleSubmit e de seguida handleRegister
 * Visto nós de momento termos ficheiros c os paths todos, para serem usados pelos modulos services,
 * Para realizar este exercicio seria necessário:
 * - mudar a estrutura do services para em vez de usar os links do ficheiro predefinido, receber um path como parametro no metodo em q esse path seria o /user/reggister que foi obtido na resposta do pedido Home
 * ou
 * - O módulo services desaparecia por completo tal como foi feito neste trabalho, e fazia-se o fetch diretamente no handleRegister, usando o path guardado no state
 * 
 * Alem disso, se o recurso Home so dever ser acedido qnd se entre na paggigna inicial da app, o q é realizado nesta funçao componentDIdMount seria movida para o componente App
 * para quando se acede ao site, ter-se loggo acesso aos paths recebidos do recurso Home
 * Depois de obter esses recursos seria necessário gguarda-los para outros componentes os usarem, tendo em conta a estrutura do trabalho seria feito no context existente, uma propriedade chamada "paths"
 * por exemplo onde se guardariam os paths todos que seriam usados pelos outros componentes
 */

class Register extends React.Component {

    async componentDidMount() {
        const homeContent = await (await HomeServices.getHome()).json()
    
        console.log(homeContent)
    
        let registerpath = homeContent.resources.register.href
    
        console.log(registerpath)
    
        this.setState({registerServerPath: registerpath})
    }
    

    userGenderOptions = [
        { key: 'm', text: 'Male', value: 'male' },
        { key: 'f', text: 'Female', value: 'female' },
        { key: 'o', text: 'Other', value: 'other' },
    ]
    

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
        console.log("[RegisterPage] Response received on the Register Request:")
        console.log(registerResponse)
        //If registration was sucessfull, log the user in, if it fails show a message of error
        if(registerResponse.status === 200) {
            const loginResponse = await UserServices.loginUser(
                UserDataModels.loginDataModel(
                    this.state.username,
                    this.state.password
                )
            )
            console.log("[RegisterPage] Response received on the Login Request:")
            console.log(loginResponse)
            //If user is logged in sucessfully redirect to projects, if not send him to home page
            if(loginResponse.status === 202) {
                let authenticationHeader = loginResponse.headers.get("authorization")
                console.log("Authorization Header received:")
                console.log(authenticationHeader)
                this.context.login(authenticationHeader, this.state.username)
                this.props.history.push('/projects')
            } else {
                //TODO: Show a message of failed login error
                this.props.history.push('/')
            }
        } else {
            if(registerResponse.status === 500) {
                this.setState({error: `Username "${this.state.username}" already taken.`})
            } else {
                this.setState({error: "Registration failed please try again."})
            }
            
        }
        

    }

    handleChange = (e, { name, value }) => this.setState({ [name]: value })

    handleDismissError = () => {
        this.setState({ error: null })
    }

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            fullname: '',
            email: '',
            age: '',
            gender: '',
            phonenumber: '',
            password: '',
            final: false,
            error: '',
            registerServerPath : undefined
        }
    }

    
  render() {
    const { username, fullname, email, age, gender, phonenumber, password} = this.state

      return (
            <AppContextConsumer>
                {({isAuth, authUserName}) => isAuth? (
                    <Container text>
                        <Header as='h1'>Register</Header>
                        <Header as='h3'>You already have an account {authUserName}!</Header>
                        <p>
                            To go manage your projects you can click <Link to="/projects">here.</Link>
                        </p>
                    </Container>
                )
                :
                (<Container text>
                    <Header as="h1">Sign Up</Header>
                    <p>Create an account to manage your projects and share them with friends.</p>
                    {this.state.error && 
                        <Message negative onDismiss={this.handleDismissError}>
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
                        <p>Register path: {this.state.registerServerPath}</p>
        
                    </Form>
                </Container>)}
            </AppContextConsumer>
      )
  }

}

Register.contextType = AppContext

export default Register;

