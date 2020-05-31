import React from 'react'
import UserServices from '../UserServices'
import UserDataModels from '../UserDataModels'
import {AppContext, AppContextConsumer} from '../../../context/AppContext'
import { Button } from 'semantic-ui-react'


class Logout extends React.Component {

    constructor(props) {
        super(props)
    }

    logout = () => {
        this.context.logout()
    }


    render() {
        return (
            <Button onClick={this.logout}>Logout</Button>
        )
    }

}

Logout.contextType = AppContext

export default Logout;