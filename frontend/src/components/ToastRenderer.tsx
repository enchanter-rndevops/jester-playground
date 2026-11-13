import { Toast } from "@base-ui/react/toast"

/**
 * Toastの表示
 *
 * @returns ToastJSX
 */
export function ToastRenderer() {
  const { toasts } = Toast.useToastManager()

  /* 
  テーマが反映されるようにスタイルを設定すること。 
  ここでclassName設定せずに、styles.cssのほうで設定したほうがいい。
  */

  return (
    <>
      {toasts.map((toast) => (
        <Toast.Root
          key={toast.id}
          toast={toast}
          className="mb-2 rounded-lg border border-slate-200 bg-white shadow-lg"
        >
          {/* {toast.type}の値により、スタイルを変更する。スタイルはthemeで定義されているものを使用すること。 */}

          <Toast.Content className="flex items-start gap-3 p-4">
            <div className="flex-1">
              <Toast.Title className="font-semibold text-slate-900" />
              <Toast.Description className="mt-1 text-sm text-slate-600" />
            </div>

            <Toast.Close className="rounded px-2 py-1 text-sm text-slate-500 hover:bg-slate-100">
              ×
            </Toast.Close>
          </Toast.Content>
        </Toast.Root>
      ))}
    </>
  )
}
