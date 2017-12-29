import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bot {

    List<Pair<Set<Cell>, Integer>> list = new ArrayList<>();

    public void start(GameField gameField) {
        list = new ArrayList<>();
        search(gameField);
        equalityComparison();
        comparizon();
//        intersections();
    }

    public List<Pair<Set<Cell>, Integer>> getList(){return list;}

    public void setList(List list){
        this.list.addAll(list);
    }

    public void search(GameField gameField) {
        Set<Cell> set = new HashSet<>();
        Cell temp;
        for (int x = 0; x < Constants.COUNT_CELLS_X; x++)
            for (int y = 0; y < Constants.COUNT_CELLS_Y; y++) {
                if (gameField.getCell(x, y).isHiden()) continue;
                    //слева-сверху
                if (x != 0 && y != 0 && gameField.getCell(x - 1, y - 1).isHiden())
                    set.add(gameField.getCell(x - 1, y - 1));
                    //сверху
                if (y != 0 && gameField.getCell(x, y - 1).isHiden())
                    set.add(gameField.getCell(x, y - 1));
                    //справа-сверху
                if (x != Constants.COUNT_CELLS_X - 1 && y != 0 && gameField.getCell(x + 1, y - 1).isHiden())
                    set.add(gameField.getCell(x + 1, y - 1));
                    //справа
                if (x != Constants.COUNT_CELLS_X - 1 && gameField.getCell(x + 1, y).isHiden())
                    set.add(gameField.getCell(x + 1, y));
                    //справа-снизу
                if (x != Constants.COUNT_CELLS_X - 1 && y != Constants.COUNT_CELLS_Y - 1
                            && gameField.getCell(x + 1, y + 1).isHiden())
                    set.add(gameField.getCell(x + 1, y + 1));
                    //снизу
                if (y != Constants.COUNT_CELLS_Y - 1 && gameField.getCell(x, y + 1).isHiden())
                    set.add(gameField.getCell(x, y + 1));
                    //слева-снизу
                if (x != 0 && y != Constants.COUNT_CELLS_Y - 1 && gameField.getCell(x - 1, y + 1).isHiden())
                    set.add(gameField.getCell(x - 1, y + 1));
                    //слева
                if (x != 0 && gameField.getCell(x - 1, y).isHiden())
                    set.add(gameField.getCell(x - 1, y));
                list.add(new Pair<>(set, gameField.getMinesNear(x, y)));
                set = new HashSet<>();
            }


    }

    public void equalityComparison(){
        for (int i = 0; i < list.size(); i++)
            for (int j = i + 1; j < list.size(); j++){
                if (list.get(i).getKey().equals(list.get(j).getKey())){
                    list.remove(j);
                }
        }
    }

    public void comparizon(){
        int temp;
        for (int i = 0; i < list.size(); i++)
            for (int j = 0; j < list.size(); j++){
                if (i == j) continue;
                if (list.get(i).getKey().containsAll(list.get(j).getKey())){
                    for (Cell cell: list.get(j).getKey()){
                        list.get(i).getKey().remove(cell);
                    }
                    temp = list.get(i).getValue() - list.get(j).getValue();
                    list.add(new Pair<>(list.get(i).getKey(), temp));
                    list.remove(i);
                }
            }
    }

    public void intersections(){
        int temp = 0;
        Set<Cell> tempCell = new HashSet<>();
        int size = list.size();

        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++){
                    for (Cell cell: list.get(i).getKey()){
                        if (!cell.getClass().equals(Cell.class)) continue;
                        if ((list.get(j).getKey()).contains(cell)){
                            temp++;
                            tempCell.add(cell);
                        }
                    }


                list.get(i).getKey().removeAll(tempCell);
                list.get(j).getKey().removeAll(tempCell);
                if (list.get(i).getValue() - list.get(j).getValue() >= 0){
                    list.add(new Pair(tempCell, list.get(i).getValue() - list.get(j).getKey().size() + temp));
                    if (list.get(i).getValue() - list.get(j).getKey().size() + temp != list.get(j).getValue()) continue;
                }

                if (list.get(j).getValue() - list.get(i).getValue() > 0){
                    list.add(new Pair(tempCell, list.get(j).getValue() - list.get(i).getKey().size() + temp));
                    if (list.get(j).getValue() - list.get(i).getKey().size() + temp != list.get(i).getValue()) continue;
                }
                list.add(new Pair(list.get(i).getKey(), list.get(i).getValue() - temp));

                list.add(new Pair(list.get(j).getKey(), list.get(j).getValue() - temp));

                list.remove(j);
                list.remove(i);

                temp = 0;
                tempCell = new HashSet<>();

            }
    }

}
