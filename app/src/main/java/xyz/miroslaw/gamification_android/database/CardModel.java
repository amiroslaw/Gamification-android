package xyz.miroslaw.gamification_android.database;


import java.util.ArrayList;
import java.util.List;

import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.Deck;

public class CardModel {
    private List<Card> cardList = new ArrayList<>();

    private Deck deck;

    public void init(Deck deck) {
        this.deck = deck;
    }
//    private DatabaseHelper databaseHelper = null;
//    private DatabaseHelper getHelper() {
//        if (databaseHelper == null) {
//            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
//        }
//        return databaseHelper;
//    }
//
//    public void getAllCards() {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        cardList = cardDao.queryForAll(Card.class);
//        DbManager.closeConnectionSource();
//    }
//
//    public List<Card> getCardFromDeck(int deckId) throws SQLException {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        List<Card> cards = cardDao.getQueryBuilder(Card.class).where().eq("deck_id", deckId).query();
//        return cards;
//    }
//
//    public void deleteCardById(Card card) {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        cardDao.deleteById(Card.class, card.getId());
//        DbManager.closeConnectionSource();
//        getAllCards();
//    }
//
//    public void deleteAllCardsInDataBase(List<Card> cards) {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        cards.forEach(card -> cardDao.deleteById(Card.class, card.getId()));
//        DbManager.closeConnectionSource();
//    }
//
//    public void saveAllCardsInDataBase(List<Card> cards) {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        cards.forEach(card -> cardDao.creatOrUpdate(card));
//        DbManager.closeConnectionSource();
//        getAllCards();
//    }
//
//    public void saveCardInDataBase(Card card) {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        cardDao.creatOrUpdate(card);
//        DbManager.closeConnectionSource();
//        getAllCards();
//    }
//
//    public int getAmountOfMediumRewards(int id) throws SQLException {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        int amount = cardDao.countMediumRewards(id);
//        DbManager.closeConnectionSource();
//        return amount;
//    }
//
//    public int getAmountOfSmallRewards(int id) throws SQLException {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        int amount = cardDao.countSmallRewards(id);
//        DbManager.closeConnectionSource();
//        return amount;
//    }

}
