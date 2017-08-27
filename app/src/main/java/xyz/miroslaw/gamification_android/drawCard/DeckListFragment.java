package xyz.miroslaw.gamification_android.drawCard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckActivity;
import xyz.miroslaw.gamification_android.viewUtils.ClickListener;
import xyz.miroslaw.gamification_android.viewUtils.Item;
import xyz.miroslaw.gamification_android.viewUtils.ListAdapter;
import xyz.miroslaw.gamification_android.viewUtils.RecyclerTouchListener;

import static android.os.Build.ID;


public class DeckListFragment extends Fragment implements DrawCardContract.View {
    public static final String DECK_ID = ID;
    private final String TAG = "myDebug " + getClass().getSimpleName();

    @BindView(R.id.rv_deckList)
    RecyclerView recyclerView;
    @BindView(R.id.btn_list_add)
    Button btnAddDeck;
    @BindView(R.id.txt_list_info)
    TextView txtInfo;
    @BindView(R.id.rl_list_headerRow)
    RelativeLayout rlHeader;
    @BindView(R.id.txt_listCol_number)
    TextView txtNumberCol;
//    ActionMode actionMode;
    private ListAdapter listAdapter;
//    private int deckPosition;

    private DrawCardContract.Presenter presenter;

    public DeckListFragment() {
        // Required empty public constructor
    }

    public static DeckListFragment newInstance() {
        return new DeckListFragment();
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
            presenter = new DrawCardPresenter(this, getContext());
        }
        View view = inflater.inflate(R.layout.fragment_deck_manager, container, false);
        ButterKnife.bind(this, view);

        txtNumberCol.setText(getResources().getString(R.string.deckList_number));
        if (presenter.isAnyDeck()) {
            btnAddDeck.setVisibility(View.GONE);
            createRecyclerview();
        } else {
            btnAddDeck.setText(R.string.all_addDeck);
        }
        return view;
    }


    private void createRecyclerview() {
        final List<Item> adapterItems = presenter.getAdapterItems();
        listAdapter = new ListAdapter(adapterItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showDrawCard(adapterItems.get(position).getId());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.btn_list_add)
    void onAddDeck(){
        Intent intent = new Intent(getContext(), CreateDeckActivity.class);
        startActivity(intent);
    }

    private void showDrawCard(int id) {
        makeToast(Integer.toString(id));
        DrawCardFragment drawCardFragment = setupBundle(id);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_drawCard, drawCardFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private DrawCardFragment setupBundle(int id) {
        DrawCardFragment drawCardFragment = DrawCardFragment.newInstance();
        Bundle arg = new Bundle();
        arg.putInt(DECK_ID, id);
        drawCardFragment.setArguments(arg);
        return  drawCardFragment;
    }

    @Override
    public void setPresenter(DrawCardContract.Presenter presenter) {

    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onExit() {
    }
}
