package xyz.miroslaw.gamification_android;

/**
 * Created by miro on 14.06.17.
 */

public interface BaseView <T> {
    void setPresenter(T presenter);

    void makeToast(String message);
}
