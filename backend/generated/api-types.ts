// API type generator for TypeScript.
// Generated at 2026-10-29T19:37:01.637944211

export interface ApiResponse<T> {
  severity: Severity;
  message: string;
  data: T | null;
  invalidFields: InvalidField[];
}

export interface InvalidField {
  field: string;
  message: string;
  rejectedValue: any;
}

export type Severity = "SUCCESS" | "ERROR" | "WARN";

// --------
// one.enchanter.rndevops.jester.playground.backend.controller.HealthController
// GET:/api/v1/health

// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/get
export interface ItemGetRequestBody {
  id: string;
}

export interface ItemGetResponseData {
  id: number;
  name: string;
  description: string;
  createdAt: string;
  username: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/list
export interface ItemListResponseData {
  items: any[];
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/register
export interface ItemRegisterRequestBody {
  name: string;
  description: string;
}

export interface ItemRegisterResponseData {
  id: number;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/delete
export interface ItemDeleteRequestBody {
  id: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.UnauthSessionController
// POST:/api/v1/session/login
export interface SessionRequestBody {
  idToken: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.WhoamiController
// POST:/api/v1/whoami
export interface WhoamiResponseData {
  sub: string;
  email: string;
  name: string;
}

