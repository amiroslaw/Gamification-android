package xyz.miroslaw.gamification_android.deckManager;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.Deck;
import xyz.miroslaw.gamification_android.viewUtils.Item;

public class DeckManagerPresenter implements DeckManagerContract.Presenter {
    private final String TAG = "myDebug " + getClass().getSimpleName();

    private DeckManagerContract.View view;
    private DeckDao deckDao;
    private CardDao cardDao;
    List<Deck> decksList = new ArrayList<>();

    public DeckManagerPresenter(DeckManagerContract.View view, Context context) {
        this.view = view;
        view.setPresenter(this);
        this.deckDao = new DeckDao(context);
        this.cardDao = new CardDao(context);
    }

    @Override
    public List<Item> getAdapterItems() {
        loadDecks();
        List<Item> items = new ArrayList<>();
        for (Deck deck : decksList) {
            items.add(new Item(deck.getId(), deck.getDeckName(), Integer.toString(deck.getCards().size())));
        }
        return items;
    }

    private void loadDecks() {
        decksList = deckDao.findAll();
    }


    @Override
    public boolean isAnyDeck() {
        return deckDao.countAll() > 0;
    }

    @Override
    public void deleteDeck(int position) {
        Deck deleted = decksList.get(position);
        cardDao.deleteAllCardsInDeck(deleted.getId());
        deckDao.deleteById(deleted.getId());
        decksList.remove(position);
        checkIfListIsEmpty();
    }

    private void checkIfListIsEmpty() {
        if(decksList.isEmpty()) view.showNoDecksView();
    }

    @Override
    public void duplicateDeck(int position, String name) {
        Deck original = deckDao.findById(decksList.get(position).getId());
        Deck newDeck = new Deck(original);
        newDeck.setDeckName(name);
        decksList.add(newDeck);

        ArrayList<Card> cards = copyCards(original, newDeck);
        saveToDB(newDeck, cards);
    }


    private ArrayList<Card> copyCards(Deck original, Deck newDeck) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (Card card : original.getCards()) {
            Card c = new Card(card);
            c.setDeck(newDeck);
            cards.add(c);
        }
        return cards;
    }

    private void saveToDB(Deck newDeck, ArrayList<Card> cards) {
        deckDao.create(newDeck);
        cardDao.saveAllCardsInDataBase(cards);
    }

}
