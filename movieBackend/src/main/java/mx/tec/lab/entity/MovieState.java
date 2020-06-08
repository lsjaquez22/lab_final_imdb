package mx.tec.lab.entity;

public enum MovieState {
    WATCHING,
    COMPLETED,
    PLAN_TO_WATCH,
    ON_HOLD;

    public static boolean isValid(String str) {
        try {
            MovieState.valueOf(str);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
