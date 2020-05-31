import React from 'react';
import { Button } from 'semantic-ui-react'
import { Link } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
import {AppContext, AppContextConsumer} from '../context/AppContext'
import Logout from '../entities/user/pages/Logout'

class NavigationBar extends React.Component {

  authenticatedNavBar() {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <Link to="/" className="navbar-brand">SPGE</Link>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto">

                    <li className="nav-item">
                        <Link to="/projects" className="nav-link">My Projects</Link>
                    </li> 

                    <li className="nav-item">
                        <Link to="/about" className="nav-link">About</Link>
                    </li>   

                </ul>

                <ul className="navbar-nav ml-auto">

                    <li className="nav-item">
                        <Link to="/" className="nav-link">{this.context.authUserName}(Not working yet!)</Link>
                    </li>

                    <li className="nav-item">
                        <Logout/>
                    </li>
                </ul>
            </div>
        </nav>
      )
  }
  
  nonAuthenticatedNavBar() {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <Link to="/" className="navbar-brand">SPGE</Link>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto">

                    <li className="nav-item">
                        <Link to="/projects" className="nav-link">Projects</Link>
                    </li> 

                    <li className="nav-item">
                        <Link to="/about" className="nav-link">About</Link>
                    </li>   

                </ul>

                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link to="/register" className="nav-link">Sign Up</Link>
                    </li>

                    <li className="nav-item">
                        <Link to="/login" className="nav-link">Login</Link>
                    </li>
                </ul>
            </div>
        </nav>
      )
  }
  
  render() {
      return this.context.isAuth? this.authenticatedNavBar() : this.nonAuthenticatedNavBar()
  }

}

NavigationBar.contextType = AppContext

export default NavigationBar;