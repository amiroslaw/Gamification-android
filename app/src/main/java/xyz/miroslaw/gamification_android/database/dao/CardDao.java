package xyz.miroslaw.gamification_android.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.sql.SQLException;

import xyz.miroslaw.gamification_android.database.DatabaseHelper;
import xyz.miroslaw.gamification_android.database.DatabaseManager;
import xyz.miroslaw.gamification_android.model.Card;

public class CardDao{

    Dao<Card, Integer> cardDao;
    DatabaseHelper dbHelper;

    public CardDao(Context context) {

        dbHelper= DatabaseManager.getHelper(context);
    }
//    public CardDao(Context context) {
//        databaseManager = new DatabaseManager();
//        try {
//            cardDao = databaseManager.getHelper(context).getCardDao();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void create(Card card) {
        try {
            dbHelper.getCardDao().create(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseManager.releaseHelper();
    }
    public int countMediumRewards(int id) throws SQLException {
//        GenericRawResults<String[]> where = getDao().queryRaw("SELECT COUNT(*) FROM card WHERE type = 2 AND deck_id =" + id);
        GenericRawResults<String[]> where = cardDao.queryRaw("SELECT COUNT(*) FROM card WHERE type = 2");
        int result =  Integer.parseInt(where.getFirstResult()[0]);

        DatabaseManager.releaseHelper();
        return result;
    }

//    public int countSmallRewards(int id) throws SQLException {
//        GenericRawResults<String[]> where = getDao(Card.class).queryRaw("SELECT COUNT(*) FROM card WHERE type = 3 AND deck_id =" + id);
//        return Integer.parseInt(where.getFirstResult()[0]);
//    }



}
