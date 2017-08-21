package xyz.miroslaw.gamification_android.cardEditor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.model.Card;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.viewUtils.Communicator;
import xyz.miroslaw.gamification_android.viewUtils.Tools;

import static android.app.Activity.RESULT_OK;


public class CreateCardFragment extends Fragment implements CardEditorContract.CreateView, AdapterView.OnItemSelectedListener {

    private static final String STATE_IMG = "imagePath", STATE_TYPE = "cardType", STATE_STARTED = "Started";
    private static final int SELECT_FILE = 0;
    private final String TAG = "myDebug " + getClass().getSimpleName();
    @BindView(R.id.iv_createCard_award)
    ImageView ivAward;
    @BindView(R.id.et_createCard_name)
    EditText etName;
    @BindView(R.id.et_createCard_description)
    EditText etDescription;
    @BindView(R.id.rl_createCard_typeValue)
    RelativeLayout rlTypeValue;
    @BindView(R.id.btn_createCard_previous)
    Button btnCancel;
    @BindView(R.id.btn_createCard_next)
    Button btnSave;
    @BindView(R.id.spinner_createCard_type)
    Spinner spinnerType;
    private boolean isFormEmpty = true;
    private CardEditorContract.Presenter presenter;
    private Card card = new Card();
    private int deckID;
    private Communicator comm;

    public CreateCardFragment() {
        // Required empty public constructor
    }

    public static CreateCardFragment newInstance() {
        return new CreateCardFragment();
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
            presenter = new CardEditorPresenter((CardEditorContract.CreateView) this, getContext());
        }
        View view = inflater.inflate(R.layout.fragment_create_card, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        setupSpinner();
        getCard();
        return view;
    }

    private void setupViews() {
        rlTypeValue.setVisibility(View.GONE);
        btnCancel.setText(R.string.all_cancel);
        btnCancel.setEnabled(true);
        btnSave.setText(R.string.all_save);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(this);
    }

    private void getCard() {
        Bundle args = getArguments();
        deckID = args.getInt(CardEditorFragment.DECK_ID);
        if (args.containsKey(CardEditorFragment.CARD_ID)) {
            int cardID = getArguments().getInt(CardEditorFragment.CARD_ID);
            card = presenter.getCard(cardID);
            deckID = card.getDeck().getId();
            isFormEmpty = false;
            setForm();
        }

    }

    @OnClick(R.id.btn_createCard_setImg)
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                ivAward.setImageURI(selectedImageUri);
                card.setImage(Tools.getRealPathFromURI(getContext(), selectedImageUri));
            }
        }
    }

    @OnClick(R.id.btn_createCard_next)
    public void onSaveCard() {
        String name = etName.getText().toString();
        if (name.equals("")) {
            makeToast(getResources().getString(R.string.all_enterName));
            etName.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight));
        } else {
            etName.setBackgroundColor(000);
            card.setTitle(name);
            card.setDescription(etDescription.getText().toString());
            presenter.onSaveCard(card, deckID);
            comm.changeFragment(null);
        }
    }

    @OnClick(R.id.btn_createCard_previous)
    public void onCancel() {
        comm.changeFragment(null);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_IMG, card.getImage());
        outState.putString(STATE_TYPE, card.getType().toString());
        outState.putBoolean(STATE_STARTED, isFormEmpty);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String imgPath = savedInstanceState.getString(STATE_IMG);
            ivAward.setImageURI(Tools.getUriFromPath(imgPath));
            String type = savedInstanceState.getString(STATE_TYPE);
            setSpinnerSelection(CardType.valueOf(type));
            isFormEmpty = savedInstanceState.getBoolean(STATE_STARTED);
        }
    }

    private void setForm() {
        etName.setText(card.getTitle());
        etDescription.setText(card.getDescription());
        ivAward.setImageURI(Tools.getUriFromPath(card.getImage()));
        setSpinnerSelection(card.getType());
    }

    private void setSpinnerSelection(CardType type) {
        switch (type) {
            case SMALL:
                spinnerType.setSelection(0);
                break;
            case MEDIUM:
                spinnerType.setSelection(1);
                break;
            case LARGE:
                spinnerType.setSelection(2);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = (String) parent.getItemAtPosition(position);
        card.setType(CardType.valueOf(selectedItem.toUpperCase()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void setCommunicator(Communicator comm){
        this.comm = comm;
    }

    @Override
    public void setPresenter(CardEditorContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}



