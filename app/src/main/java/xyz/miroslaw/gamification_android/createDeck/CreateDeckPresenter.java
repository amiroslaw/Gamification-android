package xyz.miroslaw.gamification_android.createDeck;

/**
 * Created by miro on 13.06.17.
 */

public class CreateDeckPresenter implements CreateDeckContract.Presenter {
    private CreateDeckContract.View view;

    public CreateDeckPresenter(CreateDeckContract.View view){
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void onNextClick() {
        view.setTxtTypeValue("next");

    }

    @Override
    public void onPrevClick() {
        view.setTxtTypeValue("prev");
    }

    @Override
    public void onGetImgClick() {

    }
}
