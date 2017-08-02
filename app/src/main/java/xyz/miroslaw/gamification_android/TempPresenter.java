package xyz.miroslaw.gamification_android;

/**
 * Created by miro on 25.06.17.
 */

public class TempPresenter  implements TempContract.Presenter {
    private TempContract.View view;


    public TempPresenter(TempContract.View view){
        this.view = view;
        view.setPresenter(this);
    }


}
