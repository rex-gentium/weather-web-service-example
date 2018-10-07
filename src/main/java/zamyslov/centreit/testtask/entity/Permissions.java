package zamyslov.centreit.testtask.entity;

public interface Permissions {
    int NONE =  0b00,
        READ =  0b01,
        WRITE = 0b10,
        ALL =   0b11;

    static boolean hasPermission(int expected, int actual) {
        return (expected & actual) == expected;
    }
}
