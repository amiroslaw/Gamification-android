package xyz.miroslaw.gamification_android.database;

import java.util.ArrayList;
import java.util.List;

import xyz.miroslaw.gamification_android.model.Deck;


public class DeckModel {
    private List<Deck> deckList = new ArrayList<>();
    private Deck currentDeck = new Deck();

//    public void getAllDecks() {
//        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
//        deckList = deckDao.queryForAll(Decks.class);
//        DbManager.closeConnectionSource();
//    }
//    public void getDeckById(int id)   {
//
//        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
//        currentDeck = deckDao.findById(Decks.class, id);
//        DbManager.closeConnectionSource();
////        getAllDecks();
//    }
//    public void getDeckById(Decks deck)   {
//
//        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
//        currentDeck = deckDao.findById(Decks.class, deck.getId());
//        DbManager.closeConnectionSource();
////        getAllDecks();
//    }
//    public void deleteDeckById(Decks deck)   {
//
//        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
//        deckDao.deleteById(Decks.class, deck.getId());
//        DbManager.closeConnectionSource();
//        getAllDecks();
//    }
//
//    public void saveAllDecksInDataBase(List<Decks> decks)   {
//        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
//        decks.forEach(card -> deckDao.creatOrUpdate(card));
//        DbManager.closeConnectionSource();
//        getAllDecks();
//    }
//
//    public void saveDeckInDataBase(Decks deck) {
//        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
//        deckDao.creatOrUpdate(deck);
//        DbManager.closeConnectionSource();
//        getAllDecks();
//    }
//
//    public List<Decks> getDeckList(){
//        return deckList;
//    }
//    public Decks getCurrentDeck() {
//        return currentDeck;
//    }
}
