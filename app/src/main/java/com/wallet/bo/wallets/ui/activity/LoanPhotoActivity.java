package com.wallet.bo.wallets.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.FileUtils;
import com.wallet.bo.wallets.Utils.ImageUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.http.ApiCommonTypeCallback;
import com.wallet.bo.wallets.http.HttpParameterBuilder;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.TakePhotoPopWin;
import com.wallet.bo.wallets.ui.weiget.cpb.CircularProgressButton;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


//http://blog.csdn.net/u014752325/article/details/53149973?_t_t_t=0.5613087418023497

public class LoanPhotoActivity extends BaseActivity {


    @BindView(R.id.im_photo_one)
    ImageView imPhotoOne;
    @BindView(R.id.bt_photo_one)
    ImageView btPhotoOne;
    @BindView(R.id.im_photo_two)
    ImageView imPhotoTwo;
    @BindView(R.id.bt_photo_two)
    ImageView btPhotoTwo;
    @BindView(R.id.im_photo_three)
    ImageView imPhotoThree;
    @BindView(R.id.bt_photo_three)
    ImageView btPhotoThree;
    @BindView(R.id.upload)
    CircularProgressButton upload;
    private Uri photoUri;
    private File photoOne;
    private File photoTwo;
    private File photoThree;
    private int isWho;


    private static final int TAKE_PHOTO = 0;
    private static final int PICK_PHOTO = 1;


    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    TakePhotoPopWin takePhotoPopWin;
    View popupView;

    @Override
    protected int getContentView() {
        return R.layout.activity_loan_photo;
    }

    @Override
    public void initView() {
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();

            }
        });

    }

    @Override
    protected void setUpView() {

        popupView = LayoutInflater.from(context).inflate(R.layout.camera_pop_menu, null);
        takePhotoPopWin = new TakePhotoPopWin(popupView, context, null);


    }


    @OnClick({R.id.bt_goto, R.id.bt_photo_one, R.id.bt_photo_three, R.id.bt_photo_two})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.bt_goto:
                if (photoOne == null || photoTwo == null || photoThree == null) {
                    toastShow("三张照片必须上传");
                    return;
                }
                mainDialog.show();

                RequestBody requestBodyOne = RequestBody.create(MediaType.parse("image/png"), photoOne);
                RequestBody requestBodyTwo = RequestBody.create(MediaType.parse("image/png"), photoTwo);
                RequestBody requestBodyThree = RequestBody.create(MediaType.parse("image/png"), photoThree);

                Map<String, RequestBody> params = new HashMap<>();
                RequestBody uidBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), MyApplication.getLogin().getUserid());
                params.put("uid",uidBody);
                params.put("img\"; filename=\"" + photoOne.getName(), requestBodyOne);
                params.put("img\"; filename=\"" + photoTwo.getName(), requestBodyTwo);
                params.put("img\"; filename=\"" + photoThree.getName(), requestBodyThree);

//"http://rx.ggband.cn/pt/user/upload.do?json"
                apiStores.updateImage("http://dai.moxtx.com/index.php/Home/UserInfo/UplodeCard", params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new ApiCommonTypeCallback() {

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            Log.i("ggband", "上传图片状态：" + responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
                        Log.i("ggband", "上传图片失败：" + msg);

                    }

                    @Override
                    public void onFinish() {
                        mainDialog.dismiss();
                    }
                });

