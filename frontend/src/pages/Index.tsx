import { Button } from "@base-ui/react/button"
import { Toast } from "@base-ui/react/toast"
import { ConfigContext } from "@config/config-context"
import { useContext } from "react"
import { Link } from "react-router-dom"

/**
 * ページのファイル名は、PascalCase.tsx とすること。
 * router.tscで kebab-case に返還されます。
 * 関数の名前はなんでもいいですが、Pageとしておきましょう。
 */
export default function Page() {
  const config = useContext(ConfigContext)
  const toastManager = Toast.useToastManager()

  function createToast() {
    toastManager.add({
      title: `Toast created. `,
      description: "This is a toast notification.",
      timeout: 0,
    })
  }

  return (
    <>
      <h1>index</h1>
      <div>This is SANDBOX application.</div>
      <div>{config?.apiBaseUrl}</div>
      <div>
        <Button onClick={createToast}>Toast</Button>
      </div>
      <Link to="/about">about</Link>
    </>
  )
}
