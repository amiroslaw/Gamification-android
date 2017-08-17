package xyz.miroslaw.gamification_android.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import xyz.miroslaw.gamification_android.database.DatabaseHelper;
import xyz.miroslaw.gamification_android.database.DatabaseManager;
import xyz.miroslaw.gamification_android.model.BaseModel;
import xyz.miroslaw.gamification_android.model.Card;

public class CardDao implements CommonDao {

    private DatabaseHelper dbHelper;

    public CardDao(Context context) {
        dbHelper = DatabaseManager.getHelper(context);
    }

    @Override
    public void createOrUpdate(BaseModel baseModel) {
        try {
            dbHelper.getCardDao().createOrUpdate((Card) baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(BaseModel baseModel) {
        try {
            dbHelper.getCardDao().create((Card) baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(BaseModel baseModel) {
        try {
            dbHelper.getCardDao().update((Card) baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(BaseModel baseModel) {
        try {
            dbHelper.getCardDao().delete((Card) baseModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            dbHelper.getDeckDao().deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Card findById(int id) {
        Card card = null;
        try {
            card = dbHelper.getCardDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public List<Card> findAll() {
        List<Card> cards = null;
        try {
            cards = dbHelper.getCardDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }
    public List<Card> findAllFromDeck(int deckID) {
        List<Card> cards = null;
        try {
            QueryBuilder<Card, Integer> queryBuilder = dbHelper.getCardDao().queryBuilder();
            queryBuilder.where().eq("deck_id", deckID);
            cards = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }
    public int countMediumRewards(int id) throws SQLException {
        GenericRawResults<String[]> where = dbHelper.getCardDao().queryRaw("SELECT COUNT(*) FROM card WHERE type = 2 AND deck_id =" + id);
        int result = Integer.parseInt(where.getFirstResult()[0]);
        return result;
    }

    @Override
    public int countAll() {
        int amount = -1;
        try {
            amount = (int) dbHelper.getCardDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }

    public void saveAllCardsInDataBase(List<Card> cards) {
        try {
            for (Card card : cards) {
                dbHelper.getCardDao().create(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCardsInDeck(int deckID) {
        try {
            DeleteBuilder<Card, Integer> deleteBuilder = dbHelper.getCardDao().deleteBuilder();
            deleteBuilder.where().eq("deck_id", deckID);
            deleteBuilder.delete();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public int countSmallRewards(int number) throws SQLException {
//        GenericRawResults<String[]> where = getDao(Card.class).queryRaw("SELECT COUNT(*) FROM card WHERE type = 3 AND deck_id =" + number);
//        return Integer.parseInt(where.getFirstResult()[0]);
//    }


}
