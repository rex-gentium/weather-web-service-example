package zamyslov.centreit.testtask.exception;

/***
 * Исключение, возникающее при указании значения погодного указателя,
 * выходящего за допустимые пределы (отрицательная влажность)
 */
public class InvalidIndicatorValueException extends Exception {
    public InvalidIndicatorValueException(String msg) {
        super(msg);
    }
}
