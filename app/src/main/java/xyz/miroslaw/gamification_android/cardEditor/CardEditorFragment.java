package xyz.miroslaw.gamification_android.cardEditor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import xyz.miroslaw.gamification_android.createDeck.CreateCardFragment;
import xyz.miroslaw.gamification_android.deckManager.DeckManagerFragment;
import xyz.miroslaw.gamification_android.viewUtils.ClickListener;
import xyz.miroslaw.gamification_android.viewUtils.Item;
import xyz.miroslaw.gamification_android.viewUtils.ListAdapter;
import xyz.miroslaw.gamification_android.viewUtils.RecyclerTouchListener;

import static android.os.Build.ID;


public class CardEditorFragment extends Fragment implements CardEditorContract.View, View.OnLongClickListener, ActionMode.Callback {

    private CardEditorContract.Presenter presenter;
    private static final String DECK_ID = ID;
    private final String TAG = "myDebug " + getClass().getSimpleName();

    @BindView(R.id.rv_cardsList)
    RecyclerView recyclerView;
    @BindView(R.id.btn_list_add)
    Button btnAddDeck;
    @BindView(R.id.rl_list_headerRow)
    RelativeLayout rlHeader;
    @BindView(R.id.txt_listCol_number)
    TextView txtNumberCol;
    private int deckID;
    ActionMode actionMode;
    private ListAdapter listAdapter;
    private int cardPosition;

    public CardEditorFragment() {
        // Required empty public constructor
    }

    public static CardEditorFragment newInstance() {
        return new CardEditorFragment();
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
            presenter = new CardEditorPresenter(this, getContext());
        }
        View view = inflater.inflate(R.layout.fragment_card_editor, container, false);
        ButterKnife.bind(this, view);

        deckID = getDeckIdFromBundle();
        makeToast(String.valueOf(deckID));
        txtNumberCol.setText(getResources().getString(R.string.cardList_type));
        btnAddDeck.setText(R.string.cardEditor_addCard);
        createRecyclerview();
        return view;
    }

    private int getDeckIdFromBundle() {
        Bundle extras = getActivity().getIntent().getExtras();
        int id;
        if (extras != null) {
            id = extras.getInt(DeckManagerFragment.DECK_ID);
        } else throw new IllegalArgumentException("extras is null");
        return id;
    }
    private void createRecyclerview() {

        final List<Item> adapterItems = presenter.getAdapterItems(deckID);
        listAdapter = new ListAdapter(adapterItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showCardEditor(adapterItems.get(position).getId());
            }

            @Override
            public void onLongClick(View view, int position) {
                //TODO presenter with position
//                deckPosition = adapterItems.get(position).getId();
                cardPosition = position;
                CardEditorFragment.this.onLongClick(view);
            }
        }));
    }

    private void showCardEditor(int id) {
    }

    @Override
    public boolean onLongClick(View view) {
        if (actionMode != null) {
            return false;
        }
        actionMode = getActivity().startActionMode(this);
        view.setSelected(true);
        return true;
    }

    // 4. Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.deckmanager_menu, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete:
                //TODO presenter and update data
                presenter.deleteCard(cardPosition);
//                createRecyclerview();
                listAdapter.remove(cardPosition);
                mode.finish();
                return true;
            case R.id.item_duplicate:
                presenter.duplicateCard(cardPosition);
                listAdapter.duplicate(cardPosition);
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    // 6. Called each time the action mode is shown. Always called after onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false; // Return false if nothing is done
    }
    // 7. Called when the user exits the action mode
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
    }


    @OnClick(R.id.btn_list_add)
    void onAddCard(){
        Fragment newFragment = new CreateCardFragment();

        View view = newFragment.inflate(R.layout.fragment_create_card, container, false);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment_cardEditor, newFragment);
        transaction.addToBackStack(null);


// Commit the transaction
        transaction.commit();
    }
    @Override
    public void setPresenter(CardEditorContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
