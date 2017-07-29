package xyz.miroslaw.gamification_android.menu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckActivity;
import xyz.miroslaw.gamification_android.deckList.DeckListActivity;
import xyz.miroslaw.gamification_android.deckManager.DeckManagerActivity;

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
        //TODO: chrome inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
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
