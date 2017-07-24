package xyz.miroslaw.gamification_android.createDeck;

import xyz.miroslaw.gamification_android.BasePresenter;
import xyz.miroslaw.gamification_android.BaseView;

public interface CreateDeckContract {
    interface FormView extends BaseView <Presenter> {
        //        String getDescription();
        //        void changeTxtGetImg(String txt);
        //        String getName();
//        void showPhoto(String imgPath);

        void takePhoto();
        void openGallery();

        void clearTexts();
    }
    interface NavigationView extends BaseView <Presenter> {
        void  setTxtTypeValue(String value);
    }

    interface Presenter extends BasePresenter{
        void onNextClick(String name, String description);
        void onPrevClick();
        void onImgBtnClick(String pathImg);

//        void onImageLoaded();
//        void onImageLoadFailure();
    }

}
