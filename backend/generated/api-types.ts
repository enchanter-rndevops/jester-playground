// API type generator for TypeScript.
// Generated at 2026-11-08T20:12:35.023064161

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
// GET:/api/v1/test
export interface TestRequest1 {
  name?: string;
  testRequest2?: TestRequest2;
}

export interface TestRequest2 {
  name?: string;
}

export interface TestResponseData1 {
  name?: string;
  map?: any;
  list?: string[];
  testResponseData2?: TestResponseData2;
}

export interface TestResponseData2 {
  name?: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.HealthController
// GET:/api/v1/health

// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/get
export interface ItemGetRequestBody {
  id?: string;
}

export interface ItemGetResponseData {
  id?: number;
  name?: string;
  description?: string;
  createdAt?: string;
  username?: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/list
export interface ItemListResponseData {
  items?: ItemGetResponseData[];
}

export interface ItemGetResponseData {
  id?: number;
  name?: string;
  description?: string;
  createdAt?: string;
  username?: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/register
export interface ItemRegisterRequestBody {
  name: string;
  description: string;
}

export interface ItemRegisterResponseData {
  id?: number;
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
  idToken?: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.WhoamiController
// POST:/api/v1/whoami
export interface WhoamiResponseData {
  uid?: string;
  email?: string;
  name?: string;
}

