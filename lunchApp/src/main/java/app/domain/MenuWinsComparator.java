package app.domain;

import java.util.Comparator;

public class MenuWinsComparator implements Comparator<Menu> {
    @Override
    public int compare(Menu menu1, Menu menu2) {
        return menu1.getWins() - menu2.getWins();
    }
}
