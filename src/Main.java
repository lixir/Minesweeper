
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * Главный управляющий класс.
 * Считывает действия пользователя, передаёт данные в graphicsModule, затем обрабатывает результат.
 */

public class Main {

    private static boolean endOfGame; //Флаг для завершения основного цикла программы
    private static LinkedList<Click> clicksStack;
    private static GameField gameField;
    private static Bot bot;

    /**
     * Точка входа. Содержит все необходимые действия для одного игрового цикла.
     */

    public static void main(String[] args) {
        initFields();
        boolean isBot = false;
        if (args.length == 1 && args[0].equals("--bot")){
            bot = new Bot();
            isBot = true;
        }


        while(!endOfGame){

            if (!isBot)input();
            if (isBot) {
                inputBot();
                bot.start(gameField);
            }
            logic();

        }
        System.out.println(":(");
    }

    /**
     * Задаёт значения полей для начала игры
     */
    private static void initFields() {
        endOfGame = false;
        gameField = new GameField();
        clicksStack = new LinkedList<>();
    }

    private static void write(){
        Cell temp;
        System.out.println("  0 1 2 3 4 5 6 7 8 9 ");
        for (int i = 0; i < Constants.COUNT_CELLS_Y; i++){
            System.out.print(i + " ");
            for (int j = 0; j < Constants.COUNT_CELLS_X; j++){
                temp = gameField.getCell(j, i);
                if (temp.isMarked()) {
                    System.out.print("F ");
                } else if (temp.isHiden()) System.out.print("* ");
                if (!temp.isHiden()) {
                    if (temp.getState().equals(CellState.EMPTY))
                        System.out.print(gameField.getMinesNear(j, i) + " ");
                    if (temp.getState().equals(CellState.MINE) || temp.getState().equals(CellState.EXPLOSED))
                        System.out.print("B ");
                }
                if (j == 9) System.out.println("");
            }
            if (i == 9) System.out.println("");
        }
    }


    /**
     * Считывает пользовательский ввод.
     */
    private static void input(){
        write();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int touchX = -1;
        int touchY = -1;
        int touchButton = -1;
        try {
            touchX = Integer.parseInt(reader.readLine());
            touchY = Integer.parseInt(reader.readLine());
            touchButton = Integer.parseInt(reader.readLine());
        } catch (Exception e){
            System.exit(1);
        }
        clicksStack = new LinkedList<Click>(){};
        clicksStack.add(new Click(touchX, touchY, touchButton));
        endOfGame = endOfGame || gameField.isEndGame();
    }

    private static void inputBot(){
        write();
        clicksStack = new LinkedList<Click>(){};
        List<Pair<Set<Cell>, Integer>> list = bot.getList();
        for (Pair<Set<Cell>, Integer> pair: list){
            if (pair.getValue() == 0){
                for (Cell cell: pair.getKey()){
                    clicksStack.add(new Click(cell.getX(), cell.getY(),0));
                }
            }
            if (pair.getValue() == pair.getKey().size()){
                for (Cell cell: pair.getKey()){
                    if (!cell.isMarked()) clicksStack.add(new Click(cell.getX(), cell.getY(),1));
                }
            }

        }
        if (clicksStack.size() == 0){
            double min = 0.0;
            Cell temp = new Cell(false,-1,0);
            for (Pair<Set<Cell>, Integer> pair: list){
                if (pair.getValue() != 0 && pair.getKey().size() / pair.getValue() > min){
                    min = pair.getKey().size();
                    for (Cell cell: pair.getKey()){
                        temp = cell;
                        break;
                    }
                }
            }
            if ((gameField.getMinesQuantity() - gameField.getMarkedQuantity()) != 0 &&
                    gameField.getBlackCellsQuantity()/(gameField.getMinesQuantity() - gameField.getMarkedQuantity()) > min){
                temp = gameField.getRandomBlackCell();
            }
            if (temp.getX() != -1) {
                clicksStack.add(new Click(temp.getX(), temp.getY(), 0));
            }
            if (temp.getX() == -1) clicksStack.add(new Click((int) (Math.random()*Constants.COUNT_CELLS_X),
                    (int) (Math.random()*Constants.COUNT_CELLS_Y), 0));
        }
    }

    /**
     * Основная логика игры.
     *
     */
    private static void logic() {
        for(Click click : clicksStack) {
            System.out.println(gameField.getMinesQuantity());
            write();
            gameField.recieveClick(click);
        }
    }

}
