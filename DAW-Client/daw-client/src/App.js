import React from 'react';
import { Container, Header } from 'semantic-ui-react'

class App extends React.Component {

  render() {
      return (
        <Container>
          <Header as='h1'>DAW Project G09</Header>
          <p>With this application you will be able to manage your projects, share them with friends and much more.
             To start click <a href= "/register">here.</a>
          </p>
        </Container>
      )
  }

}

export default App;
