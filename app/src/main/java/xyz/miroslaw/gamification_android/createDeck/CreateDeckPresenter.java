package xyz.miroslaw.gamification_android.createDeck;


import android.content.Context;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;

import xyz.miroslaw.gamification_android.database.DatabaseManager;
import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.model.Deck;

public class CreateDeckPresenter implements CreateDeckContract.Presenter {
    private final int maxCardInDeck = 3;
    //  TODO:  private  final int maxCardInDeck = 10;
    private final String DEBUGTAG = "myDebug " + getClass().getSimpleName();

    private CreateDeckContract.View view;
    private Deque<Card> cards = new ArrayDeque<>();
    private String imgPath = "";
    private int cardCounter = 1;
    private DeckDao deckDao;
    private CardDao cardDao;
    private Deck deck = new Deck("non", 30);

    public CreateDeckPresenter(CreateDeckContract.View view, Context context) {
        this.view = view;
        view.setPresenter(this);
        deckDao = new DeckDao(context);
        cardDao = new CardDao(context);
    }

    @Override
    public void onNextClick(String name, String description) {
        view.disableReturning(false);
        CardType type = computeType(cardCounter);
        cards.add(new Card(type, name, description, imgPath));
        cardCounter++;
        type = computeType(cardCounter);
        view.setTxtTypeValue(type.toString());
        view.clearTexts();
        if (cardCounter > maxCardInDeck) {
            view.showDialog();
        }
    }

    private CardType computeType(int cardCounter) {
        if (cardCounter == 1) return CardType.LARGE;
        if (cardCounter > 1 && cardCounter < 5) return CardType.MEDIUM;
        return CardType.SMALL;
    }

    @Override
    public void onPrevClick() {
        cardCounter --;
        Card card = cards.pollLast();
        view.setTxtTypeValue(card.getType().toString());
        view.setPrevCardValues(card.getTitle(), card.getDescription(), card.getImage());
        if(cardCounter <= 1){
            view.disableReturning(true);
        }
    }

    @Override
    public void onImgBtnClick(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public void setDeckName(String deckName) {
        deck.setDeckName(deckName);
        Log.d(DEBUGTAG, "setDeckName: deck " + deck.getDeckName());
        saveDeck();
    }

    private void saveDeck() {
        deckDao.createOrUpdate(deck);
        Card card;
        while (!cards.isEmpty()) {
            card = cards.poll();
            card.setDeck(deck);
            cardDao.createOrUpdate(card);
            Log.d(DEBUGTAG, "saveDeck:  " + card.getTitle());
        }
        DatabaseManager.releaseHelper();

//        Deck deck = new Deck("from presenter");
//        Deck deck = deckDao.findById(1);

//        Card card = new Card(CardType.SMALL, "type test");
//        List<Deck> decks = deckDao.findAll();
//        Deck fetchDeck = deckDao.findById(1);
//        Log.d(DEBUGTAG, "onCreate: how many card " + cardDao.findAll().size());
    }

}
