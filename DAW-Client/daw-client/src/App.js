import React from 'react';
import { Container, Header } from 'semantic-ui-react'
import { Link } from 'react-router-dom'
import {AppContext, AppContextConsumer} from './context/AppContext'
import UserServices from './entities/user/UserServices';

class App extends React.Component {

 
 

  async getRegisterUrl (){
    const homeResponse = await UserServices.getHome()
      
      if(homeResponse.status === 200) {
        const homeResponseContent = await homeResponse.json()
        
        console.log("registerPath:")
        console.log(homeResponseContent.api.links.register)
        let registerPath = homeResponseContent.api.links.register
        return registerPath
      }
  }

  async componentDidMount() {
    const registerProps = await this.getRegisterUrl()
    this.setState({register: registerProps})
}

  constructor(props) {
    super(props)
    this.state = {
        register: {}
    }
}
  
  render() {
      return (
        <AppContextConsumer>
          {({isAuth, authUserName}) => isAuth? (
            <Container text>
              <Header as='h1'>DAW Project G09</Header>
              <p>Hello {authUserName}!</p>
              <p>
                To go manage your projects you can click <Link to="/projects">here.</Link>
              </p>
            </Container>
          )
          :
          (<Container text>
            <Header as='h1'>DAW Project G09</Header>
            <p>With this application you will be able to manage your projects, share them with friends and much more.
               To start click <Link to={`${this.state.register}`}>here.</Link> or if you already have an account login <Link to="/login">here.</Link>
            </p>
          </Container>)}
        </AppContextConsumer>
      )
  }

}

App.contextType = AppContext

export default App;
