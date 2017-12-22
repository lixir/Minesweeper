
/**
 * Определяет все возможные результаты клика по клетке
 */

public enum ClickResult {
    REGULAR, /* Ничего не произшло */
    OPENED, /* Клетка была открыта, но в ней не оказалось мины */
    EXPLOSED; /* Клетка была открыта и в ней оказалась мина */
}
