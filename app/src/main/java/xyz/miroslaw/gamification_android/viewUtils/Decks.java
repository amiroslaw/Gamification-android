package xyz.miroslaw.gamification_android.viewUtils;

import lombok.Data;

@Data
public class Decks {
    private int id;
    private String deckName;
    public Decks(int id, String deckName){
        this.id = id;
        this.deckName = deckName;
    }
}
