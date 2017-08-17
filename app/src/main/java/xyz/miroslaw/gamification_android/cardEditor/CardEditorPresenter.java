package xyz.miroslaw.gamification_android.cardEditor;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.viewUtils.Item;

public class CardEditorPresenter implements CardEditorContract.Presenter{

    private CardEditorContract.View view;
    private CardDao cardDao;
    private List<Card> cardList;
    public CardEditorPresenter(CardEditorContract.View view, Context context) {
        this.view = view;
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
}