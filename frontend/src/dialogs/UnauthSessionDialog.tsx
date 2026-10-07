import { useState } from "react";

/*
 * ここで入力されたIDTokenは、検証をせずにセッションが作成されます。
 * そのため、セキュリティ上の理由から、本番環境ではこのダイアログを使用しないでください（ローカル環境のみ有効になるように、VITE_ENVIRONMENT 変数などで適切な制御を行うこと。
 * ここで使用されているAPIは、local環境でのみ有効です(springProfileActive=localのときのみ)。
 *
 * ローカルではCognitoを使わずに、任意のIDTokenを使ってセッションを作成することができます。
 * ここで必要になるのは、ユーザー名ではなく、IDTokenだけです。IDTopkenに含める情報は、subだけです。
 * ユーザー情報は必要なので、テーブル usesr.sub に登録しておくこと。
 *
 * 直接IDTokenを入力するのはめんどくさいので、subを入力すると、IDTokenを生成するようにしたほうがいいでしょう。
 *
 */
export function UnauthSessionDialog() {
  const [token, setToken] = useState("");

  const login = async () => {
    await fetch("/api/v1/test/session", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ idToken: token }),
      credentials: "include",
    });
    window.location.reload();
  };

  return (
    <div>
      <textarea
        value={token}
        onChange={(e) => setToken(e.target.value)}
        placeholder="Paste fake IDToken"
      />
      <button onClick={login}>Create Local Session</button>
    </div>
  );
}
