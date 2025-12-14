import { LoggedOut } from "../components/LoggedOut";

export function meta({}) {
  return [
    { title: "Logged out | My Titles" },
  ];
}

export default function Component() {
  return <LoggedOut />;
}
