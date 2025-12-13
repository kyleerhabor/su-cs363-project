import { useParams } from "react-router";
import { titleContentCreatedRoutePath } from "../routes";

export function TitleContentCreated() {
  const { title, content } = useParams();

  return (
    <div>
      The title content has been created. <a href={titleContentCreatedRoutePath(title, content)}>Go...</a>
    </div>
  );
}
