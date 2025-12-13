import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { API_URL } from "../components";
import { titleContentCreateRoutePath } from "../routes";

function TitleContent({ title, data }) {
  if (data === null) {
    return null;
  }

  return (
    <div>
      <h1>{data.title.name}</h1>
      <p className="title-description">{data.title.description}</p>

      <h2>Contents</h2>
      <ul className="title-contents">
        {data.title.contents.map((content) => (
          <li key={content.id}>
            <h3>{content.name}</h3>
            <p>{content.synopsis}</p>
          </li>
        ))}
      </ul>

      <a href={titleContentCreateRoutePath(title)}>Add content...</a>
    </div>
  )
}

function TitleNotFound({ code }) {
  if (code !== "TITLE_NOT_FOUND") {
    return null;
  }

  return (
    <div>
      Title not found.
    </div>
  )
}

export function Title() {
  const { title } = useParams();
  const [data, setData] = useState(null);
  const [code, setCode] = useState(null);
  useEffect(() => {
    (async () => {
      let res;

      try {
        const response = await fetch(`${API_URL}/titles/${title}`);
        res = [response.ok, await response.json()];
      } catch (error) {
        console.error(error);

        return;
      }

      const [ok, data] = res;
      let d;
      let code;

      if (!ok) {
        d = null;
        code = data.code;
      } else {
        d = data;
        code = null;
      }

      setData(d);
      setCode(code);
    })()
  }, [title]);

  return (
    <div>
      <TitleContent data={data} />
      <TitleNotFound code={code} />
    </div>
  );
}
