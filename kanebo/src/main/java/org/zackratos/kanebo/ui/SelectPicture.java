package org.zackratos.kanebo.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions3.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.tools.JsonData;
import org.zackratos.kanebo.tools.PhotoTool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class SelectPicture extends BaseActivity {

    @BindView(R.id.paizhao)
    Button paizhao;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;
    private static final int TAKE_PICTURE = 3021, BARCODE = 3000;
    private static final int REQ_CODE = 1000;
    private JsonData m_JsonObject;
    private FaceDetector mFaceDetector;
    private Uri mImageUri;
    private static final int REQUEST_TAKE_PHOTO_CODE = 1001;
    private AlertDialog dialog;
    private View imgEntryView;
    // ps???RxPermissions????????????rxJava??????
    final RxPermissions rxPermissions = new RxPermissions(SelectPicture.this);
    // ?????????
    private static final String[] permissionsGroup = new String[]{
            Manifest.permission.CAMERA
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected int initView() {
        return R.layout.atc_selectpicture;
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= 23) {
            initPermission();
        }
        initTitle();
//        mFaceDetector = FaceDetector.createDetector(this, null);
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TakePhoto(TAKE_PICTURE);// ??????
                // ???????????????????????????
//                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//?????????????????????Intent
//                if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//                    startActivityForResult(takePhotoIntent, REQ_CODE);//????????????
//                }

                // ????????????
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//???????????????Intent
                if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    File imageFile = createImageFile();//?????????????????????????????????
                    if (imageFile != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            /*7.0???????????????FileProvider???File?????????Uri*/
                            mImageUri = FileProvider.getUriForFile(SelectPicture.this, "org.zackratos.kanebo.fileprovider", imageFile);
                        } else {
                            /*7.0?????????????????????Uri???fromFile?????????File?????????Uri*/
                            mImageUri = Uri.fromFile(imageFile);
                        }
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//????????????????????????Uri???????????????
                        startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO_CODE);//????????????
                    }
                }
            }
        });

        // ??????????????????
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpoto();
            }
        });
    }

    private void initTitle() {
        titleContent.setText("??????");
        titleRight.setVisibility(View.VISIBLE);
    }

    // ??????????????????????????????
    public void showpoto() {
        LayoutInflater inflater = LayoutInflater.from(SelectPicture.this);
        imgEntryView = inflater.inflate(R.layout.photo_show, null);
        dialog = new AlertDialog.Builder(SelectPicture.this).create();
        ImageView img = imgEntryView.findViewById(R.id.large_image);
        // Glide??????????????????
        Glide.with(SelectPicture.this)
                .load(mImageUri)
                .into(img);
        dialog.setView(imgEntryView); // ?????????dialog
        dialog.getWindow()
                .setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // ??????????????????dialog
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                dialog.cancel();
            }
        });
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     *
     * @return ?????????????????????
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    // ??????
    private void TakePhoto(int type) {
        try {
            startActivityForResult(PhotoTool.getCamraIntent(this), type);
        } catch (Exception e) {
            e.printStackTrace();
//            ActionBack(ErrResult(m_JsonObject.getJsonData()).toString());
        }
    }

    private JSONObject ResultObject(JSONObject input, JSONObject data)
            throws Exception {
        JSONObject result = new JSONObject();
        result.put("type", input.getString("type"));
        // result.put("code", input.get("code"));
        result.put("data", data);
        return result;
    }

