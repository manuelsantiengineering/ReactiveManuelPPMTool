import React, { Component } from "react";
import { Link } from "react-router-dom";
import { PropTypes } from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../actions/securityActions";
import Octicon, {
  File,
  Browser,
  MarkGithub
} from "@githubprimer/octicons-react";

class Header extends Component {
  logout() {
    this.props.logout();
    window.location.href = "/";
  }

  render() {
    const { validToken, user } = this.props.security;

    const swaggerDocumentation = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <a href="/v2/api-docs" className="nav-link">
              <Octicon icon={File} size="medium" verticalAlign="middle" />{" "}
            </a>
          </li>
          <li className="nav-item">
            <a href="/swagger-ui.html" className="nav-link">
              <Octicon icon={Browser} size="medium" verticalAlign="middle" />{" "}
            </a>
          </li>
          <li className="nav-item">
            <a
              href="https://github.com/manuelsantiengineering/ReactiveManuelPPMTool"
              className="nav-link"
            >
              <Octicon icon={MarkGithub} size="medium" verticalAlign="middle" />{" "}
            </a>
          </li>
        </ul>
      </div>
    );

    const userIsAuthenticated = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to="/dashboard" className="nav-link">
              Dashboard
            </Link>
          </li>
        </ul>

        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link to="/dashboard" className="nav-link ">
              <i className="fas fa-user-circle mr-1" />
              {user.firstName}
            </Link>
          </li>

          <li className="nav-item">
            <Link
              to="/logout"
              className="nav-link"
              onClick={this.logout.bind(this)}
            >
              Logout
            </Link>
          </li>
        </ul>
      </div>
    );

    const userIsNotAuthenticated = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link to="/register" className="nav-link ">
              <i className="fas fa-user-circle mr-1" />
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
    );

    let headerLinks;

    if (validToken && user) {
      headerLinks = userIsAuthenticated;
    } else {
      headerLinks = userIsNotAuthenticated;
    }

    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container">
          <Link to="/" className="navbar-brand">
            Project Management Tool
          </Link>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item float-left">{headerLinks}</li>
              <li className="nav-item float-right">{swaggerDocumentation}</li>
            </ul>
          </div>
        </div>
      </nav>
    );
  }
}

Header.propTypes = {
  logout: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  { logout }
)(Header);
