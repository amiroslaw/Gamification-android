package xyz.miroslaw.gamification_android.createDeck;

import android.app.AlertDialog;
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
import xyz.miroslaw.gamification_android.drawCard.DrawCardActivity;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.viewUtils.OnSwipeTouchListener;
import xyz.miroslaw.gamification_android.viewUtils.Tools;
import xyz.miroslaw.gamification_android.viewUtils.TypeRange;

import static android.app.Activity.RESULT_OK;


public class CreateDeckFragment extends Fragment implements CreateDeckContract.View {

    private final String TAG = "myDebug " + getClass().getSimpleName();

    private static final String STATE_IMG = "imagePath", STATE_TYPE = "cardType";
    private static final int SELECT_FILE = 0;
    @BindView(R.id.iv_createCard_award)
    ImageView ivAward;
    @BindView(R.id.et_createCard_name)
    EditText etName;
    @BindView(R.id.et_createCard_description)
    EditText etDescription;
    @BindView(R.id.rl_createCard_typeValue)
    RelativeLayout rlTypeValue;
    @BindView(R.id.btn_createCard_previous)
    Button btnPrev;
    @BindView(R.id.spinner_createCard_type)
    Spinner spinnerType;
    private CreateDeckContract.Presenter presenter;
    private boolean isFirstCard = true;
    private String imgPath ="";
    private String type = "EMPTY";

    public CreateDeckFragment() {
        // Required empty public constructor
    }

    public static CreateDeckFragment newInstance() {
        return new CreateDeckFragment();
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
            presenter = new CreateDeckPresenter(this, getContext());
        }
        View view = inflater.inflate(R.layout.fragment_create_card, container, false);
        setSwipe(view);
        ButterKnife.bind(this, view);
        spinnerType.setVisibility(View.GONE);
        TypeRange.drawHeart(rlTypeValue, getActivity(), CardType.LARGE);
        return view;
    }

    private void setSwipe(final View view) {
        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                onNextCard();
            }

            @Override
            public void onSwipeRight() {
                if (!isFirstCard) presenter.onPrevClick();
            }
        });
    }

    @Override
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
                imgPath = Tools.getRealPathFromURI(getContext(), selectedImageUri);
                presenter.onImgBtnClick(imgPath);
            }
        }
    }

    @OnClick(R.id.btn_createCard_next)
    public void onNextCard() {
        String name = etName.getText().toString();
        if (name.equals("")) {
            makeToast(getResources().getString(R.string.all_enterName));
            etName.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight));
        } else {
            etName.setBackgroundColor(000);
            presenter.onNextClick(name, etDescription.getText().toString());
        }
    }

    @OnClick(R.id.btn_createCard_previous)
    public void onPreviousCard() {
        presenter.onPrevClick();
    }

    @Override
    public void setPrevCardValues(String name, String description, String imgPath) {
        this.imgPath = imgPath;
        etName.setText(name);
        etDescription.setText(description);
        ivAward.setImageURI(Tools.getUriFromPath(imgPath));
    }

    @Override
    public void clearTexts() {
        etDescription.setText("");
        etName.setText("");
        ivAward.setImageDrawable(null);
    }

    @Override
    public void disableReturning(boolean disable) {
        isFirstCard = disable;
        btnPrev.setEnabled(!disable);
    }

    @Override
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
                    presenter.saveDeck(enteredName);
                    startDrawCardActivity();
                    dialog.dismiss();
                } else {
                    makeToast(getString(R.string.change_name_emptyField));
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousCard();
                dialog.dismiss();
            }
        });
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_IMG, imgPath);
        outState.putString(STATE_TYPE, type);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            imgPath = savedInstanceState.getString(STATE_IMG);
            ivAward.setImageURI(Tools.getUriFromPath(imgPath));
            type = savedInstanceState.getString(STATE_TYPE);
            showTypeValue(CardType.valueOf(type));
        }
    }

    @Override
    public void showTypeValue(CardType type) {
        this.type = type.toString();
        TypeRange.drawHeart(rlTypeValue, getActivity(), type);
    }

    @Override
    public void startDrawCardActivity() {
        Intent intent = new Intent(getActivity(), DrawCardActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(CreateDeckContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}



