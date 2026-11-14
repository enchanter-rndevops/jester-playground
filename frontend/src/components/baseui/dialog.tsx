import { Dialog as BaseDialog } from "@base-ui/react/dialog"

import type { ComponentProps } from "react"

/*
 * Dialog
 */
export const DialogRoot = (props: ComponentProps<typeof BaseDialog.Root>) => (
  <BaseDialog.Root {...props} />
)

export const DialogTrigger = ({
  className,
  ...props
}: ComponentProps<typeof BaseDialog.Trigger>) => (
  <BaseDialog.Trigger {...props} className={`Dialog Trigger ${className}`} />
)

export const DialogPortal = (
  props: ComponentProps<typeof BaseDialog.Portal>,
) => <BaseDialog.Portal {...props} />

export const DialogBackdrop = ({
  className,
  ...props
}: ComponentProps<typeof BaseDialog.Backdrop>) => (
  <BaseDialog.Backdrop {...props} className={`Dialog Backdrop ${className}`} />
)

export const DialogPopup = ({
  className,
  ...props
}: ComponentProps<typeof BaseDialog.Popup>) => (
  <BaseDialog.Popup {...props} className={`Dialog Popup ${className}`} />
)

export const DialogTitle = ({
  className,
  ...props
}: ComponentProps<typeof BaseDialog.Title>) => (
  <BaseDialog.Title {...props} className={`Dialog Title ${className}`} />
)

export const DialogDescription = ({
  className,
  ...props
}: ComponentProps<typeof BaseDialog.Description>) => (
  <BaseDialog.Description
    {...props}
    className={`Dialog Description ${className}`}
  />
)

export const DialogClose = ({
  className,
  ...props
}: ComponentProps<typeof BaseDialog.Close>) => (
  <BaseDialog.Close {...props} className={`Dialog Close ${className}`} />
)

// まとめてドット記法でエクスポート
export const Dialog = {
  Root: DialogRoot,
  Trigger: DialogTrigger,
  Portal: DialogPortal,
  Backdrop: DialogBackdrop,
  Popup: DialogPopup,
  Title: DialogTitle,
  Description: DialogDescription,
  Close: DialogClose,
}
