package xyz.miroslaw.gamification_android.drawCard;

import android.content.Intent;
import android.net.Uri;
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
import xyz.miroslaw.gamification_android.menu.MenuActivity;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.viewUtils.OnSwipeTouchListener;
import xyz.miroslaw.gamification_android.viewUtils.Tools;
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

    @OnClick(R.id.btn_draw_next)
    public void onNextCard() {
        Card card = presenter.drawCard();
        if(card == null){
            showEmptyCard();
        } else {
            showViews(card);
        }
    }

    private void showEmptyCard() {
        tvTitle.setText(R.string.draw_blankCard);
        tvDescription.setText(R.string.draw_sorryStatement);
        ivAward.setImageResource(R.mipmap.ic_empty);
        TypeRange.drawHeart(rlTypeValue, getActivity(), CardType.EMPTY );
    }

    private void showViews(Card card) {
        ivAward.setVisibility(View.VISIBLE);
        tvDescription.setVisibility(View.VISIBLE);
        tvTitle.setText(card.getTitle());
        tvDescription.setText(card.getDescription());
        TypeRange.drawHeart(rlTypeValue, getActivity(), card.getType());
        Uri uriImg = Tools.getUriFromPath(card.getImage());
        ivAward.setImageURI(uriImg);
    }

    @OnClick(R.id.btn_draw_exit)
    @Override
    public void onExit() {
        makeToast(getString(R.string.draw_congratulations));
        Intent intent = new Intent(getContext(), MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.saveParametersInDB();
    }

    @Override
    public void setPresenter(DrawCardContract.Presenter presenter) {

    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
