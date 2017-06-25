package xyz.miroslaw.gamification_android.viewUtils;

import lombok.Data;

@Data
public class Deck {
    private int id;
    private String deckName;
    public Deck(int id, String deckName){
        this.id = id;
        this.deckName = deckName;
    }
}
