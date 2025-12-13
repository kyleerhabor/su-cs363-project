import { createContext, useContext, useEffect, useState } from "react";

export const API_URL = "http://localhost:8080";
export const UserContext = createContext(null);
const LOCAL_STORAGE_KEY_USER = "user";

export function UserProvider({ children }) {
  const [user, setUser] = useState(() => {
    if (typeof localStorage === "undefined") {
      return null;
    }

    return JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY_USER));
  });

  useEffect(() => {
    if (typeof localStorage === "undefined") {
      return;
    }

    localStorage.setItem(LOCAL_STORAGE_KEY_USER, JSON.stringify(user));
  }, [user]);

  return (
    <UserContext.Provider value={[user, setUser]}>
      {children}
    </UserContext.Provider>
  );
}
