import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";
import LandingPage from "./components/Layout/LandingPage";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />

            <Route exact path="/" component={LandingPage} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/login" component={Login} />

            {
              // Private Routes Start
            }
            <Route exact path="/dashboard" component={Dashboard} />
            <Route exact path="/addProject" component={AddProject} />
            <Route
              exact
              path="/updateProject/:projectIdentifier"
              component={UpdateProject}
            />
            <Route
              exact
              path="/projectBoard/:projectIdentifier"
              component={ProjectBoard}
            />
            <Route
              exact
              path="/addProjectTask/:projectIdentifier"
              component={AddProjectTask}
            />
            <Route
              exact
              path="/updateProjectTask/:backlog_id/:projectIdentifier"
              component={UpdateProjectTask}
            />

            {
              // Private Routes End
            }
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
