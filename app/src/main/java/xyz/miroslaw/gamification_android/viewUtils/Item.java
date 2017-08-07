package xyz.miroslaw.gamification_android.viewUtils;

import lombok.Data;


@Data
public class Item {

    private int number;
    private String name;

    public Item(String name, int number) {
        this.number = number;
        this.name = name;
    }
}
