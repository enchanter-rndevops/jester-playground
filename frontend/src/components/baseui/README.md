# BaseUIラッパー

デフォルトで定義されているスタイルシートの定義を適用するため、BaseUIをラップしています。
ラップしたコンポーネントは、もとのBaseUIと同じ名前なので、importでこちらのコンポーネントを使用するようにしてください。

ラップしていないコンポーネントはここに追加すること。

自分で書くのはしんどいので、AIにこのように指示すると、いい感じのコードを生成してくれます。
Geminiの無料チャットやCopilotでもいい感じに生成してくれますが、いちいちコピペするのはめんどくさいので、プロジェクト全体を読み込ませて一回で生成するようにしたいものです。

以下のコードを見本にして、BaseUIの<ラップするコンポーネント名>をラップしたコンポーネントを作成してください。

cssのほうは、コンポーネントと同じ名前のスタイルを定義しています。

```
export const AlertDialogBackdrop = ({
  className,
  ...props
}: ComponentProps<typeof BaseAlertDialog.Backdrop>) => (
  <BaseAlertDialog.Backdrop
    {...props}
    className={`AlertDialog Backdrop ${className}`}
  />
)

export const AlertDialogPopup = ({
  className,
  ...props
}: ComponentProps<typeof BaseAlertDialog.Popup>) => (
  <BaseAlertDialog.Popup
    {...props}
    className={`AlertDialog Popup ${className}`}
  />
)


export const AlertDialog = {
  Root: BaseAlertDialog.Root,
  Trigger: BaseAlertDialog.Trigger,
  Portal: BaseAlertDialog.Portal,
  Backdrop: AlertDialogBackdrop,
  Popup: AlertDialogPopup,
}

```
