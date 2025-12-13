import { LoginSuccess } from "../components/LoginSuccess";

export function meta() {
  return [
    { title: "Logged In | My Titles" },
  ];
}

export default function Component() {
  return <LoginSuccess />;
}
