package xyz.miroslaw.gamification_android.deckManager;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.cardEditor.CardEditorActivity;
import xyz.miroslaw.gamification_android.createDeck.CreateDeckActivity;
import xyz.miroslaw.gamification_android.viewUtils.ClickListener;
import xyz.miroslaw.gamification_android.viewUtils.ListAdapter;
import xyz.miroslaw.gamification_android.viewUtils.Item;
import xyz.miroslaw.gamification_android.viewUtils.RecyclerTouchListener;

import static android.os.Build.ID;


public class DeckManagerFragment extends Fragment implements DeckManagerContract.View, View.OnLongClickListener, ActionMode.Callback {
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
    ActionMode actionMode;
    private ListAdapter listAdapter;
    private int deckPosition;

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
        btnAddDeck.setText(R.string.all_addDeck);
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
//        btnAddDeck.setVisibility(View.VISIBLE);
        rlHeader.setVisibility(View.GONE);
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
                showCardEditor(adapterItems.get(position).getId());
            }

            @Override
            public void onLongClick(View view, int position) {
                deckPosition = position;
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
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.deckmanager_menu, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete:
                presenter.deleteDeck(deckPosition);
                listAdapter.remove(deckPosition);
                mode.finish();
                return true;
            case R.id.item_duplicate:
                showDialog();
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
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_name, null);
        final EditText etName = (EditText) view.findViewById(R.id.et_change_name);
        Button btnCancel = (Button) view.findViewById(R.id.btn_change_name_cancel);
        Button btnAccept = (Button) view.findViewById(R.id.btn_change_name_accept);

        builder.setView(view);
        final AlertDialog dialog = builder.setCancelable(false).create();
        dialog.show();
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredName = etName.getText().toString();
                boolean etIsNotEmpty = !enteredName.isEmpty();
                if (etIsNotEmpty) {
                    presenter.duplicateDeck(deckPosition, enteredName);
                    listAdapter.duplicate(deckPosition, enteredName);
                    makeToast("send " + enteredName);
                    dialog.dismiss();
                } else {
                    makeToast(getString(R.string.change_name_emptyField));
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.btn_list_add)
    void onAddDeck(){
        Intent intent = new Intent(getContext(), CreateDeckActivity.class);
        startActivity(intent);
    }

    private void showCardEditor(int id) {
        makeToast(Integer.toString(id));
        Intent intent = new Intent(getContext(), CardEditorActivity.class);
        intent.putExtra(DECK_ID, id);
        startActivity(intent);
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
