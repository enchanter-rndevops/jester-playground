import { Button as BaseButton } from "@base-ui/react/button"

/*
 * Button
 */
export function Button({ className = "", ...props }) {
  return <BaseButton {...props} className={`Button ${className}`} />
}
