import { index, route } from "@react-router/dev/routes";

export const ROUTE_PATH_SIGNUP = "/signup";
export const ROUTE_PATH_SIGNUP_SUCCESS = "/signup/success";
export const ROUTE_PATH_LOGIN = "/login";
export const ROUTE_PATH_LOGIN_SUCCESS = "/login/success";
export const ROUTE_PATH_LOGOUT = "/logout";
export const ROUTE_PATH_LOGOUT_SUCCESS = "/logout/success";
export const ROUTE_PATH_LOGOUT_DELETE = "/logout/delete";
export const ROUTE_PATH_LOGOUT_DELETE_SUCCESS = "/logout/delete/success";
const ROUTE_PATH_TITLE = "/titles/:title";
const ROUTE_PATH_TITLE_CONTENT_CREATE = "/titles/:title/contents/create";
const ROUTE_PATH_TITLE_CONTENT_CREATED = "/titles/:title/contents/:content/created";
export const ROUTE_PATH_TITLE_CREATE = "/titles/create";
const ROUTE_PATH_TITLE_CREATED = "/titles/:title/created";

export function titleRoutePath(title) {
  return `/titles/${title}`;
}

export function titleCreatedRoutePath(title) {
  return `/titles/${title}/created`;
}

export function titleContentCreateRoutePath(title) {
  return `/titles/${title}/contents/create`;
}

export function titleContentCreatedRoutePath(title, content) {
  return `/titles/${title}/contents/${content}/created`;
}

export default [
  index("routes/home.jsx"),
  route(ROUTE_PATH_LOGIN, "routes/login.jsx"),
  route(ROUTE_PATH_LOGIN_SUCCESS, "routes/loginSuccess.jsx"),
  route(ROUTE_PATH_LOGOUT, "routes/logout.jsx"),
  route(ROUTE_PATH_LOGOUT_SUCCESS, "routes/logoutSuccess.jsx"),
  route(ROUTE_PATH_LOGOUT_DELETE, "routes/logoutDelete.jsx"),
  route(ROUTE_PATH_LOGOUT_DELETE_SUCCESS, "routes/logoutDeleteSuccess.jsx"),
  route(ROUTE_PATH_SIGNUP, "routes/signup.jsx"),
  route(ROUTE_PATH_SIGNUP_SUCCESS, "routes/signupSuccess.jsx"),
  route(ROUTE_PATH_TITLE, "routes/title.jsx"),
  route(ROUTE_PATH_TITLE_CREATE, "routes/titleCreate.jsx"),
  route(ROUTE_PATH_TITLE_CREATED, "routes/titleCreated.jsx"),
  route(ROUTE_PATH_TITLE_CONTENT_CREATE, "routes/titleContentCreate.jsx"),
  route(ROUTE_PATH_TITLE_CONTENT_CREATED, "routes/titleContentCreated.jsx"),
];
