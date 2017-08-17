package xyz.miroslaw.gamification_android.cardEditor;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.viewUtils.Item;

public class CardEditorPresenter implements CardEditorContract.Presenter{

    private CardEditorContract.CardListView cardListView;
    private CardEditorContract.CreateView createView;
    private CardDao cardDao;
    private List<Card> cardList;
    public CardEditorPresenter(CardEditorContract.CardListView view, Context context) {
        this.cardListView = view;
        view.setPresenter(this);
        cardDao = new CardDao(context);
    }
    public CardEditorPresenter(CardEditorContract.CreateView view, Context context) {
        this.createView = view;
        view.setPresenter(this);
        cardDao = new CardDao(context);
    }

    @Override
    public List<Item> getAdapterItems(int deckID) {
        cardList = cardDao.findAllFromDeck(deckID);
        List<Item> items = new ArrayList<>();
        for (Card card : cardList) {
            items.add(new Item(card.getId(), card.getTitle(), card.getType().toString()));
        }
        return items;
    }

    @Override
    public void deleteCard(int position) {
        Card deleted = cardList.get(position);
        cardDao.delete(deleted);
        cardList.remove(position);
    }

    @Override
    public void duplicateCard(int position) {
        Card original = cardDao.findById(cardList.get(position).getId());
        Card newCard = new Card(original);
        cardList.add(newCard);
        cardDao.create(newCard);
    }

    @Override
    public void onSaveCard(String name, String string) {

    }

    @Override
    public Card getCard(int cardID) {
        return cardDao.findById(cardID);

    }
}