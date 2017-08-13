package xyz.miroslaw.gamification_android.menu;


import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;


public interface MenuContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void onMenuItemClick();


    }
}
