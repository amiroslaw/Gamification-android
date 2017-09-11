package xyz.miroslaw.gamification_android;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.model.Deck;
import xyz.miroslaw.gamification_android.viewUtils.Item;

public class DataHelper {
    public static final int MAX_CARD_IN_DECK = 30;
    public static final Card CARD_SMALL = new Card(CardType.SMALL, "small", "description3", "path3");
    public static final Card CARD_MEDIUM = new Card(CardType.MEDIUM, "medium", "description2", "path2");
    public static final Card CARD_LARGE = new Card(CardType.LARGE, "large", "description1", "path1");
    public static final List<Card> CARDS = new ArrayList<>(Arrays.asList(CARD_LARGE, CARD_MEDIUM, CARD_SMALL));

    public static final List<Item> CARD_ITEMS = new ArrayList<>(Arrays.asList(
            new Item(0, CARD_LARGE.getTitle(), CARD_LARGE.getType().toString()),
            new Item(0, CARD_MEDIUM.getTitle(), CARD_MEDIUM.getType().toString()),
            new Item(0, CARD_SMALL.getTitle(), CARD_SMALL.getType().toString())

    ));


    public static final Deck DECK1 = new Deck("deck1", 3);
    public static final Deck DECK2 = new Deck("deck2", 1);
    public static final List<Deck> DECKS = new ArrayList<>(Arrays.asList(DECK1, DECK2));

    public static final List<Item> DECK_ITEMS = new ArrayList<>(Arrays.asList(
            new Item(1, DECK1.getDeckName(), "3"),
            new Item(2, DECK1.getDeckName(), "1")
    ));


}
