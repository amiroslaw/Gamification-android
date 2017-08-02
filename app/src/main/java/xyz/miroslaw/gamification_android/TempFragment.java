package xyz.miroslaw.gamification_android;

/**
 * Created by miro on 18.06.17.
 */

//package xyz.miroslaw.gamification_android;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import butterknife.ButterKnife;
//import xyz.miroslaw.gamification_android.createDeck.CreateDeckContract;
//import xyz.miroslaw.gamification_android.createDeck.CreateDeckPresenter;
//
//public class TemplateFragment extends Fragment implements CreateDeckContract.View {
//
//    private CreateDeckContract.Presenter presenter;
//
//    public TemplateFragment() {
//        // Required empty public constructor
//    }
//
//    public static TemplateFragment newInstance() {
//        return new TemplateFragment();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.setRetainInstance(true);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        if (presenter == null) {
//            presenter = new CreateDeckPresenter(this);
//        }
//        View view = inflater.inflate(R.layout.fragment_create_deck_navigation, container, false);
//        ButterKnife.bind(this, view);
//
//        return view;
//    }
//
//    @Override
//    public void setPresenter(CreateDeckContract.Presenter presenter) {
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    @Override
//    public void makeToast(String message) {
//        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//    }

//}
