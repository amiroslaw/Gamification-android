package xyz.miroslaw.gamification_android.createDeck;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.model.CardType;
import xyz.miroslaw.gamification_android.viewUtils.OnSwipeTouchListener;
import xyz.miroslaw.gamification_android.viewUtils.TypeRange;


public class CreateCardFragment extends Fragment implements CreateDeckContract.View {
    private static final int SELECT_FILE = 0, REQUEST_CAMERA = 1;
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
    private CreateDeckContract.Presenter presenter;
    private boolean isFirstCard = true;

    public CreateCardFragment() {
        // Required empty public constructor
    }

    public static CreateCardFragment newInstance() {
        return new CreateCardFragment();
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
        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                makeToast("swipeLeft");
                onNextCard();
            }
            @Override
            public void onSwipeRight() {
                makeToast("swipeRight");
                if(!isFirstCard) presenter.onPrevClick();
            }
        });
        ButterKnife.bind(this, view);
        TypeRange.drawHeart(rlTypeValue, getActivity(), CardType.LARGE);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    @OnClick(R.id.btn_createCard_setImg)
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    @OnClick(R.id.btn_createCard_cameraImg)
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ivAward.setImageBitmap(imageBitmap);
                sendImgPath(data.getData());

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                sendImgPath(selectedImageUri);
                ivAward.setImageURI(selectedImageUri);
            }

        }
    }

    private void sendImgPath(Uri selectedImageUri) {
        String imgPath = getRealPathFromURI(getContext(), selectedImageUri);
        presenter.onImgBtnClick(imgPath);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
    public void showTypeValue(CardType type) {
        TypeRange.drawHeart(rlTypeValue, getActivity(), type);
    }

    @Override
    public void setPrevCardValues(String name, String description, String pathImg) {
        etName.setText(name);
        etDescription.setText(description);

        boolean isPathInvalid = pathImg.equals("") || pathImg == null;
        if (!isPathInvalid) {
            Picasso.with(getContext()).load(pathImg).into(ivAward);
        }
    }

    @OnClick(R.id.btn_createCard_next)
    public void onNextCard() {
        String name = etName.getText().toString();
        if (name.equals("")) {
            makeToast(getResources().getString(R.string.all_enterName));
//            etName.setHighlightColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
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
                    makeToast("send " + enteredName);
                    presenter.setDeckName(enteredName);
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
}
//    @Override
//    public void showPhoto(String imgPath) {
//        String path = Environment.getExternalStorageDirectory() + imgPath;
//        Log.d("pathshow", path);
//        File file = new File(path);
//        if(file.exists()){
//            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            ivAward.setImageBitmap(myBitmap);
//            makeToast(imgPath);
//        } else {
//            makeToast("nie wczytało obrazka");
//        }}


