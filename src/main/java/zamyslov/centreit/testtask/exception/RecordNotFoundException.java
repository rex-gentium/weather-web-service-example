package zamyslov.centreit.testtask.exception;

/***
 * Исключение, возникающее при попытке взять из БД данные по несуществующим ключам
 */
public class RecordNotFoundException extends Exception {
    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
