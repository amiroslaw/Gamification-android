package xyz.miroslaw.gamification_android.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckActivity;
import xyz.miroslaw.gamification_android.database.DatabaseHelper;
import xyz.miroslaw.gamification_android.database.dao.CardDao;
import xyz.miroslaw.gamification_android.deckList.DeckListActivity;
import xyz.miroslaw.gamification_android.deckManager.DeckManagerActivity;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.Deck;

public class MenuActivity extends AppCompatActivity implements MenuContract.View {
    private final String DEBUGTAG = getClass().getSimpleName();
    private MenuContract.Presenter presenter;
    @BindView(R.id.lv_menu_menuitems)
    ListView menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        if (presenter == null) {
            presenter = new MenuPresenter(this);
        }
        createListView();
        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        Dao<Card, Integer> cardDao = null;
        Dao<Deck, Integer> deckDao = null;

        try {
            deckDao = databaseHelper.getDeckDao();
            cardDao = databaseHelper.getCardDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(DEBUGTAG, "onCreate: test");
        Deck deck = new Deck("decks");
        deck.setDeckName("new name");
        Card card = new Card(2, "change name");
        card.setDeck(deck);
        Card card2 = new Card(2, "second");
        card2.setDeck(deck);
        Card card3dao = new Card(2, "from card dao");
        card3dao.setDeck(deck);
        CardDao daoCard = new CardDao(this);
        if (cardDao == null) {
            Log.d(DEBUGTAG, "onCreate: nullpointer");
        }
        int small = 99 ;
        try {
            deckDao.createOrUpdate(deck);
            cardDao.createOrUpdate(card);
            cardDao.createOrUpdate(card2);

            cardDao.createOrUpdate(card3dao);
            small = daoCard.countMediumRewards(33);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log.d(DEBUGTAG, "onCreate: small " + small);

    }

    private void createListView() {
        String[] menuNames = getResources().getStringArray(R.array.menu_items);
        menuItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuNames));
        menuItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                changeActivity(position);
            }
        });
    }

    private void changeActivity(int position) throws IllegalArgumentException {
        Toast.makeText(getApplicationContext(),
                "Click ListItem Number " + position, Toast.LENGTH_LONG)
                .show();
        //TODO move switch to presenter
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(this, CreateDeckActivity.class);
                break;
            case 1:
                intent = new Intent(this, DeckListActivity.class);
                break;
            case 2:
                intent = new Intent(this, DeckManagerActivity.class);
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(position));
        }
        startActivity(intent);
    }

    @Override
    public void setPresenter(MenuContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void makeToast(String message) {

    }
}
