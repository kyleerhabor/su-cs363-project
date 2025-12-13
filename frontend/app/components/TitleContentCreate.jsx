import { useState } from "react";
import { useNavigate, useParams } from "react-router";
import { API_URL } from "../components";
import { titleContentCreatedRoutePath } from "../routes";

async function createTitleContent({ formData, setCode, navigate, title }) {
  const name = formData.get("name");
  const synopsis = formData.get("synopsis");
  let res;

  try {
    const response = await fetch(`${API_URL}/titles/${title}/contents`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ name, synopsis }),
    })

    res = [response.ok, await response.json()]
  } catch (error) {
    console.error(error);

    return;
  }

  const [ok, data] = res;

  if (!ok) {
    setCode(data.code);

    return;
  }

  setCode(null);
  navigate(titleContentCreatedRoutePath(title));
}

function TitleContentCreateTitleNotFound({ code }) {
  if (code !== "TITLE_NOT_FOUND") {
    return;
  }

  return (
    <div>
      Title not found.
    </div>
  )
}

export function TitleContentCreate() {
  const { title } = useParams();
  const navigate = useNavigate();
  const [code, setCode] = useState(null);

  return (
    <form action={(formData) => createTitleContent({ formData, setCode, navigate, title })}>
      <div className="form-grid">
        <label htmlFor="create-title-content-name">Name:</label>
        <input id="create-title-content-name"
               className="form-input"
               type="text"
               name="name"
               required autoComplete="on" />

        <label htmlFor="create-title-content-synopsis">Synopsis:</label>
        <textarea id="create-title-content-synopsis"
                  className="form-input"
                  name="synopsis"
                  required />
      </div>

      <button className="form-submit" type="submit">
        Submit
      </button>
      <TitleContentCreateTitleNotFound code={code} />
    </form>
  )
}
