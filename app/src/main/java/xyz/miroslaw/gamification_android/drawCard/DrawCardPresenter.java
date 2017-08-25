package xyz.miroslaw.gamification_android.drawCard;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.drawCard.DrawCardContract.Presenter;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.model.Deck;
import xyz.miroslaw.gamification_android.viewUtils.Item;

import static android.content.ContentValues.TAG;

public class DrawCardPresenter implements Presenter{
    private DrawCardContract.View view;
    DeckDao deckDao;
    CardDao cardDao;
    //TODO: switch to Deque
    private List<Card> cards;

    public DrawCardPresenter(DrawCardContract.View view, Context context){
        this.view = view;
        view.setPresenter(this);
        deckDao = new DeckDao(context);
        cardDao = new CardDao(context);
    }

    @Override
    public Card drawCard() {
        Card removedCard = cards.remove(cards.size() - 1);
        cardDao.deleteById(removedCard.getId());
        return removedCard;
    }

    @Override
    public void initDeck(int deckID) {
        cards = cardDao.findAllFromDeck(deckID);
        swapLargeAward();
    }

    private void swapLargeAward() {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getType() == CardType.LARGE) {
                Collections.swap(cards, 0, i);
                break;
            }
        }
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
