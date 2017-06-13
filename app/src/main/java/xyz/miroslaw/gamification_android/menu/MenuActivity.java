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
//    error: failed to push some refs to 'https://github.com/amiroslaw/Gamification-android.git'
//    hint: Updates were rejected because the remote contains work that you do
//    To https://github.com/amiroslaw/Gamification-android.git
//    hint: not have locally. This is usually caused by another repository pushing
//!	refs/heads/master:refs/heads/master	[rejected] (fetch first)
//    hint: to the same ref. You may want to first integrate the remote changes
//            Done
//    hint: (e.g., 'git pull ...') before pushing again.
//            hint: See the 'Note about fast-forwards' in 'git push --help' for details.

    private void createListView() {
        String[] menuNames = getResources().getStringArray(R.array.menu_items);
        menuItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuNames));
    }
}
