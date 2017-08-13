package xyz.miroslaw.gamification_android.deckManager;

import java.util.List;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;
import xyz.miroslaw.gamification_android.viewUtils.Item;


public interface DeckManagerContract {
    interface View extends BaseView<Presenter> {
        void showNoDecksView();
    }

    interface Presenter extends BasePresenter {
        List<Item> getAdapterItems();

        boolean isAnyDeck();

        void deleteDeck(int deckID);

        void duplicateDeck(int deckID, String name);
    }
}
