import { ROUTE_PATH_HOME } from "../routes";

export function LogoutDeleted() {
  return (
    <div>
      Your user has been deleted. <a href={ROUTE_PATH_HOME}>Home...</a>
    </div>
  );
}
