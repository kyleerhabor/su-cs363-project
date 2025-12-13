import { ROUTE_PATH_HOME } from "../routes";

export function LoginSuccess() {
  return (
    <div>
      You have been logged in. <a href={ROUTE_PATH_HOME}>Home...</a>
    </div>
  );
}
