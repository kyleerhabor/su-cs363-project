import { ROUTE_PATH_HOME } from "../routes";

export function LogoutSuccess() {
  return (
    <div>
      You have been logged out. <a href={ROUTE_PATH_HOME}>Home...</a>
    </div>
  );
}
