package xyz.miroslaw.gamification_android.drawCard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

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
    @BindView(R.id.is_draw_award)
    ImageSwitcher isAward;
    @BindView(R.id.ts_draw_title)
    TextSwitcher tsTitle;
    @BindView(R.id.ts_draw_description)
    TextSwitcher tsDescription;
    @BindView(R.id.ts_draw_counter)
    TextSwitcher tsCounter;
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
        setAnimation();
        tsTitle.setCurrentText(getResources().getString(R.string.draw_info));
        presenter.initDeck(getDeckId());
        return view;
    }

    private void setAnimation() {
        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
//        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
//        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        tsTitle.setInAnimation(in);
        tsTitle.setOutAnimation(out);
        tsDescription.setInAnimation(in);
        tsDescription.setOutAnimation(out);
        tsCounter.setInAnimation(in);
        tsCounter.setOutAnimation(out);
        isAward.setOutAnimation(out);
        isAward.setInAnimation(in);
        isAward.addView(new ImageView(getContext()));
        isAward.addView(new ImageView(getContext()));
//        tsTitle.setFactory((ViewSwitcher.ViewFactory) makeView());
        tsTitle.setFactory(viewFactory);
//        tsTitle.addView(new TextView(getContext()));
//        tsTitle.addView(new TextView(getContext()));
//        tsDescription.addView(new TextView(getContext()));
//        tsDescription.addView(new TextView(getContext()));
        tsDescription.setFactory(viewFactory);
        tsCounter.setFactory(viewFactory);
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
        });
    }

    @OnClick(R.id.btn_draw_next)
    public void onNextCard() {
        presenter.drawCard();
    }
    @Override
    public void showEmptyCard() {
        tsTitle.setText(getResources().getString(R.string.draw_blankCard));
        tsDescription.setText(getResources().getString(R.string.draw_sorryStatement));
        isAward.setImageResource(R.mipmap.ic_empty);
        TypeRange.drawHeart(rlTypeValue, getActivity(), CardType.EMPTY);
    }
    @Override
    public void showAward(Card card) {
        isAward.setVisibility(View.VISIBLE);
        tsDescription.setVisibility(View.VISIBLE);
        tsTitle.setText(card.getTitle());
        tsDescription.setText(card.getDescription());
        TypeRange.drawHeart(rlTypeValue, getActivity(), card.getType());
        Uri uriImg = Tools.getUriFromPath(card.getImage());
        isAward.setImageURI(uriImg);
    }

    @OnClick(R.id.btn_draw_exit)
    @Override
    public void onExit() {
        makeToast(getString(R.string.draw_congratulations));
        Intent intent = new Intent(getContext(), MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void showCardCounter(int counter) {
        tsCounter.setText(String.valueOf(counter));
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
    private ViewSwitcher.ViewFactory viewFactory = new ViewSwitcher.ViewFactory() {
        // TODO: move to utils
        @Override
        public View makeView() {
            TextView t = new TextView(getActivity());
            t.setGravity(Gravity.CENTER);
//            t.setWidth();
//            t.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
            return t;
        }
    };

}
