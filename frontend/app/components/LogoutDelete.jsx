import { useContext } from "react"
import { API_URL, UserContext } from "../components";
import { useNavigate } from "react-router";
import { ROUTE_PATH_HOME, ROUTE_PATH_LOGOUT_DELETED } from "../routes";

async function deleteUser({ user, setUser, navigate }) {
  let response;

  try {
    response = await fetch(`${API_URL}/users/${user.id}`, { method: "DELETE" });
  } catch (error) {
    console.error(error);

    return;
  }

  if (!response.ok) {
    return;
  }

  setUser(null);
  navigate(ROUTE_PATH_LOGOUT_DELETED);
}

export function LogoutDelete() {
  const [user, setUser] = useContext(UserContext);
  const navigate = useNavigate();

  return (
    <div>
      Are you sure? <a href={ROUTE_PATH_HOME}>Home...</a>
      <div>
        <button onClick={() => deleteUser({ user, setUser, navigate })}>
          Confirm.
        </button>
      </div>
    </div>
  )
}
