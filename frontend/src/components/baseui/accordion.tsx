import { Accordion as BaseAccordion } from "@base-ui/react/accordion"

import type { ComponentProps } from "react"

/*
 * Accordion
 */
export const AccordionRoot = ({
  className,
  ...props
}: ComponentProps<typeof BaseAccordion.Root>) => (
  <BaseAccordion.Root {...props} className={`Accordion Root ${className}`} />
)

export const AccordionItem = ({
  className,
  ...props
}: ComponentProps<typeof BaseAccordion.Item>) => (
  <BaseAccordion.Item {...props} className={`Accordion Item ${className}`} />
)

export const AccordionHeader = ({
  className,
  ...props
}: ComponentProps<typeof BaseAccordion.Header>) => (
  <BaseAccordion.Header
    {...props}
    className={`Accordion Header ${className}`}
  />
)

export const AccordionPanel = ({
  className,
  ...props
}: ComponentProps<typeof BaseAccordion.Panel>) => (
  <BaseAccordion.Panel {...props} className={`Accordion Panel ${className}`} />
)

export const Accordion = {
  Root: AccordionRoot,
  Item: AccordionItem,
  Header: AccordionHeader,
  Panel: AccordionPanel,
}
