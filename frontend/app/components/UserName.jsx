export function SimpleUserName({ user }) {
  return (
    <>
      {user.name} ({user.username})
    </>
  )
}

export function UserName({ user }) {
  return (
    <span>
      <SimpleUserName user={user} />
    </span>
  )
}