//                mainDialog.show();
//
//                RequestBody requestBodyOne = RequestBody.create(MediaType.parse("image/png"), photoOne);
//                RequestBody requestBodyTwo = RequestBody.create(MediaType.parse("image/png"), photoTwo);
//                RequestBody requestBodyThree = RequestBody.create(MediaType.parse("image/png"), photoThree);
//
//                Map<String, RequestBody> params = new HashMap<String, RequestBody>();
//                RequestBody uidBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), MyApplication.getLogin().getUserid());
//                params.put("uid", uidBody);
//                params.put("img\"; filename=\"" + photoOne.getName(), requestBodyOne);
//                params.put("img\"; filename=\"" + photoTwo.getName(), requestBodyTwo);
//                params.put("img\"; filename=\"" + photoThree.getName(), requestBodyThree);
//
////"http://rx.ggband.cn/pt/user/upload.do?json"
//                httpLoader.updateCardImg(params, new ApiBaseResponseCallback<Object>() {
//                    @Override
//                    public void onSuccessful(Object s) {
//                        Log.i(Config.LOGTAG, s.toString());
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        toastShow(msg);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        mainDialog.dismiss();
//                    }
//                });

                break;

            case R.id.bt_photo_one:
                showPopwindow(1);
                break;

            case R.id.bt_photo_two:
                showPopwindow(2);
                break;

            case R.id.bt_photo_three:
                showPopwindow(3);
                break;

        }


    }


    /**
     * 自定义图片名，获取照片的file
     */
    private File createImgFile() {
        //创建文件
        String fileName = "photo_image.png";//确定文件名
        File tempFile = new File(new File(Environment.getExternalStorageDirectory()
                + Config.FILEPATH), fileName);
        FileUtils.createFileByDeleteOldFile(tempFile);
        return tempFile;
    }


    private void setImageBitmap() {
        Bitmap bitmap = ImageUtils.decodeUri(context, photoUri, 320, 480);
        Drawable drawable = ImageUtils.bitmap2Drawable(getResources(), bitmap);

        if (isWho == 1) {
            imPhotoOne.setBackgroundDrawable(drawable);
            String fileName = "photoOne" + ".png";//确定文件名
            photoOne = new File(new File(Environment.getExternalStorageDirectory()
                    + Config.FILEPATH), fileName);
            FileUtils.createFileByDeleteOldFile(photoOne);
            ImageUtils.save(bitmap, photoOne, Bitmap.CompressFormat.PNG);
        } else if (isWho == 2) {
            imPhotoTwo.setBackgroundDrawable(drawable);
            String fileName = "photoTwo" + ".png";//确定文件名
            photoTwo = new File(new File(Environment.getExternalStorageDirectory()
                    + Config.FILEPATH), fileName);
            FileUtils.createFileByDeleteOldFile(photoTwo);
            ImageUtils.save(bitmap, photoTwo, Bitmap.CompressFormat.PNG);
        } else {
            imPhotoThree.setBackgroundDrawable(drawable);
            String fileName = "photoThree" + ".png";//确定文件名
            photoThree = new File(new File(Environment.getExternalStorageDirectory()
                    + Config.FILEPATH), fileName);
            FileUtils.createFileByDeleteOldFile(photoThree);
            ImageUtils.save(bitmap, photoThree, Bitmap.CompressFormat.PNG);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    setImageBitmap();
                    break;
                case PICK_PHOTO:
                    //data中自带有返回的uri
                    photoUri = data.getData();
                    //获取照片路径
                    String[] filePathColumn = {MediaStore.Audio.Media.DATA};
                    Cursor cursor = context.getContentResolver().query(photoUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    cursor.close();
                    //有了照片路径，之后就是压缩图片，和之前没有什么区别
                    setImageBitmap();
                    break;
            }
        }
    }


    private void showPopwindow(int who) {
        isWho = who;

        takePhotoPopWin.setFocusable(true);
        takePhotoPopWin.setOutsideTouchable(false);// 设置允许在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                if (AntiShake.check(v.getId())) {    //判断是否多次点击
                    return;
                }
                switch (v.getId()) {
                    case R.id.btn_camera_pop_camera:

                        //只是加了一个uri作为地址传入
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photoFile = createImgFile();
                        photoUri = Uri.fromFile(photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, TAKE_PHOTO);
                        break;
                    case R.id.btn_camera_pop_album:

                        Intent intentp = new Intent(Intent.ACTION_GET_CONTENT);
                        intentp.setType("image/png");
                        startActivityForResult(intentp, PICK_PHOTO);
                        break;

                    case R.id.btn_camera_pop_cancel:
                        break;
                }

                takePhotoPopWin.dismiss();
            }
        };

        popupView.findViewById(R.id.btn_camera_pop_camera).setOnClickListener(listener);
        popupView.findViewById(R.id.btn_camera_pop_album).setOnClickListener(listener);
        popupView.findViewById(R.id.btn_camera_pop_cancel).setOnClickListener(listener);
        takePhotoPopWin.showAtLocation(popupView, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


}
