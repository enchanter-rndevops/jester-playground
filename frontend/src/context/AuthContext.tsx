import { createContext, useEffect, useState } from "react"
import type { WhoamiResponseData } from "../types/ApiTypes"

interface AuthContextValue {
  user: WhoamiResponseData | null
  refreshUser: () => Promise<void>
}

export const AuthContext = createContext<AuthContextValue | null>(null)

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<WhoamiResponseData | null>(null)

  const refreshUser = async () => {
    const response = await fetch("/api/v1/whoami", { method: "POST" })

    if (!response.ok) {
      setUser(null)
      return
    }

    const body = await response.json()

    setUser(body.data)
  }

  useEffect(() => {
    refreshUser()
  }, [])

  return (
    <AuthContext.Provider
      value={{
        user,
        refreshUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}
