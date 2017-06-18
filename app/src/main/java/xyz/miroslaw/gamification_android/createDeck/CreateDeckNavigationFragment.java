package xyz.miroslaw.gamification_android.createDeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;


public class CreateDeckNavigationFragment extends Fragment implements CreateDeckContract.NavigationView{

    private CreateDeckContract.Presenter presenter;
    @BindView(R.id.txt_createDeck_typeValue) TextView typeValue;

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
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if (presenter == null) {
            presenter = new CreateDeckPresenter(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_deck_navigation, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_createDeck_previous)
    public void onPrevClick(){
        presenter.onPrevClick();
    }
    @OnClick(R.id.btn_createDeck_next)
    public void onNextClick(){
        presenter.onNextClick();
    }

    @Override
    public void setTxtTypeValue(String value) {
        typeValue.setText(value);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setPresenter(CreateDeckContract.Presenter presenter) {
        this.presenter =  presenter;
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
