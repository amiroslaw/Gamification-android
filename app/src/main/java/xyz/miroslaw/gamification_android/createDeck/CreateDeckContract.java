package xyz.miroslaw.gamification_android.createDeck;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;

public interface CreateDeckContract {
    interface View extends BaseView <Presenter> {
        void takePhoto();
        void openGallery();

        void setTxtTypeValue(String value);
        void setPrevCardValues(String name, String description, String pathImg);
        void clearTexts();
        void disableReturning(boolean enable);

        void showDialog();
    }

    interface Presenter extends BasePresenter{
        void onNextClick(String name, String description);
        void onPrevClick();
        void onImgBtnClick(String pathImg);
        void setDeckName(String deckName);

//        void onImageLoaded();
//        void onImageLoadFailure();
    }

}
