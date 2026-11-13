import { Toast } from "@base-ui/react/toast"
import type { ApiResponse, Severity } from "../types/ApiTypes"

const toastManager = Toast.useToastManager()

/**
 * ApiResponseからToastを生成。
 * @param response
 */
export function createApiResponseToast(response: ApiResponse<any>) {
  toastManager.add({
    title: "", // response.severityから自動的に設定するように修正。
    description: response.message,
    timeout: 0, // response.severityから自動的に設定するように修正。WARNING以上なら0.
    // type, dataのどちらかを使用する。
    type: response.severity,
    data: {
      severity: response.severity,
    },
  })
}

/**
 * Toastを生成する。
 * @param title
 * @param description
 * @param severity
 */
export function createToast(
  title: string,
  description: string,
  severity?: Severity,
) {
  toastManager.add({
    title: title,
    description: description,
    timeout: 0, // response.severityから自動的に設定するように修正。WARNING以上なら0.
    // type, dataのどちらかを使用する。
    type: severity,
    data: {
      severity: severity,
    },
  })
}
