package one.enchanter.rndevops.jester.playground.backend.exception;

/**
 * 
 * NotFoundException
 * 
 * なんらかのリソースを取得するAPIで、目的のリソースが取得できなかった場合の例外。
 * userやitemを取得するAPIで取得できなかった場合にこれをthrowすると、共通のNOT_FOUNDレスポンスを返すようになっている。
 * ControllerからNOT_FOUNDを返さないようにすること。
 * 
 * 名前がわかりにくいので、変更する予定。
 * 
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
