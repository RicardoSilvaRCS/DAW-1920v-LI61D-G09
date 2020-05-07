import React from 'react';
import { Header } from 'semantic-ui-react'

class ProjectIntro extends React.Component {

    checkUserAuth = async () => {
        /**
         * TODO: Send requests to our own server
         */
        /*const response = await fetch('/projects/user/')
        const body = await response.json()
        console.log(`[ProjectIntro] Received body: ${JSON.stringify(body)}`)*/
        /**
         * TODO: If a user is auth change the state.auth to true or false
         */
    }

    componentDidMount() {
        this.checkUserAuth()
    }

    /**
     * Make renders for both non auth and auth pages
     */

    renderAuthUserInfo() {
        return (
            <div>
              <Header as='h1'>TODO: Present authenticated user projects</Header>
            </div>
          )
    }

    renderNonAuthInfo() {
        return (
            <div>
              <Header as='h1'>TODO: Present non authenticated user projects</Header>
            </div>
          )
    }

    constructor(props) {
        super(props)
        this.state = {
            auth: false
        }
    }

  render() {
    return this.state.auth ? this.renderAuthUserInfo() : this.renderNonAuthInfo()
  }

}

export default ProjectIntro;