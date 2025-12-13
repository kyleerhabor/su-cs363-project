import { ROUTE_PATH_HOME } from "../routes";

export function TitleUnfavorited() {
  return (
    <div>
      The title has been unfavorited. <a href={ROUTE_PATH_HOME}>Home...</a>
    </div>
  );
}
