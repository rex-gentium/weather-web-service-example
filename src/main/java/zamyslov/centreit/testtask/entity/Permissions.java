package zamyslov.centreit.testtask.entity;

/***
 * Интерфейс, содержащий константы прав доступа пользователей внутри веб-сервиса
 */
public interface Permissions {
    int NONE =  0b00, // нет прав
        READ =  0b01, // только чтение
        WRITE = 0b10, // только запись
        ALL =   0b11; // чтение + запись

    /***
     * Проверяет наличие права в наборе
     * @param expected права, наличие которых проверяется
     * @param actual набор прав пользователя
     * @return true, если в actual содержится искомое право, иначе false
     */
    static boolean hasPermission(int expected, int actual) {
        return (expected & actual) == expected;
    }
}
