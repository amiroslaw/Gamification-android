package xyz.miroslaw.gamification_android.drawCard;

import java.util.List;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.viewUtils.Item;


public interface DrawCardContract {
    interface View extends BaseView<Presenter> {

        void showEmptyCard();

        void showAward(Card card);

        void onExit();

        void showCardCounter(int counter);
    }

    interface Presenter extends BasePresenter {
        List<Item> getAdapterItems();
        boolean isAnyDeck();
        void drawCard();
        void initDeck(int deckID);

        void saveParametersInDB();
    }
}
