package xyz.miroslaw.gamification_android.deckList;


public class DeckListPresenter implements DeckListContract.Presenter {
    private DeckListContract.View view;


    public DeckListPresenter(DeckListContract.View view){
        this.view = view;
        view.setPresenter(this);
    }

}