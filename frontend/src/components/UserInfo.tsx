import { useAuth } from "../hooks/useAuth"

export function UserInfo(props: any) {
  const { user } = useAuth()

  if (!user) {
    return <div className={`UserInfo ${props.className}`}>未ログイン</div>
  }

  return (
    <div className={`UserInfo ${props.className}`}>
      {user.name}（{user.email}）
    </div>
  )
}
