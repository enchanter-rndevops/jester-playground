import type { WhoamiResponseData } from "../types/ApiTypes"

export async function loadCurrentUser(): Promise<WhoamiResponseData | null> {
  const response = await fetch("/api/v1/whoami")

  if (!response.ok) {
    return null
  }

  const body = await response.json()

  return body.data
}
