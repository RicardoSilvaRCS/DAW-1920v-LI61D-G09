import React from 'react';
import ReactDOM from 'react-dom';
import { Route, Switch, BrowserRouter as Router } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
/* Import server settings */

/* Pages Import */
import App from './App';
import About from './entities/misc/About'
import Login from './entities/user/pages/Login';
import Register from './entities/user/pages/Register';
import ProjectIntro from './entities/project/pages/ProjectIntro'
/* Components Import */
import { Container } from 'semantic-ui-react'

import * as serviceWorker from './serviceWorker';

ReactDOM.render(
  <Router>
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <a className="navbar-brand" href="/">SPGE</a>

      <div className="collapse navbar-collapse" id="navbarSupportedContent">
        <ul className="navbar-nav mr-auto">
          
          <li className="nav-item">
            <a className="nav-link" href="/register">Registo</a>
          </li>

          <li className="nav-item">
            <a className="nav-link" href="/login">Login</a>
          </li>

          <li className="nav-item">
            <a className="nav-link" href="/projects">Projectos</a>
          </li> 

          <li className="nav-item">
            <a className="nav-link" href="/about">Info</a>
          </li>

        </ul>
      </div>
    </nav>

    <Container fluid>
      <Switch>
        <Route exact path="/" component={App} />
        <Route path="/about" component={About} />
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <Route path="/projects" component={ProjectIntro} />
      </Switch>
    </Container>
  </Router>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
