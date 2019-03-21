import React, { Component } from "react";
import { Link } from "react-router-dom";

class Header extends Component {
  render() {
    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container">
          <a className="navbar-brand" href="/">
            Project Management Tool
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#mobile-nav"
          >
            <span className="navbar-toggler-icon" />
          </button>

          <div className="collapse navbar-collapse" id="mobile-nav">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item">
                <a className="nav-link" href="/dashboard">
                  Dashboard
                </a>
              </li>
            </ul>

            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to="/register" className="nav-link ">
                  Sign Up
                </Link>
              </li>

              <li className="nav-item">
                <Link to="/login" className="nav-link">
                  Login
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    );
  }
}

export default Header;
