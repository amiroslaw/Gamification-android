package xyz.miroslaw.gamification_android.viewUtils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.model.CardType;


public class TypeRange {

    public static void drawHeart(ViewGroup layout, Activity activity, CardType type) {
        View view = null;
        switch (type){
            case SMALL:
                view = activity.getLayoutInflater().inflate(R.layout.type_rating_small, null);
                break;
            case MEDIUM:
                view = activity.getLayoutInflater().inflate(R.layout.type_rating_medium, null);
                break;

            case LARGE:
                view = activity.getLayoutInflater().inflate(R.layout.type_rating_large, null);
                break;
            default:
                view = activity.getLayoutInflater().inflate(R.layout.type_rating_empty, null);
                break;
        }
        layout.removeAllViews();
        layout.addView(view);
    }
}
