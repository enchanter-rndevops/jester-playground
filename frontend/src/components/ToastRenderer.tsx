import { Toast } from "@base-ui/react/toast"

/**
 * Toastの表示
 *
 * @returns ToastJSX
 */
export function ToastRenderer(props: any) {
  const { toasts } = Toast.useToastManager()

  return (
    <div className="Toast ${props.className}">
      {toasts.map((toast) => (
        <Toast.Root
          key={toast.id}
          toast={toast}
          className={`Toast Root ${props.className}`}
        >
          <Toast.Content className={`Toast Content ${props.className}`}>
            <Toast.Title className={`Toast Title ${props.className}`} />
            <Toast.Description
              className={`Toast Description ${props.className}`}
            />
            <Toast.Close className={`Toast Close ${props.className}`}>
              ×
            </Toast.Close>
          </Toast.Content>
        </Toast.Root>
      ))}
    </div>
  )
}
