package xyz.miroslaw.gamification_android.deckManager;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.model.Deck;
import xyz.miroslaw.gamification_android.viewUtils.Item;

public class DeckManagerPresenter implements DeckManagerContract.Presenter {
    private final String TAG = "myDebug " + getClass().getSimpleName();

    private DeckManagerContract.View view;
    private DeckDao deckDao;
    private List<Deck> decksList;

    public DeckManagerPresenter(DeckManagerContract.View view, Context context) {
        this.view = view;
        view.setPresenter(this);
        deckDao = new DeckDao(context);
//        cardDao = new CardDao(context);
    }

    @Override
    public List<Item> getAdapterItems() {
        decksList = deckDao.findAll();
        Log.d(TAG, "getAdapterItems: size " + decksList.size());
        List<Item> items = new ArrayList<>();
        for (Deck deck : decksList) {
            items.add(new Item(deck.getId(), deck.getDeckName(), deck.getCards().size()));
        }
        return items;
    }

    @Override
    public boolean isAnyDeck() {
        return deckDao.countAll() > 0;
    }

    //TODO copy from javafx; delete card and deck
    @Override
    public void deleteDeck(int deckID) {
        deckDao.deleteById(deckID);
    }

    @Override
    public void duplicateDeck(int deckID) {

    }

}
