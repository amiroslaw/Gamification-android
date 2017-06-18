package xyz.miroslaw.gamification_android.createDeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;


public class CreateDeckFormFragment extends Fragment implements CreateDeckContract.View {

    private CreateDeckContract.Presenter presenter;

    public CreateDeckFormFragment() {
        // Required empty public constructor
    }
    public static CreateDeckFormFragment newInstance() {
        return new CreateDeckFormFragment();
    }

    @Override
    public void setPresenter(CreateDeckContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = new CreateDeckPresenter(this);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_deck_form, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void setTxtTypeValue(String value) {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public String showPhoto() {
        return null;
    }
}
