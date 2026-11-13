import { useAuth } from "../hooks/useAuth"

export function UserInfo() {
  const { user } = useAuth()

  if (!user) {
    return <div>未ログイン</div>
  }

  return (
    <div>
      {user.name}（{user.email}）
    </div>
  )
}
