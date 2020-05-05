import React from 'react';
import { Container, Header } from 'semantic-ui-react'

class App extends React.Component {

  render() {
      return (
        <Container>
          <Header as='h1'>Projeto DAW G09</Header>
          <p>Com esta aplicação poderás gerir os teus projetos, partilhá-los com amigos e muito mais.
             Para começares clica <a href= "/register">aqui.</a>
          </p>
        </Container>
      )
  }

}

export default App;
