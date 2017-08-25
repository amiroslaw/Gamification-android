package xyz.miroslaw.gamification_android.drawCard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.viewUtils.OnSwipeTouchListener;
import xyz.miroslaw.gamification_android.viewUtils.TypeRange;


public class DrawCardFragment extends Fragment implements DrawCardContract.View {
    //TODO; check if it will be used
    @BindView(R.id.btn_draw_next)
    Button btnNextCard;
    @BindView(R.id.btn_draw_exit)
    Button btnExitActivity;
    @BindView(R.id.iv_draw_award)
    ImageView ivAward;
    @BindView(R.id.tv_draw_title)
    TextView tvTitle;
    @BindView(R.id.tv_draw_description)
    TextView tvDescription;
    @BindView(R.id.rl_draw_typeValue)
    RelativeLayout rlTypeValue;


    public DrawCardFragment() {
        // Required empty public constructor
    }

    public static DrawCardFragment newInstance() {
        return new DrawCardFragment();
    }

    private final String TAG = "myDebug " + getClass().getSimpleName();
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
            presenter = new DrawCardPresenter(this, getContext());
        }
        View view = inflater.inflate(R.layout.fragment_draw_card, container, false);
        ButterKnife.bind(this, view);
        setSwipe(view);
        tvTitle.setText(R.string.draw_info);
        presenter.initDeck(getDeckId());
        return view;
    }

    private int getDeckId() {
        Bundle args = getArguments();
        return args.getInt(DeckListFragment.DECK_ID);
    }

    private void setSwipe(final View view) {
        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                onNextCard();
            }
//            @Override
//            public void onSwipeRight() {
//                makeToast("swipeRight");
//            }
        });
    }
    public void showTypeValue(CardType type) {
        TypeRange.drawHeart(rlTypeValue, getActivity(), type);
    }
    Card currentCart;
    @OnClick(R.id.btn_draw_next)
    public void onNextCard() {
        currentCart = presenter.drawCard();
        makeToast(currentCart.getTitle());
        showViews();
    }

    private void showViews() {
        ivAward.setVisibility(View.VISIBLE);
        tvDescription.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_draw_exit)
    public void onExit() {

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
