package xyz.miroslaw.gamification_android.drawCard;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private DeckDao deckDao;
    private CardDao cardDao;
    private Deck deck;

    //TODO: switch to Deque
    private List<Card> cards;

    public DrawCardPresenter(DrawCardContract.View view, Context context){
        this.view = view;
        view.setPresenter(this);
        deckDao = new DeckDao(context);
        cardDao = new CardDao(context);
    }
    @Override
    public void initDeck(int deckID) {
        deck = deckDao.findById(deckID);
        cards = cardDao.findAllFromDeck(deckID);
        swapLargeAward();
    }

    @Override
    public void saveParametersInDB() {
        deckDao.createOrUpdate(deck);
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
    public Card drawCard() {
        Log.d(TAG, "drawCard: empty cards "+ deck.getHowManyBlankCards());
        Card removedCard = null;
        int deckSize = cards.size();
        int randomIndex = getRandomIndex();
        if (randomIndex >= 0) {
            if(deckSize == 0){
                deckDao.delete(deck);
                view.onExit();
            } else {
                removedCard = cards.remove(deckSize - 1);
                cardDao.deleteById(removedCard.getId());
            }
        }
        return removedCard;
    }

    private int getRandomIndex() {
        final int maxIndex = cards.size();
        Random random = new Random();
        int howManyBlankCards = deck.getHowManyBlankCards();
        int randomIndex = random.nextInt(maxIndex + howManyBlankCards) + 1;
        if (randomIndex > maxIndex) {
            deck.setHowManyBlankCards(--howManyBlankCards);
            return -1;
        } else {
            return randomIndex;
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
