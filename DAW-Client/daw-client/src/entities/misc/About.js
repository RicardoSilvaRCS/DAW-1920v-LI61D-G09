import React from 'react';
import { Container, Header } from 'semantic-ui-react'

class About extends React.Component {

  render() {
      return (
        <Container text>
          <Header as='h1'>About</Header>
          <p>Made by Ari, Alex e Ricardo for DAW discipline</p>
        </Container>
      )
  }

}

export default About;