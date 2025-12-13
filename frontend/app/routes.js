import { index, route } from "@react-router/dev/routes";

export const ROUTE_PATH_HOME = "/";
export const ROUTE_PATH_SIGNUP = "/signup";
export const ROUTE_PATH_SIGNUP_SUCCESS = "/signup/success";
export const ROUTE_PATH_LOGIN = "/login";
export const ROUTE_PATH_LOGIN_SUCCESS = "/login/success";
export const ROUTE_PATH_LOGOUT = "/logout";
export const ROUTE_PATH_LOGOUT_SUCCESS = "/logout/success";
export const ROUTE_PATH_LOGOUT_DELETE = "/logout/delete";
export const ROUTE_PATH_LOGOUT_DELETED = "/logout/deleted";
export const ROUTE_PATH_TITLE_CREATE = "/titles/create";
const ROUTE_PATH_TITLE_CREATED = "/titles/:title/created";
const ROUTE_PATH_TITLE_FAVORITED = "/titles/:title/favorited";
const ROUTE_PATH_TITLE_UNFAVORITED = "/titles/:title/unfavorited";

export function titleCreatedRoutePath(title) {
  return `/titles/${title}/created`;
}

export function titleFavoritedRoutePath(title) {
  return `/titles/${title}/favorited`;
}

export function titleUnfavoritedRoutePath(title) {
  return `/titles/${title}/unfavorited`;
}

export default [
  index("routes/home.jsx"),
  route(ROUTE_PATH_LOGIN, "routes/login.jsx"),
  route(ROUTE_PATH_LOGIN_SUCCESS, "routes/loginSuccess.jsx"),
  route(ROUTE_PATH_LOGOUT, "routes/logout.jsx"),
  route(ROUTE_PATH_LOGOUT_SUCCESS, "routes/logoutSuccess.jsx"),
  route(ROUTE_PATH_LOGOUT_DELETE, "routes/logoutDelete.jsx"),
  route(ROUTE_PATH_LOGOUT_DELETED, "routes/logoutDeleted.jsx"),
  route(ROUTE_PATH_SIGNUP, "routes/signup.jsx"),
  route(ROUTE_PATH_SIGNUP_SUCCESS, "routes/signupSuccess.jsx"),
  route(ROUTE_PATH_TITLE_CREATE, "routes/titleCreate.jsx"),
  route(ROUTE_PATH_TITLE_CREATED, "routes/titleCreated.jsx"),
  route(ROUTE_PATH_TITLE_FAVORITED, "routes/titleFavorited.jsx"),
  route(ROUTE_PATH_TITLE_UNFAVORITED, "routes/titleUnfavorited.jsx"),
];
