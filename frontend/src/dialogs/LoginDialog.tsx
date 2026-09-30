import { Button, Dialog, Field, Form } from "@base-ui/react";
import { ConfigContext } from "@config/config-context";
import { useContext } from "react";

export function LoginDialog(props: Dialog.Root.Props) {
  // const { data, isLoading, isError, refetch } = useApiQuery(
  //   ["v1/session", undefined],
  //   { enabled: false },
  // );

  const open = props.open;
  const setDialogOpen = props.onOpenChange;

  const config = useContext(ConfigContext);
  const cognitoUrl = config?.cognitoUrl;

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
                  <Form
                    onSubmit={async (event) => {
                      event.preventDefault();
                      const formData = new FormData(event.currentTarget);
                      const username = formData.get("username") as string;
                      const password = formData.get("password") as string;

                      console.log(formData.keys());
                      console.log(username);
                      console.log(password);
                      // 2. ローカル偽Cognitoへログイン
                      const res = await fetch(`${cognitoUrl}`, {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ username, password }),
                      });

                      const { IdToken } = await res.json();

                      console.log(IdToken);

                      const sessionRes = await fetch(`/api/v1/session`, {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        credentials: "include",
                        body: JSON.stringify({ idToken: IdToken }),
                      });

                      console.log(sessionRes.json());
                    }}
                  >
                    <Field.Root name="username">
                      <Field.Control
                        type="text"
                        required
                        defaultValue=""
                        placeholder="User Id"
                        className="h-8 w-full border border-neutral-950 bg-white px-2 text-sm font-normal text-neutral-950 placeholder:text-neutral-500 focus:outline-2 focus:-outline-offset-1 focus:outline-neutral-950 dark:border-white dark:bg-neutral-950 dark:text-white dark:placeholder:text-neutral-400 dark:focus:outline-white any-pointer-coarse:text-base"
                      />
                    </Field.Root>
                    <Field.Root name="password">
                      <Field.Control
                        type="password"
                        required
                        defaultValue=""
                        placeholder="password"
                        className="h-8 w-full border border-neutral-950 bg-white px-2 text-sm font-normal text-neutral-950 placeholder:text-neutral-500 focus:outline-2 focus:-outline-offset-1 focus:outline-neutral-950 dark:border-white dark:bg-neutral-950 dark:text-white dark:placeholder:text-neutral-400 dark:focus:outline-white any-pointer-coarse:text-base"
                      />
                    </Field.Root>
                    <div>
                      <Button type="submit">Sign in</Button>
                    </div>
                  </Form>
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
