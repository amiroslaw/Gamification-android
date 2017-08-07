package xyz.miroslaw.gamification_android.deckManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.database.dao.DeckDao;
import xyz.miroslaw.gamification_android.model.Deck;
import xyz.miroslaw.gamification_android.viewUtils.ClickListener;
import xyz.miroslaw.gamification_android.viewUtils.DeckListAdapter;
import xyz.miroslaw.gamification_android.viewUtils.Item;
import xyz.miroslaw.gamification_android.viewUtils.RecyclerTouchListener;


public class DeckManagerFragment extends Fragment implements DeckManagerContract.View, View.OnLongClickListener, ActionMode.Callback {
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
    ActionMode actionMode;
    private DeckDao deckDao;
    private List<Deck> decksList;
    private DeckManagerContract.Presenter presenter;

    public DeckManagerFragment() {
        // Required empty public constructor
    }

    public static DeckManagerFragment newInstance() {
        return new DeckManagerFragment();
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
            presenter = new DeckManagerPresenter(this);
        }
        View view = inflater.inflate(R.layout.fragment_deck_manager, container, false);
        ButterKnife.bind(this, view);
        //TODO: move to presenter
        deckDao = new DeckDao(view.getContext());
        checkListSize();
        txtNumberCol.setText(getResources().getString(R.string.deckList_number));
        createRecyclerview();

        return view;
    }

    private void checkListSize() {
        //TODO: move to presenter
        decksList = deckDao.findAll();
        if(decksList.size() == 10) {
            txtInfo.setVisibility(View.VISIBLE);
            btnAddDeck.setVisibility(View.VISIBLE);
            btnAddDeck.setText(R.string.all_addDeck);
            rlHeader.setVisibility(View.GONE);
        }
    }

    private void createRecyclerview() {

        DeckListAdapter deckListAdapter = new DeckListAdapter(convertToItem());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deckListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //TODO presenter  open editcard
                Deck deck = decksList.get(position);
                Toast.makeText(getContext().getApplicationContext(), deck.getDeckName() + " is selected! ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
             //TODO presenter with position
                DeckManagerFragment.this.onLongClick(view);
            }
        }));
    }
    //TODO: move to presenter
    private List<Item> convertToItem() {
        List<Item> items = new ArrayList<>();

        for (Deck deck : decksList) {
            items.add(new Item(deck.getDeckName(), deck.getCards().size()));
        }
        makeToast("size " + items.size());
        return items;
    }

    @Override
    public boolean onLongClick(View view) {
//         if actionmode is null "not started"
        if (actionMode != null) {
            return false;
        }
        // Start the CAB
        actionMode = getActivity().startActionMode(this);
        view.setSelected(true);
        return true;
    }
    // 4. Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.deckmanager_menu, menu);
        return true;
    }

    // 5. Called when the user click share item
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete:
                //TODO presenter and update data
                Toast.makeText(getContext(), "item_delete!", Toast.LENGTH_SHORT).show();
                mode.finish(); // Action picked, so close the CAB
                return true;
            case R.id.item_duplicate:
                Toast.makeText(getContext(), "item_duplicate!", Toast.LENGTH_SHORT).show();
                //TODO presenter
                mode.finish(); // Action picked, so close the CAB
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

    @Override
    public void setPresenter(DeckManagerContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
