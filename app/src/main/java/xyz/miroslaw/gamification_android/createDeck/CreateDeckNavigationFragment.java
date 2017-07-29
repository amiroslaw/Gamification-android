package xyz.miroslaw.gamification_android.createDeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;


public class CreateDeckNavigationFragment extends Fragment {

    private CreateDeckContract.Presenter presenter;


    public CreateDeckNavigationFragment() {
        // Required empty public constructor
    }

    public static CreateDeckNavigationFragment newInstance() {
        return new CreateDeckNavigationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (presenter == null) {
//            presenter = new CreateDeckPresenter(this);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_create_deck_navigation, container, false);
//        ButterKnife.bind(this, view);
//
//
//        return view;
        return  null;
    }

    @OnClick(R.id.btn_createCard_previous)
    public void onPrevClick() {
        presenter.onPrevClick();
    }

    @OnClick(R.id.btn_createCard_next)
    public void onNextClick() {

    }

//    @Override
//    public void setTxtTypeValue(String value) {
////        txtTypeValue.setText(value);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    @Override
//    public void setPresenter(CreateDeckContract.Presenter presenter) {
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void makeToast(String message) {
//        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//    }


}
