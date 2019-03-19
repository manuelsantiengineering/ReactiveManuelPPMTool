import axios from "axios";
import {
  GET_ERRORS,
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK
} from "./types";

export const addProjectTask = (
  projectIdentifier,
  project_task,
  history
) => async dispatch => {
  try {
    await axios.post(`/api/backlog/${projectIdentifier}`, project_task);
    history.push(`/projectBoard/${projectIdentifier}`);
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

export const getBacklog = projectIdentifier => async dispatch => {
  try {
    const res = await axios.get(`/api/backlog/${projectIdentifier}`);
    dispatch({
      type: GET_BACKLOG,
      payload: res.data
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getProjectTask = (
  projectIdentifier,
  projectTaskSequence,
  history
) => async dispatch => {
  try {
    const res = await axios.get(
      `/api/backlog/${projectIdentifier}/${projectTaskSequence}`
    );
    dispatch({
      type: GET_PROJECT_TASK,
      payload: res.data
    });
  } catch (err) {
    history.push("/dashboard");
  }
};

export const updateProjectTask = (
  projectIdentifier,
  projectTaskSequence,
  updateProjectTask,
  history
) => async dispatch => {
  try {
    await axios.patch(
      `/api/backlog/${projectIdentifier}/${projectTaskSequence}`,
      updateProjectTask
    );
    history.push(`/projectBoard/${projectIdentifier}`);
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

export const deleteProjectTask = (
  projectIdentifier,
  projectTaskSequence
) => async dispatch => {
  try {
    if (
      window.confirm(
        `Are you sure?\nThis will delete the project task ${projectTaskSequence} and all the data related to it.\nThis action cannot be undone.`
      )
    ) {
      // const res = await axios.delete(`/api/project/${projectIdentifier}`);
      await axios.delete(
        `/api/backlog/${projectIdentifier}/${projectTaskSequence}`
      );
      dispatch({
        type: DELETE_PROJECT_TASK,
        payload: projectTaskSequence
      });
    }
  } catch (err) {
    // history.push("/dashboard");
    // Good place catch errors if there is no connection and display some error component
  }
};