//    private boolean isHead(Bitmap bm) {
//        if (null != bm) {
//            if (mFaceDetector == null) {
//                /**
//                 * ???????????????????????????????????????????????????????????????SDK ?????????????????????????????????????????????SDK
//                 */
////                CToast.showMsg(getContext(), "???SDK???????????????????????????");
//                return false;
//            }
//
//            // ????????????????????????
////            String jcresult = mFaceDetector.detectARGB(bm);
//            // Log.d(TAG, "result:"+jcresult);
//            // ??????????????????
////            mFaces = ParseResult.parseResult(jcresult);
//            if (null != mFaces && mFaces.length > 0) {
//                // drawFaceRects(mFaces);
//                return true;
//            } else {
//                // ?????????????????????????????????????????????
//                int errorCode = 0;
//                JSONObject object;
//                try {
//                    object = new JSONObject(jcresult);
//                    errorCode = object.getInt("ret");
//                } catch (JSONException e) {
//                }
//                // errorCode!=0???????????????????????????????????????????????????
//                if (JPushInterface.ErrorCode.SUCCESS == errorCode) {
////                    CToast.showMsg(getContext(), "?????????????????????");
//                    // showTip("?????????????????????");
//                    return false;
//                } else {
////                    CToast.showMsg(getContext(), "?????????????????????????????????" + errorCode);
//                    // showTip("?????????????????????????????????"+errorCode);
//                    return false;
//                }
//            }
//        }
//        return false;
//    }

    // ?????????????????????js??????
    private void TakePhotoBack(Bitmap bm, String success) {
        try {
            JSONObject data = new JSONObject();
            JSONObject result = ResultObject(m_JsonObject.getJsonData(), data);
            result.put("success", success);
            if (success.equals("1")) {
                JSONObject inputData = m_JsonObject.getJsonObject("data");
                int size = 40;
                // if (inputData != null) {
                // size = inputData.getInt("size");
                // }

                int isValide = 0;
                try {
                    if (inputData != null) {
                        isValide = inputData.getInt("isValide");
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                if (isValide == 1) {
                    // ????????????
//                    if (!isHead(bm)) {
//                        result.put("success", 0);
//                    } else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    String base64 = Base64.encodeToString(
                            baos.toByteArray(), Base64.NO_WRAP);
                    data.put("image", base64);
//                    }
                } else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    String base64 = Base64.encodeToString(baos.toByteArray(),
                            Base64.NO_WRAP);
                    data.put("image", base64);
                }
            }
//            data.put("time", DateTool.CurrDateTime());
            data.put("msg", "??????");
//            ActionBack(result.toString());// ??????????????????
        } catch (Exception e) {
            // TODO: handle exception
//            ActionBack(ErrResult(m_JsonObject.getJsonData()).toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("Img", requestCode + "-----++++++++++" + resultCode + "============" + data);
//        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
//            /*????????????????????????????????????intent??????Bundle?????????
//             * ??????Bundle????????????data????????????Intent?????????
//             * Bundle?????????data?????????Bitmap??????*/
//            Bundle extras = data.getExtras();
//            Bitmap bitmap = (Bitmap) extras.get("data");
//            img.setImageBitmap(bitmap);
//        }

        // ????????????
        if (requestCode == REQUEST_TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
//            try {
            /*????????????????????????Uri???BitmapFactory???decodeStream????????????Bitmap*/
            Log.i("Img", mImageUri + "-----++++++++++");
//                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
//                img.setImageBitmap(bitmap);//?????????ImageView???
            Glide.with(SelectPicture.this)// Glide??????????????????OK
                    .load(mImageUri)
                    .into(img);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
        }

//        try {
//            if (resultCode == RESULT_OK) {
//                if (requestCode == TAKE_PICTURE) {// ?????????????????????
//                    // CToast.showMsg(getContext(), "????????????");
//                    final Bitmap bm = PhotoTool.lessenUriImage(this);// ?????????????????????????????????
//                    // CToast.showMsg(getContext(), "??????????????????");
////                    TakePhotoBack(bm, "1");
//                } else if (requestCode == 1111) {
//                    String imagePath = null;
//                    Uri uri = data.getData();
//                    if (DocumentsContract.isDocumentUri(this, uri)) {
//                        // ?????????document?????????Uri????????????document id??????
//                        String docId = DocumentsContract.getDocumentId(uri);
//                        if ("com.android.providers.media.documents".equals(uri
//                                .getAuthority())) {
//                            String id = docId.split(":")[1]; // ????????????????????????id
//                            String selection = MediaStore.Images.Media._ID
//                                    + "=" + id;
//                            imagePath = getImagePath(
//                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                    selection);
//                        } else if ("com.android.providers.downloads.documents"
//                                .equals(uri.getAuthority())) {
//                            Uri contentUri = ContentUris
//                                    .withAppendedId(
//                                            Uri.parse("content://downloads/public_downloads"),
//                                            Long.valueOf(docId));
//                            imagePath = getImagePath(contentUri, null);
//                        }
//                    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//                        // ????????????document?????????Uri??????????????????????????????
//                        imagePath = getImagePath(uri, null);
//                    }
//                    // String path = getPath(data.getData());
//                    final Bitmap bm = PhotoTool.getUriImage(this,
//                            imagePath);
////                    TakePhotoBack(bm, "1");
//                } else if (requestCode == BARCODE) {
////                    BarCodeBack(data.getExtras().getString("Code"), "1");
//                }
//            } else if (resultCode == RESULT_CANCELED) {
//                {
//                    if (requestCode == TAKE_PICTURE) {
//                        // final Bitmap bm = PhotoTool
//                        // .lessenUriImage(getContext());
////                        TakePhotoBack(null, "0");
//                    } else if (requestCode == BARCODE) {
////                        BarCodeBack("", "0");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // CToast.showMsg(getContext(), "????????????:" + e.getLocalizedMessage());
////            ActionBack(ErrResult(m_JsonObject.getJsonData()).toString());
//        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // ??????uri???selection??????????????????????????????
        Cursor cursor = getContentResolver().query(uri, null, selection, null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    @OnClick({R.id.title_back, R.id.title_right})
    public void focusClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                showToast("???????????????");
                break;
            default:
                break;
        }
    }

    // ????????????
    private void initPermission() {
//        RxPermissions rxPermissions=new RxPermissions(this);
        rxPermissions.request(permissionsGroup)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(SelectPicture.this, "???????????????", Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            //?????????????????????????????????????????????????????????????????????????????????????????????????????????
                            Toast.makeText(SelectPicture.this, "?????????????????????", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
