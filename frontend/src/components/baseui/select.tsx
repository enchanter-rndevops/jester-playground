import { Select as BaseSelect } from "@base-ui/react/select"
import type { ComponentProps } from "react"

export const SelectRoot = (props: ComponentProps<typeof BaseSelect.Root>) => (
  <BaseSelect.Root {...props} />
)

export const SelectTrigger = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Trigger>) => (
  <BaseSelect.Trigger
    {...props}
    className={className ? `Select Trigger ${className}` : "Select Trigger"}
  />
)

export const SelectValue = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Value>) => (
  <BaseSelect.Value
    {...props}
    className={className ? `Select Value ${className}` : "Select Value"}
  />
)

export const SelectIcon = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Icon>) => (
  <BaseSelect.Icon
    {...props}
    className={className ? `Select Icon ${className}` : "Select Icon"}
  />
)

export const SelectPortal = (
  props: ComponentProps<typeof BaseSelect.Portal>,
) => <BaseSelect.Portal {...props} />

export const SelectPositioner = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Positioner>) => (
  <BaseSelect.Positioner
    {...props}
    className={
      className ? `Select Positioner ${className}` : "Select Positioner"
    }
  />
)

export const SelectPopup = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Popup>) => (
  <BaseSelect.Popup
    {...props}
    className={className ? `Select Popup ${className}` : "Select Popup"}
  />
)

export const SelectArrow = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Arrow>) => (
  <BaseSelect.Arrow
    {...props}
    className={className ? `Select Arrow ${className}` : "Select Arrow"}
  />
)

export const SelectItem = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Item>) => (
  <BaseSelect.Item
    {...props}
    className={className ? `Select Item ${className}` : "Select Item"}
  />
)

export const SelectItemText = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.ItemText>) => (
  <BaseSelect.ItemText
    {...props}
    className={className ? `Select ItemText ${className}` : "Select ItemText"}
  />
)

export const SelectItemIndicator = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.ItemIndicator>) => (
  <BaseSelect.ItemIndicator
    {...props}
    className={
      className ? `Select ItemIndicator ${className}` : "Select ItemIndicator"
    }
  />
)

export const SelectGroup = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Group>) => (
  <BaseSelect.Group
    {...props}
    className={className ? `Select Group ${className}` : "Select Group"}
  />
)

export const SelectGroupLabel = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.GroupLabel>) => (
  <BaseSelect.GroupLabel
    {...props}
    className={
      className ? `Select GroupLabel ${className}` : "Select GroupLabel"
    }
  />
)

export const SelectSeparator = ({
  className,
  ...props
}: ComponentProps<typeof BaseSelect.Separator>) => (
  <BaseSelect.Separator
    {...props}
    className={className ? `Select Separator ${className}` : "Select Separator"}
  />
)

// まとめてドット記法でエクスポート
export const Select = {
  Root: SelectRoot,
  Trigger: SelectTrigger,
  Value: SelectValue,
  Icon: SelectIcon,
  Portal: SelectPortal,
  Positioner: SelectPositioner,
  Popup: SelectPopup,
  Arrow: SelectArrow,
  Item: SelectItem,
  ItemText: SelectItemText,
  ItemIndicator: SelectItemIndicator,
  Group: SelectGroup,
  GroupLabel: SelectGroupLabel,
  Separator: SelectSeparator,
}
