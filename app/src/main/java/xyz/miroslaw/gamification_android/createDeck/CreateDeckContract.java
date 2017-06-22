package xyz.miroslaw.gamification_android.createDeck;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;

public interface CreateDeckContract {
    interface FormView extends BaseView <Presenter> {
//        String getName();
//        String getDescription();

//        void changeTxtGetImg(String txt);
        void openGallery();
        void takePhoto();

//        void showPhoto(String imgPath);
    }
    interface NavigationView extends BaseView <Presenter> {
        void  setTxtTypeValue(String value);
    }

    interface Presenter extends BasePresenter{
        void onNextClick();
        void onPrevClick();

        void onImgBtnClick(String pathImg);

//        void onImageLoaded();
//        void onImageLoadFailure();
    }

}
