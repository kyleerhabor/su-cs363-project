import { useNavigate } from "react-router"
import { API_URL } from "../components";
import { titleCreatedRoutePath } from "../routes";

async function createTitle({ formData, navigate }) {
  const name = formData.get("name");
  const description = formData.get("description");
  let res;

  try {
    let response = await fetch(`${API_URL}/titles`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ name, description }),
    });

    res = [response.ok, await response.json()]
  } catch (error) {
    console.error(error);

    return;
  }

  const [ok, data] = res;

  if (!ok) {
    return;
  }

  navigate(titleCreatedRoutePath(data.id));
}

export function TitleCreate() {
  const navigate = useNavigate();

  return (
    <form action={(formData) => createTitle({ formData, navigate })}>
      <div className="form-grid">
        <label htmlFor="create-title-name">Name:</label>
        <input id="create-title-name"
               className="form-input"
               type="text"
               name="name"
               required autoComplete="on" />

        <label htmlFor="create-title-description">Description:</label>
        <textarea id="create-title-description"
                  className="form-input"
                  name="description"
                  required />
      </div>

      <button className="form-submit" type="submit">
        Submit
      </button>
    </form>
  )
}
