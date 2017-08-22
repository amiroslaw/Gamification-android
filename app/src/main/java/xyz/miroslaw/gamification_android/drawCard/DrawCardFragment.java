package xyz.miroslaw.gamification_android.drawCard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;


public class DrawCardFragment extends Fragment implements DrawCardContract.View {

    public DrawCardFragment() {
        // Required empty public constructor
    }

    public static DrawCardFragment newInstance() {
        return new DrawCardFragment();
    }


    private DrawCardContract.Presenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = new DrawCardPresenter(this);
        }
        View view = inflater.inflate(R.layout.fragment_draw_card, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setPresenter(DrawCardContract.Presenter presenter) {

    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    }
