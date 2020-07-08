import React from 'react';
import { Container, Header } from 'semantic-ui-react'
import { Link } from 'react-router-dom'
import {AppContext, AppContextConsumer} from './context/AppContext'

class App extends React.Component {

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
               To start click <Link to="/register">here.</Link> or if you already have an account login <Link to="/login">here.</Link>             
            </p>
          </Container>)}
        </AppContextConsumer>
      )
  }

}

App.contextType = AppContext

export default App;
