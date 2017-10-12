package xyz.miroslaw.gamification_android.help;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.miroslaw.gamification_android.R;

public class HelpPagerAdapter  extends PagerAdapter{

    private String[] titles;
    private String[] descriptions;
    private int images[] = {R.drawable.menu, R.drawable.create, R.drawable.draw, R.drawable.manager2};
    private LayoutInflater layoutInflater;
    Context ctx;
    HelpPagerAdapter(Context context) {
        ctx = context;
        titles = context.getResources().getStringArray(R.array.help_array_title);
        descriptions = context.getResources().getStringArray(R.array.help_array_desc);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        boolean hasImage = true;
        View view = layoutInflater.inflate(R.layout.help_layout, collection, false);
//        TextView txtTitle = (TextView) view.findViewById(R.id.txt_help_title);
//        txtTitle.setText(titles[position]);
        TextView txtDesc = (TextView) view.findViewById(R.id.txt_help_description);
        txtDesc.setMovementMethod(new ScrollingMovementMethod());
        txtDesc.setText(descriptions[position]);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_help_image);
        if(hasImage) {
            imageView.setImageResource(images[position]);
        } else {
            imageView.setVisibility(View.GONE);
        }
        collection.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
