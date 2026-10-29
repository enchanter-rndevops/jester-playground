package one.enchanter.rndevops.jester.playground.typegenerator;

/**
 * ApiResponseTsGenerator
 * 
 * ApiResponse, InvalidField の型定義。
 * 固定の文字列のを返しているだけなので、もとの型定義にあわせてここも修正すること。
 * あまりそうする必要性も感じませんが、そのうちこれも自動生成にするかもしれません。
 * 
 */
public class ApiResponseTsGenerator {

    public static String generate() {
        return """
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
                """;
    }
}
