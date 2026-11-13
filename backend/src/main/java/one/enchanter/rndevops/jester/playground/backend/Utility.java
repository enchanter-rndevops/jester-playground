package one.enchanter.rndevops.jester.playground.backend;

public final class Utility {

    private Utility() {
    }

    public static String getClassName(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.getClass().getSimpleName();
    }

    /**
     * ログに出力するエラーIDを生成する。
     * 
     * @param prefix "E", "W"など、重要度によって簡単なPrefixを付ける。
     * @return 生成したエラーID
     */
    public static String generateErrorId(String prefix) {
        return (prefix + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }
}
