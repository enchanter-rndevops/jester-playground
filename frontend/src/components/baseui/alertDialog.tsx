import { AlertDialog as BaseAlertDialog } from "@base-ui/react/alert-dialog"

import type { ComponentProps } from "react"

/*
 * AlertDialog
 */
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

export const AlertDialogTitle = ({
  className,
  ...props
}: ComponentProps<typeof BaseAlertDialog.Title>) => (
  <BaseAlertDialog.Title
    {...props}
    className={`AlertDialog Title ${className}`}
  />
)

export const AlertDialogDescription = ({
  className,
  ...props
}: ComponentProps<typeof BaseAlertDialog.Description>) => (
  <BaseAlertDialog.Description
    {...props}
    className={`AlertDialog Description ${className}`}
  />
)

export const AlertDialogClose = ({
  className,
  ...props
}: ComponentProps<typeof BaseAlertDialog.Close>) => (
  <BaseAlertDialog.Close
    {...props}
    className={`AlertDialog Close ${className}`}
  />
)

export const AlertDialog = {
  Root: BaseAlertDialog.Root,
  Trigger: BaseAlertDialog.Trigger,
  Portal: BaseAlertDialog.Portal,
  Backdrop: AlertDialogBackdrop,
  Popup: AlertDialogPopup,
  Title: AlertDialogTitle,
  Description: AlertDialogDescription,
  Close: AlertDialogClose,
}
