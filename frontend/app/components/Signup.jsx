import { useNavigate } from "react-router";
import { useContext, useState } from "react";
import { API_URL, UserContext } from "../components";
import { ROUTE_PATH_SIGNED_UP } from "../routes";
import "../app.css";

async function signup({ formData, navigate, setUser, setCode }) {
  const name = formData.get("name");
  const username = formData.get("username");
  let res;

  try {
    const response = await fetch(`${API_URL}/users`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        name: name,
      }),
    });

    res = [response.ok, await response.json()];
  } catch (error) {
    console.error(error);

    return;
  }

  const [ok, data] = res;

  if (!ok) {
    setUser(null);
    setCode(data.code);

    return;
  }

  setUser(data.id);
  setCode(null);
  navigate(ROUTE_PATH_SIGNED_UP);
}

function SignupErrorUsernameExists({ code }) {
  if (code !== "USERNAME_EXISTS") {
    return null;
  }

  return (
    <div>
      Username is taken.
    </div>
  )
}

export function Signup() {
  const [_, setUser] = useContext(UserContext);
  const [code, setCode] = useState();
  const navigate = useNavigate();

  return (
    <form action={(formData) => signup({ formData, navigate, setUser, setCode })}>
      <div className="form-grid">
        <label htmlFor="name">Name:</label>
        <input className="form-input"
               type="text"
               name="name"
               required />

        <label htmlFor="username">Username:</label>
        <input className="form-input"
               type="text"
               name="username"
               required />
      </div>

      <button className="form-submit" type="submit">
        Submit
      </button>
      <SignupErrorUsernameExists code={code} />
    </form>
  )
}
