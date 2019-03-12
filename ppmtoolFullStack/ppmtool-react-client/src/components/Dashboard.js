import React, { Component } from "react";
import Projectitem from "./Project/Projectitem";
import CreateProjectButton from "./Project/CreateProjectButton";

class Dashboard extends Component {
  render() {
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />
              <CreateProjectButton />
              <br />
              <Projectitem />
              <br />
              <hr />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Dashboard;
