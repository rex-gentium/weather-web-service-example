package zamyslov.centreit.testtask.exception;

/***
 * Исключение, возникающее при передаче null в качестве параметра между методами разных слоев
 * там, где это не предполагается
 */
public class InternalNullPointerException extends Exception {
    public InternalNullPointerException(String msg) {
        super(msg);
    }
}
