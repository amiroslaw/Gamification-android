package xyz.miroslaw.gamification_android.menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckActivity;
import xyz.miroslaw.gamification_android.deckManager.DeckManagerActivity;

public class MenuActivity extends AppCompatActivity implements MenuContract.View {
    private final int PERMISSIONS_REQUEST_STORAGE = 5;
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

        //chrome inspect
        Stetho.initializeWithDefaults(this);
        checkPermission();
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
                intent = new Intent(this, DeckManagerActivity.class);
                break;
            case 2:
//                intent = new Intent(this, DeckListActivity.class);
                intent = null;
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

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_STORAGE) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // do something here to handle degraded mode
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
