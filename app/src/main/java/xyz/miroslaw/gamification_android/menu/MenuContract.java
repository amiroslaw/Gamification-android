package xyz.miroslaw.gamification_android.menu;


import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;

/**
 * Created by miro on 13.06.17.
 */

public interface MenuContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void onMenuItemClick();


    }
}
