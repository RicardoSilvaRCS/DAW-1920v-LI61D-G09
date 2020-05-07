import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Route, Switch, BrowserRouter as Router } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
/* Pages Import */
import App from './App';
import About from './components/pages/miscpages/About'
import ProjectIntro from './components/pages/projectpages/ProjectIntro'
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
            <a className="nav-link" href="/about">About</a>
          </li>
          <li className="nav-item">
            <a className="nav-link" href="/projects">Projects</a>
          </li>
        </ul>
      </div>
    </nav>

    <Container fluid>
      <Switch>
        <Route exact path="/" component={App} />
        <Route path="/about" component={About} />
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
