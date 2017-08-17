package xyz.miroslaw.gamification_android.viewUtils;

import lombok.Data;


@Data
public class Item {

    private int id;
    private String number;
    private String name;

    public Item(int id, String name, String number) {
        this.number = number;
        this.name = name;
        this.id = id;
    }
}
