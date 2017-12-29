import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BotTest {
    GameField gameField = new GameField();
    Bot bot = new Bot();
    List list = new ArrayList(){
        {
            add(new Pair(
                    new HashSet<Cell>(){
                        {add(new Cell(false, 0, 0));}
                        {add(new Cell(true, 0, 1));}
            }, 1));

            add(new Pair(
                    new HashSet<Cell>(){
                        {add(new Cell(false, 0, 0));}
                        {add(new Cell(true, 0, 1));}
                    }, 1));
        }
    };

    @Test
    public void equalityComparisonTest(){
        List tempList = new ArrayList(){ {add(list.get(0));} };
        bot.setList(list);
        bot.equalityComparison();
        System.out.println(bot.getList());
        assertEquals(bot.getList(), tempList);
    }

    @Test
    public void comparizonTest(){
        List tempList = new ArrayList(){ {add(list.get(0));} };
        tempList.add(new Pair(
                new HashSet<Cell>(){
                    {add(new Cell(false, 0, 0));}
                }, 1));
        bot.setList(tempList);
        bot.comparizon();
        tempList.remove(0);
        tempList.add(new Pair(
                new HashSet<Cell>(){
                    {add(new Cell(false, 0, 1));}
                }, 0));
        assertEquals(bot.getList(), tempList);
    }

    @Test
    public void intersectionsTest(){
        List tempList = new ArrayList(){ {add(list.get(0));} };
        tempList.add(new Pair(
                new HashSet<Cell>(){
                    {add(new Cell(false, 0, 0));}
                    {add(new Cell(false, 1, 0));}
                }, 1));
        bot.setList(tempList);
        bot.intersections();
        List result = new ArrayList(){
            {
                add(new Pair(
                        new HashSet<Cell>(){
                            {add(new Cell(false, 0, 0));}
                        }, 1));
                add(new Pair(
                        new HashSet<Cell>(){
                            {add(new Cell(true, 0, 1));}
                        }, 0));
                add(new Pair(
                        new HashSet<Cell>(){
                            {add(new Cell(false, 1, 0));}
                        }, 0));

            }
        };
        assertEquals(bot.getList(), result);
    }
}