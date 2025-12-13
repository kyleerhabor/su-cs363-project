import { useContext, useEffect, useState } from "react"
import { useNavigate } from "react-router";
import { SimpleUserName } from "./UserName";
import { API_URL, UserContext } from "../components";
import { ROUTE_PATH_SIGNUP, ROUTE_PATH_LOGIN_SUCCESS } from "../routes";

function login({ formData, setUser, navigate }) {
  const user = formData.get("user");

  if (user === null) {
    return;
  }

  setUser(user);
  navigate(ROUTE_PATH_LOGIN_SUCCESS);
}

function LoginForm() {
  const [_, setUser] = useContext(UserContext);
  const navigate = useNavigate();
  const [data, setData] = useState(null);
  useEffect(() => {
    async function start() {
      let data;

      try {
        data = await fetch(`${API_URL}/users`).then((response) => {
          if (!response.ok) {
            return null;
          }

          return response.json();
        });
      } catch (error) {
        console.error(error);

        data = null;
      }

      setData(data);
    }

    start();
  }, []);

  if (data === null) {
    return null;
  }

  return (
    <div>
      <form className="form-grid" action={(formData) => login({ formData, setUser, navigate })}>
        <label htmlFor="login-user">Login as:</label>
        <select id="login-user" name="user">
          {data.users.map((user) => (
            <option key={user.id} value={user.id}>
              <SimpleUserName user={user} />
            </option>
          ))}
        </select>

        <button className="form-submit" type="submit">
          Submit
        </button>
      </form>
    </div>
  );
}

export function Login() {
  return (
    <div>
      <LoginForm />
      <div>
        ... or <a href={ROUTE_PATH_SIGNUP}>create a new user</a> first.
      </div>
    </div>
  )
}
