import React from 'react'

const AppContext = React.createContext({
    isAuth: false,
    authToken: undefined,
    authUserName: undefined,
    login: () => {},
    logout: () => {}
})

class AppContextProvider extends React.Component {

    constructor() {
        super()
        this.state = {
            isAuth: false,
            authToken: undefined,
            authUserName: undefined,
            login: this.login,
            logout: this.logout
        }
    }

    login = (authToken, authUserName) => {
        this.setState(state => {
            state.isAuth = true
            state.authToken = authToken
            state.authUserName = authUserName
            return state
        })
    }

    logout = () => {
        this.setState(state => {
            state.isAuth = false
            state.authToken = undefined
            state.authUserName = undefined
            return state
        })
    }

    render() {
        return (
            <AppContext.Provider value={this.state}>
                {this.props.children}
            </AppContext.Provider>
        )
    }
}

const AppContextConsumer = AppContext.Consumer

export {AppContext, AppContextProvider, AppContextConsumer}