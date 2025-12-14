import { ROUTE_PATH_HOME } from "../routes";

export function SignedUp() {
  return (
    <div>
      Your user has been signed up. <a href={ROUTE_PATH_HOME}>Home...</a>
    </div>
  );
}
