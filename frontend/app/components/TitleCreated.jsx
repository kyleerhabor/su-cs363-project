import { useParams } from "react-router";
import { titleRoutePath } from "../routes";

export function TitleCreated() {
  const { title } = useParams();

  return (
    <div>
      The title has been created. <a href={titleRoutePath(title)}>Go...</a>
    </div>
  );
}
