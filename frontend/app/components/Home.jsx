import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { UserName } from "./UserName";
import { API_URL, UserContext } from "../components";
import {
  titleFavoritedRoutePath,
  titleUnfavoritedRoutePath,
  ROUTE_PATH_LOGIN,
  ROUTE_PATH_LOGOUT,
  ROUTE_PATH_LOGOUT_DELETE,
  ROUTE_PATH_TITLE_CREATE
} from "../routes";

function HomeUserNotLoggedIn() {
  return (
    <div>
      You are not logged in. <a href={ROUTE_PATH_LOGIN}>Login...</a>
    </div>
  )
}

function HomeUserLoggedIn({ user }) {
  const [data, setData] = useState(null);
  useEffect(() => {
    (async () => {
      let res;

      try {
        const response = await fetch(`${API_URL}/users/${user}`);
        res = [response.ok, await response.json()];
      } catch (error) {
        console.error(error);
        setData(null);

        return;
      }

      const [ok, data] = res;

      if (!ok) {
        setData(null);

        return;
      }

      setData(data);
    })();
  }, [user]);

  if (data === null) {
    return null;
  }

  return (
    <div>
      Logged in as <UserName user={data.user} />. <a href={ROUTE_PATH_LOGOUT}>Logout...</a> <a href={ROUTE_PATH_LOGOUT_DELETE}>Delete...</a>
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

async function favoriteTitle({ event, title, user, favorites, navigate }) {
  event.preventDefault();

  const userTitle = favorites.get(title.id);
  let response;
  let to;

  try {
    if (userTitle === undefined) {
      response = await fetch(`${API_URL}/users/${user}/titles`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: title.id,
          isFavorite: true,
        }),
      });

      to = titleFavoritedRoutePath(title.id);
    } else {
      response = await fetch(`${API_URL}/users/titles/${userTitle}`, {
        method: "DELETE",
      });

      to = titleUnfavoritedRoutePath(title.id);
    }
  } catch (error) {
    console.error(error);

    return;
  }

  if (!response.ok) {
    return;
  }

  navigate(to);
}

function HomeContentFavorite({ title, favorites }) {
  const navigate = useNavigate();
  const [user, _] = useContext(UserContext);

  if (user === null) {
    return null;
  }

  return (
    <a href={favorites.has(title.id) ? titleUnfavoritedRoutePath(title.id) : titleFavoritedRoutePath(title.id)}
       onClick={(event) => favoriteTitle({ event, user, title, favorites, navigate })}>
      {favorites.has(title.id) ? "Unfavorite..." : "Favorite..."}
    </a>
  )
}

function HomeContentTitleFavorite({ isFavorite }) {
  if (!isFavorite) {
    return null;
  }

  return (
    <span>
      ❤️
    </span>
  )
}

function HomeContent() {
  const [user, _] = useContext(UserContext);
  const [data, setData] = useState(null);
  const [favorites, setFavorites] = useState(null);
  useEffect(() => {
    (async () => {
      let data;
      let favorites;

      try {
        // I know I can make this concurrent, so don't bug me about it.
        const dataResponse = await fetch(`${API_URL}/titles`)
        data = [dataResponse.ok, await dataResponse.json()];

        if (user !== null) {
          const favoritesResponse = await fetch(`${API_URL}/users/${user}`);
          favorites = [favoritesResponse.ok, await favoritesResponse.json()];
        }
      } catch (error) {
        console.error(error);
        setData(null);
        setFavorites(null);

        return;
      }

      const [dataOk, dataData] = data;

      if (!dataOk) {
        setData(null);
        setFavorites(null);

        return;
      }

      setData(dataData);

      let favs;

      if (favorites === undefined) {
        favs = new Map();
      } else {
        const [ok, data] = favorites;

        if (!ok) {
          setData(null);
          setFavorites(null);

          return;
        }

        favs = data.user.titles
          .filter((title) => title.isFavorite)
          .reduce((result, title) => result.set(title.title.id, title.id), new Map())
      }

      setFavorites(favs);
    })();
  }, [user]);

  if (data === null) {
    return null;
  }

  return (
    <div>
      <h1>Titles</h1>
      <ul className="titles">
        {data.titles.map((title) => (
          <li key={title.id}>
            <h2>
              {title.name} <HomeContentTitleFavorite isFavorite={favorites.has(title.id)} />
            </h2>
            <p className="title-description">{title.description}</p>
            <HomeContentFavorite title={title} favorites={favorites} />
          </li>
        ))}
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
