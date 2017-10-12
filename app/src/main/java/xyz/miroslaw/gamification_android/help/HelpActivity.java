package xyz.miroslaw.gamification_android.help;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPagerFragment);
        viewPager.setAdapter(new HelpPagerAdapter(this));
    }
}
