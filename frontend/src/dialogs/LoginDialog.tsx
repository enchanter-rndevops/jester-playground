import { Dialog } from "@base-ui/react";
import LoginForm from "./LoginForm";

export function LoginDialog(props: Dialog.Root.Props) {
  const open = props.open;
  const setDialogOpen = props.onOpenChange;

  return (
    <>
      <Dialog.Root open={open} onOpenChange={setDialogOpen}>
        <Dialog.Portal>
          <Dialog.Backdrop className="fixed inset-0 min-h-dvh bg-black opacity-20 transition-opacity duration-150 data-ending-style:opacity-0 data-starting-style:opacity-0 supports-[-webkit-touch-callout:none]:absolute dark:opacity-50" />
          <Dialog.Viewport>
            <Dialog.Popup className="fixed top-1/2 left-1/2 -mt-8 flex w-96 max-w-[calc(100vw-3rem)] -translate-x-1/2 -translate-y-1/2 flex-col gap-4 border border-neutral-950 bg-white p-4 text-neutral-950 shadow-[0.25rem_0.25rem_0] shadow-black/12 transition-[scale,opacity] duration-100 ease-out data-ending-style:scale-[0.98] data-ending-style:opacity-0 data-starting-style:scale-[0.98] data-starting-style:opacity-0 dark:border-white dark:bg-neutral-950 dark:text-white dark:shadow-none">
              <div>
                <Dialog.Title>Jester Playground Sign in.</Dialog.Title>
                <Dialog.Description>
                  <LoginForm />
                  <Dialog.Close>Close</Dialog.Close>
                </Dialog.Description>
              </div>
            </Dialog.Popup>
          </Dialog.Viewport>
        </Dialog.Portal>
      </Dialog.Root>
    </>
  );
}
