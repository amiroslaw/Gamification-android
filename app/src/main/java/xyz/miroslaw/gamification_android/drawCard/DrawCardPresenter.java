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

public class DrawCardPresenter implements Presenter {
    private DrawCardContract.DeckListView deckListView;
    private DrawCardContract.DrawCardView drawCardView;
    private DeckDao deckDao;
    private CardDao cardDao;
    Deck deck;
    List<Card> cards;

    public DrawCardPresenter(DrawCardContract.DeckListView view, Context context) {
        this.deckListView = view;
        view.setPresenter(this);
        deckDao = new DeckDao(context);
        cardDao = new CardDao(context);
    }
    public DrawCardPresenter(DrawCardContract.DrawCardView view, Context context) {
        this.drawCardView = view;
        view.setPresenter(this);
        deckDao = new DeckDao(context);
        cardDao = new CardDao(context);
    }

    @Override
    public void initDeck(int deckID) {
        deck = deckDao.findById(deckID);
        cards = cardDao.findAllFromDeck(deckID);
        Collections.shuffle(cards);
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
    public void saveParametersInDB() {
        deckDao.createOrUpdate(deck);
    }

    @Override
    public void drawCard() {
        Card drawnCard;
        int deckSize = cards.size();
        int howManyBlankCards = deck.getHowManyBlankCards();
        final boolean hasOnlyLargeAward = howManyBlankCards == 0 && deckSize == 1;

        if (deckSize == 0) {
            drawCardView.onExit();
        } else if (hasOnlyLargeAward) {
            drawnCard = cards.remove(0);
            cardDao.deleteById(drawnCard.getId());
            deckDao.delete(deck);
            drawCardView.showAward(drawnCard);
        } else if (isBlank()) {
            drawCardView.showEmptyCard();
            deck.setHowManyBlankCards(--howManyBlankCards);
        } else {
            drawnCard = cards.remove(deckSize - 1);
            cardDao.deleteById(drawnCard.getId());
            drawCardView.showAward(drawnCard);
        }
        drawCardView.showCardCounter(howManyBlankCards + cards.size());
    }

    private boolean isBlank() {
        Random random = new Random();
        final int amountOfAwards = cards.size();
        // random from 2 to all cards -1
        int randomIndex = random.nextInt(amountOfAwards + deck.getHowManyBlankCards() - 1) + 2;
        return randomIndex > amountOfAwards;
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
