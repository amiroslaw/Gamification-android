package xyz.miroslaw.gamification_android.createDeck;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;

/**
 * Created by miro on 13.06.17.
 */

public interface CreateDeckContract {
    interface View extends BaseView <Presenter>{
//        String getName();
//        String getDescription();
        void  setTxtTypeValue(String value);
        String showPhoto();

    }

    interface Presenter extends BasePresenter{
        void onNextClick();
        void onPrevClick();
        void onGetImgClick();
//        void onImageLoaded();
//        void onImageLoadFailure();

    }
}
