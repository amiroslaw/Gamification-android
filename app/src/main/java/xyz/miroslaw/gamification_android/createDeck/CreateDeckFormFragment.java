package xyz.miroslaw.gamification_android.createDeck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.miroslaw.gamification_android.R;


public class CreateDeckFormFragment extends Fragment implements CreateDeckContract.FormView {
    private static final int SELECT_FILE =  0, REQUEST_CAMERA = 1;
    @BindView(R.id.iv_formFragment_award)
    ImageView awardImg;

    private CreateDeckContract.Presenter presenter;

    public CreateDeckFormFragment() {
        // Required empty public constructor
    }
    public static CreateDeckFormFragment newInstance() {
        return new CreateDeckFormFragment();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
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
            presenter = new CreateDeckPresenter(this);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_deck_form, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @OnClick(R.id.btn_createDeck_setImg)
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    @OnClick(R.id.btn_createDeck_cameraImg)
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                awardImg.setImageBitmap(imageBitmap);
                sendImgPath(data.getData());

            }else if(requestCode==SELECT_FILE){
                Uri selectedImageUri = data.getData();
                sendImgPath(selectedImageUri);
                awardImg.setImageURI(selectedImageUri);
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
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
//    @Override
//    public void showPhoto(String imgPath) {
//        String path = Environment.getExternalStorageDirectory() + imgPath;
//        Log.d("pathshow", path);
//        File file = new File(path);
//        if(file.exists()){
//            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            awardImg.setImageBitmap(myBitmap);
//            makeToast(imgPath);
//        } else {
//            makeToast("nie wczytało obrazka");
//        }}


}
