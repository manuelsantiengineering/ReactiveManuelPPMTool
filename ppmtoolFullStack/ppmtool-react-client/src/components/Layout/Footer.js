import React, { Component } from "react";
import { Link } from "react-router-dom";
import { PropTypes } from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../actions/securityActions";
import Octicon, { File, Browser } from "@githubprimer/octicons-react";

class Footer extends Component {
  logout() {
    this.props.logout();
    window.location.href = "/";
  }

  render() {
    const { validToken, user } = this.props.security;

    const swaggerDocumentation = (
      <div className="footer-copyright text-right py-3 " id="mobile-nav">
        <div className="col-lg-1 mb-lg-0 mb-1 float-right">
          <Link to="/v2/api-docs" className="nav-link">
            <Octicon icon={File} size="medium" verticalAlign="middle" />{" "}
            <Octicon icon={Browser} size="medium" verticalAlign="middle" />{" "}
          </Link>
        </div>
      </div>
    );

    return (
      <footer className="page-footer font-small  pt-4">
        <div className="col-lg-12 mb-lg-0 mb-12">{swaggerDocumentation}</div>
      </footer>
    );
  }
}

Footer.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  { logout }
)(Footer);
