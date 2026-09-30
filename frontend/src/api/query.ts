import { useQuery, type UseQueryOptions } from "@tanstack/react-query";
import type { ApiMap } from "./ApiMap";

export function useApiQuery<K extends keyof ApiMap>(
  [key, input]: [K, ApiMap[K]["request"]],
  options?: Omit<UseQueryOptions<ApiMap[K]["response"]>, "queryKey">,
) {
  return useQuery<ApiMap[K]["response"]>({
    queryKey: [key, input],
    ...options,
  });
}
