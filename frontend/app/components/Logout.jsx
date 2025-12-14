import { useContext } from "react";
import { UserContext } from "../components";
import { useNavigate } from "react-router";
import { ROUTE_PATH_HOME, ROUTE_PATH_LOGGED_OUT } from "../routes";

function logout({ setUser, navigate }) {
  setUser(null);
  navigate(ROUTE_PATH_LOGGED_OUT);
}

export function Logout() {
  const [_, setUser] = useContext(UserContext);
  const navigate = useNavigate();

  return (
    <div>
      Are you sure? <a href={ROUTE_PATH_HOME}>Home...</a>
      <div>
        <button onClick={() => logout({ setUser, navigate })}>
          Confirm.
        </button>
      </div>
    </div>
  )
}
