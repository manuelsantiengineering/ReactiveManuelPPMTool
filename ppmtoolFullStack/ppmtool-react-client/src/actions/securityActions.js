import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import jwt_decode from "jwt-decode";
import setJWTToken from "../securityUtils/setJWTToken";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const login = LoginRequest => async dispatch => {
  try {
    // Post Login request
    const res = await axios.post("/api/users/login", LoginRequest);
    // Extract token from res.data
    const { token } = res.data;
    // Store the token in localStorage
    localStorage.setItem("jwtToken", token);
    // Set the token in the headers
    setJWTToken(token);
    // Decode the token on React
    const decoded = jwt_decode(token);
    // Dispatch to our security reducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded
    });
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};
