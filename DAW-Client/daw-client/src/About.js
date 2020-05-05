import React from 'react';
import { Container, Header } from 'semantic-ui-react'

class App extends React.Component {

  render() {
      return (
        <Container>
          <Header as='h1'>Sobre:</Header>
          <p>Feito por Ari, Alex e Ricardo para avaliação da UC de DAW</p>
        </Container>
      )
  }

}

export default App;