import React from 'react'
import cookies from '../entities/AuxiliarModules/CookiesModule' 

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
            isAuth: cookies.getCookie("isAuth") || undefined,
            authToken: cookies.getCookie("authToken")||undefined,
            authUserName: cookies.getCookie("authUserName")||undefined,
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
        cookies.setCookie("authUserName",authUserName,1)
        cookies.setCookie("isAuth",true,1)
        cookies.setCookie("authToken",authToken,1)
    }

    logout = () => {
        this.setState(state => {
            state.isAuth = false
            state.authToken = undefined
            state.authUserName = undefined
            return state
        })
        cookies.clearCookie("authUserName")
        cookies.clearCookie("isAuth")
        cookies.clearCookie("authToken")
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