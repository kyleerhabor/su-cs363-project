import { useContext } from "react";
import { UserName } from "./UserName";
import { UserContext } from "../components";
import { ROUTE_PATH_LOGIN, ROUTE_PATH_LOGOUT, ROUTE_PATH_LOGOUT_DELETE, ROUTE_PATH_TITLE_CREATE } from "../routes";

function HomeUserNotLoggedIn() {
  return (
    <div>
      You are not logged in. <a href={ROUTE_PATH_LOGIN}>Login...</a>
    </div>
  )
}

function HomeUserLoggedIn({ user }) {
  return (
    <div>
      Logged in as <UserName user={user} />. <a href={ROUTE_PATH_LOGOUT}>Logout...</a> <a href={ROUTE_PATH_LOGOUT_DELETE}>Delete...</a>
    </div>
  );
}

function HomeUser() {
  const [user, _] = useContext(UserContext);

  if (user === null) {
    return <HomeUserNotLoggedIn />;
  }

  return <HomeUserLoggedIn user={user} />;
}

function HomeContent() {
  const [user, _] = useContext(UserContext);

  if (user === null) {
    return null;
  }

  return (
    <div>
      <h1>Titles</h1>
      <ul>

      </ul>
      <a href={ROUTE_PATH_TITLE_CREATE}>Create title...</a>
    </div>
  );
}

export function Home() {
  return (
    <div>
      <header>
        <HomeUser />
      </header>
      <br />
      <main>
        <HomeContent />
      </main>
    </div>
  );
}
