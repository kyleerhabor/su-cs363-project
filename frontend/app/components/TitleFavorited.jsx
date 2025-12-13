import { ROUTE_PATH_HOME } from "../routes";

export function TitleFavorited() {
  return (
    <div>
      The title has been favorited. <a href={ROUTE_PATH_HOME}>Home...</a>
    </div>
  );
}
