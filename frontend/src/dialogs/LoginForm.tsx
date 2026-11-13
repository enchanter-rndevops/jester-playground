import { fetchAuthSession, signIn } from "aws-amplify/auth"
import { useState } from "react"
import { useAuth } from "../hooks/useAuth"

/*
 * LoginFormコンポーネントは、ユーザーがメールアドレスとパスワードを入力してログインするためのフォームを提供します。
 * AIが生成したコード。要修正。
 */
export default function LoginForm() {
  const [userId, setUserId] = useState("")
  const [password, setPassword] = useState("")
  const auth = useAuth()

  const handleLogin = async (e: { preventDefault: () => void }) => {
    e.preventDefault()

    let idToken = null

    if (import.meta.env.DEV) {
      console.log("ローカルダミーIDToken生成")
      idToken = createDummyIdToken(
        userId, // cognito:username
        password, // sub
      )
    } else {
      try {
        // 1. Cognitoへログイン要求
        const { isSignedIn } = await signIn({
          username: userId,
          password: password,
        })

        if (isSignedIn) {
          console.log("Cognitoログイン成功")

          // 2. 現在のセッションからIDトークンを取得
          const session = await fetchAuthSession()
          idToken = session.tokens?.idToken?.toString()

          if (!idToken) {
            throw new Error("IDトークンの取得に失敗しました")
          }
        }
      } catch (error) {
        console.error("ログインエラー:", error)
      }
    }

    const response = await fetch("/api/v1/session/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ idToken: idToken }),
    })

    if (response.ok) {
      console.log("/api/session でのセッション確立に成功しました！")

      if (response.ok) {
        await auth.refreshUser()
      }
    } else {
      console.error("/api/session での検証に失敗しました")
    }
  }

  return (
    <form onSubmit={handleLogin}>
      <h2>ログイン</h2>
      <div>
        <label>ユーザーID:</label>
        <input
          type="text"
          value={userId}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setUserId(e.target.value)
          }
        />
      </div>
      <div>
        <label>パスワード:</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <button type="submit">ログイン</button>
    </form>
  )
}

function createDummyIdToken(cognitoUsername: string, sub: string): string {
  const header = {
    alg: "none",
    typ: "JWT",
  }

  const payload = {
    sub,
    "cognito:username": cognitoUsername,
  }

  const encode = (obj: unknown) =>
    btoa(JSON.stringify(obj))
      .replace(/\+/g, "-")
      .replace(/\//g, "_")
      .replace(/=/g, "")

  return `${encode(header)}.${encode(payload)}.`
}
