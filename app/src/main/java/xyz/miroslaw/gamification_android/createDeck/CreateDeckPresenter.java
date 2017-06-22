package xyz.miroslaw.gamification_android.createDeck;


public class CreateDeckPresenter implements CreateDeckContract.Presenter {
    private CreateDeckContract.FormView formView;
    private CreateDeckContract.NavigationView navigationView;

    public CreateDeckPresenter(CreateDeckContract.NavigationView view){
        this.navigationView = view;
        view.setPresenter(this);
    }
    public CreateDeckPresenter(CreateDeckContract.FormView view){
        this.formView = view;
        view.setPresenter(this);
    }

    @Override
    public void onNextClick() {
        navigationView.setTxtTypeValue("next");
    }

    @Override
    public void onPrevClick() {
        navigationView.setTxtTypeValue("prev");
    }


    @Override
    public void onImgBtnClick(String pathImg) {
//        formView.changeTxtGetImg("switch");
//        formView.showPhoto(pathImg);
    }

}
