package xyz.miroslaw.gamification_android;


public interface BaseView <T>{
    void setPresenter(T presenter);

    void makeToast(String message);
}
