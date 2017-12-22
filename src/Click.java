/**
 * Содержит данные о клике -- координаты X и Y нажатия и кнопка, которой оно было сделано.
 */

public class Click {
    public int x, y, button;

    public Click(int x, int y, int button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }
}
