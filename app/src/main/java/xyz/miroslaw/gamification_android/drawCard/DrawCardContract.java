package xyz.miroslaw.gamification_android.drawCard;

import java.util.List;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.viewUtils.Item;


public interface DrawCardContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        List<Item> getAdapterItems();
        boolean isAnyDeck();
        Card drawCard();
        void initDeck(int deckID);
    }
}
