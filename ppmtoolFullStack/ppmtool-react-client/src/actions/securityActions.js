import axios from "axios";
// import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import { GET_ERRORS } from "./types";

// export const createNewUser = (newUser, history) => async dispatch => {
//   try {
//     await axios.post("/api/users/register", newUser);
//     history.pushState("/login");
//     dispatch({
//       type: GET_ERRORS,
//       payload: {}
//     });
//   } catch (err) {
//     dispatch({
//       type: GET_ERRORS,
//       payload: err.response.data
//     });
//   }
// };
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
