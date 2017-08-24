package xyz.miroslaw.gamification_android.drawCard;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.drawCard.DrawCardContract.Presenter;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.Deck;
import xyz.miroslaw.gamification_android.viewUtils.Item;

import static android.content.ContentValues.TAG;

public class DrawCardPresenter implements Presenter{
    private DrawCardContract.View view;
    DeckDao deckDao;
    CardDao cardDao;

    public DrawCardPresenter(DrawCardContract.View view, Context context){
        this.view = view;
        view.setPresenter(this);
        deckDao = new DeckDao(context);
        cardDao = new CardDao(context);
    }

    @Override
    public Card drawCard() {
        return null;
    }

    @Override
    public void initDeck() {

    }

    @Override
    public List<Item> getAdapterItems() {
        List<Deck> decksList = deckDao.findAll();
        Log.d(TAG, "getAdapterItems: size " + decksList.size());
        List<Item> items = new ArrayList<>();
        for (Deck deck : decksList) {
            items.add(new Item(deck.getId(), deck.getDeckName(), Integer.toString(deck.getCards().size())));
        }
        return items;
    }

    @Override
    public boolean isAnyDeck() {
        return deckDao.countAll() > 0;
    }

}
