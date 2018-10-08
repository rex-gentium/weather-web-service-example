package zamyslov.centreit.testtask.exception;

/***
 * Исключение, возникающее при несовпадении пароля пользователя
 */
public class PasswordMismatchException extends Exception {
    public PasswordMismatchException(String msg) {
        super(msg);
    }
}
