import { ROUTE_PATH_HOME } from "../routes";

export function TitleCreated() {
  return (
    <div>
      The title has been created. <a href={ROUTE_PATH_HOME}>Home...</a>
    </div>
  );
}
