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
import ProjectIssues from './entities/issue/pages/GetProjectIssues'
import ProjectDetailed from './entities/project/pages/ProjectDetailed';
import IssueDetailed from './entities/issue/pages/IssueDetailed'

/* Components Import */
import { Container } from 'semantic-ui-react'
import * as serviceWorker from './serviceWorker';
import {AppContextProvider} from './context/AppContext'
import NavigationBar from './components/NavigationBar'

ReactDOM.render(
  <Router>
    <AppContextProvider>
      <NavigationBar/>
      <Container fluid>
        <Switch>
          <Route exact path="/" component={App} />
          <Route exact path="/about" component={About} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/projects" component={ProjectIntro} />       
          <Route exact path="/projects/:projName/details" component={ProjectDetailed}/>
          <Route exact path="/projects/:projName/issues" component={ProjectIssues}/>
          <Route exact path="/issues/:issueId/details" component={IssueDetailed}/>
        </Switch>
      </Container>
    </AppContextProvider>
  </Router>
  ,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
