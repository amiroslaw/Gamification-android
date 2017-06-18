package xyz.miroslaw.gamification_android.menu;

/**
 * Created by miro on 13.06.17.
 */

public class MenuPresenter implements MenuContract.Presenter {

    private MenuContract.View view;
    public MenuPresenter(MenuContract.View view){
        view.setPresenter(this);
    }


    @Override
    public void onMenuItemClick() {

    }
}
