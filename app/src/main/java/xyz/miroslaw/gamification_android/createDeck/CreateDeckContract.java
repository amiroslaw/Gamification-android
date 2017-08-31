package xyz.miroslaw.gamification_android.createDeck;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;
import xyz.miroslaw.gamification_android.model.CardType;

public interface CreateDeckContract {
    interface View extends BaseView <Presenter> {
        void openGallery();
        void showTypeValue(CardType type);
        void setPrevCardValues(String name, String description, String pathImg);
        void clearTexts();
        void disableReturning(boolean enable);
        void showDialog();
        void startDrawCardActivity();
    }

    interface Presenter extends BasePresenter{
        void onNextClick(String name, String description);
        void onPrevClick();
        void onImgBtnClick(String pathImg);
        void setDeckName(String deckName);
    }

}
