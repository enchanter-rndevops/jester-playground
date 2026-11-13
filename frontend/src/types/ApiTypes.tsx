export interface ApiResponse<T> {
  severity: Severity
  message: string
  data: T | null
  invalidFields: InvalidField[]
}

export interface InvalidField {
  field: string
  message: string
  rejectedValue: any
}

export interface WhoamiResponseData {
  uid: string
  email: string
  name: string
}

export type Severity = "SUCCESS" | "ERROR" | "WARN" | "INFO"
