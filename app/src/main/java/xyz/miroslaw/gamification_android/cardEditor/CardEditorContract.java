package xyz.miroslaw.gamification_android.cardEditor;


import java.util.List;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;
import xyz.miroslaw.gamification_android.viewUtils.Item;

public interface CardEditorContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        List<Item> getAdapterItems(int deckID);

        void deleteCard(int cardPosition);

        void duplicateCard(int cardPosition);
    }
}
