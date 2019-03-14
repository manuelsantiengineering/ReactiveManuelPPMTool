import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

export const createProject = (project, history) => async dispatch => {
  try {
    const res = await axios.post("http://localhost:8080/api/project", project);
    history.push("/dashboard");
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

export const getProjects = () => async dispatch => {
  try {
    const res = await axios.get("http://localhost:8080/api/project/all");
    dispatch({
      type: GET_PROJECTS,
      payload: res.data
    });
  } catch (err) {}
};

export const getProject = (projectIdentifier, history) => async dispatch => {
  try {
    const res = await axios.get(
      `http://localhost:8080/api/project/${projectIdentifier}`
    );
    dispatch({
      type: GET_PROJECT,
      payload: res.data
    });
  } catch (err) {
    history.push("/dashboard");
    // Good place catch errors if there is no connection and display some error component
  }
};

export const deleteProject = projectIdentifier => async dispatch => {
  try {
    const res = await axios.delete(
      `http://localhost:8080/api/project/${projectIdentifier}`
    );
    dispatch({
      type: DELETE_PROJECT,
      payload: projectIdentifier
    });
  } catch (err) {
    // history.push("/dashboard");
    // Good place catch errors if there is no connection and display some error component
  }
};
