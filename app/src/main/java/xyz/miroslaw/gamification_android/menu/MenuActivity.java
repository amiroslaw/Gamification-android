package xyz.miroslaw.gamification_android.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.lv_menu_menuitems) ListView menuItems;
//    private ArrayAdapter<String> arrayAdapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        createListView();
    }

    private void createListView() {
        String[] menuNames = getResources().getStringArray(R.array.menu_items);
        menuItems.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuNames));
    }
}
