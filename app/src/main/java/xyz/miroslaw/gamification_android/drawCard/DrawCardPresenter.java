package xyz.miroslaw.gamification_android.drawCard;


import xyz.miroslaw.gamification_android.drawCard.DrawCardContract.Presenter;

public class DrawCardPresenter implements Presenter{
    private DrawCardContract.View view;

    public DrawCardPresenter(DrawCardContract.View view){
        this.view = view;
        view.setPresenter(this);
    }
}
