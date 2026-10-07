import { fetchAuthSession, signIn } from "aws-amplify/auth";
import { useState } from "react";

/*
 * LoginFormコンポーネントは、ユーザーがメールアドレスとパスワードを入力してログインするためのフォームを提供します。
 * AIが生成したコード。要修正。
 */
export default function LoginForm() {
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e: { preventDefault: () => void }) => {
    e.preventDefault();
    try {
      // 1. Cognitoへログイン要求
      const { isSignedIn } = await signIn({
        username: userId,
        password: password,
      });

      if (isSignedIn) {
        console.log("Cognitoログイン成功！");

        // 2. 現在のセッションからIDトークンを取得
        const session = await fetchAuthSession();
        const idToken = session.tokens?.idToken?.toString();

        if (!idToken) {
          throw new Error("IDトークンの取得に失敗しました");
        }

        // 3. 自前のバックエンド /api/session へIDトークンを送信
        const response = await fetch("/api/session", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ idToken: idToken }),
        });

        if (response.ok) {
          console.log("/api/session でのセッション確立に成功しました！");

          // whoami APIを呼び出してユーザー情報を取得
          const whoamiResponse = await fetch("/api/whoami", {
            method: "GET",
            headers: {
              Authorization: `Bearer ${idToken}`,
            },
          });

          if (whoamiResponse.ok) {
            const userData = await whoamiResponse.json();
            console.log("ユーザー情報:", userData);
            // ここでユーザー情報を状態管理やコンテキストに保存するなどの処理を行うことができます
          } else {
            console.error("whoami APIの呼び出しに失敗しました");
          }
        } else {
          console.error("/api/session での検証に失敗しました");
        }
      }
    } catch (error) {
      console.error("ログインエラー:", error);
    }
  };

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
  );
}
