package zamyslov.centreit.testtask.exception;

/***
 * Исключение, возникающее при отсутствия прав пользователя на выполнение запроса
 */
public class AccessRightsException extends Exception {
    public AccessRightsException(String msg) {
        super(msg);
    }
}
