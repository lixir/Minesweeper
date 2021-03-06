
/**
 * Содержит все возможные состояния клетки
 *
 * @author DoKel
 * @version 1.0
 */
public enum CellState {
    EMPTY, /* В клетке нет мины */
    MINE, /* В клетке есть мина, но она не взорвана */
    EXPLOSED; /* В клетке есть мина и она взорвана*/
}
