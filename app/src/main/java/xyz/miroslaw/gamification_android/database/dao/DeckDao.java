package xyz.miroslaw.gamification_android.database.dao;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import xyz.miroslaw.gamification_android.database.DatabaseHelper;
import xyz.miroslaw.gamification_android.database.DatabaseManager;
import xyz.miroslaw.gamification_android.model.BaseModel;
import xyz.miroslaw.gamification_android.model.Deck;

public class DeckDao implements CommonDao {

    private DatabaseHelper dbHelper;

    public DeckDao(Context context) {
//        DatabaseManager dbManager = new DatabaseManager();
        dbHelper = DatabaseManager.getHelper(context);
    }

    @Override
    public void createOrUpdate(BaseModel baseModel) {
        try {
            dbHelper.getDeckDao().createOrUpdate((Deck) baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(BaseModel baseModel) {
        try {
            dbHelper.getDeckDao().create((Deck) baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(BaseModel baseModel) {
        try {
            dbHelper.getDeckDao().update((Deck) baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(BaseModel baseModel) {
        try {
            dbHelper.getDeckDao().delete((Deck) baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Deck findById(int id) {
        Deck deck = null;
        try {
            deck = dbHelper.getDeckDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deck;
    }

    @Override
    public List<Deck> findAll() {
        List<Deck> decks = null;
        try {
            decks = dbHelper.getDeckDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return decks;
    }
}
