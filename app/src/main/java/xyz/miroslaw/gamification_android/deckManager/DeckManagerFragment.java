package xyz.miroslaw.gamification_android.deckManager;

import android.content.Intent;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckActivity;
import xyz.miroslaw.gamification_android.viewUtils.ClickListener;
import xyz.miroslaw.gamification_android.viewUtils.DeckListAdapter;
import xyz.miroslaw.gamification_android.viewUtils.Item;
import xyz.miroslaw.gamification_android.viewUtils.RecyclerTouchListener;

import static android.os.Build.ID;


public class DeckManagerFragment extends Fragment implements DeckManagerContract.View, View.OnLongClickListener, ActionMode.Callback {
    private static final String DECK_ID = ID;
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
    ActionMode actionMode;
    private int deckID;

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
            presenter = new DeckManagerPresenter(this, getContext());
        }
        View view = inflater.inflate(R.layout.fragment_deck_manager, container, false);
        ButterKnife.bind(this, view);
        txtNumberCol.setText(getResources().getString(R.string.deckList_number));

        if (presenter.isAnyDeck()) {
            createRecyclerview();
        } else {
            showNoDecksView();
        }
        return view;
    }

    @Override
    public void showNoDecksView() {
        txtInfo.setVisibility(View.VISIBLE);
        btnAddDeck.setVisibility(View.VISIBLE);
        btnAddDeck.setText(R.string.all_addDeck);
        rlHeader.setVisibility(View.GONE);
    }
    private DeckListAdapter deckListAdapter;

    private void createRecyclerview() {

        final List<Item> adapterItems = presenter.getAdapterItems();
        deckListAdapter = new DeckListAdapter(adapterItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deckListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showCardEditor(adapterItems.get(position).getId());
                makeToast(Integer.toString(position));
            }

            @Override
            public void onLongClick(View view, int position) {
                //TODO presenter with position
                deckID = adapterItems.get(position).getId();
                DeckManagerFragment.this.onLongClick(view);
            }
        }));
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
                presenter.deleteDeck(deckID);
                createRecyclerview();
                Toast.makeText(getContext(), "item_delete! "+item, Toast.LENGTH_SHORT).show();
                mode.finish();
                return true;
            case R.id.item_duplicate:
                Toast.makeText(getContext(), "item_duplicate!", Toast.LENGTH_SHORT).show();
                //TODO presenter
                presenter.duplicateDeck(deckID);
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
        deckID = -1;
    }

    @OnClick(R.id.btn_list_add)
    void onAddDeck(){
        Intent intent = new Intent(getContext(), CreateDeckActivity.class);
        startActivity(intent);
    }

    private void showCardEditor(int id) {
//        makeToast(Integer.toString(id));
//        Intent intent = new Intent(getContext(), CardEditorActivity.class);
//        intent.putExtra(DECK_ID, id);
//        startActivity(intent);
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
