package com.wallet.bo.wallets.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.DpUtils;
import com.wallet.bo.wallets.Utils.FileUtils;
import com.wallet.bo.wallets.Utils.ImageUtils;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Head;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.ui.weiget.CommonDialog;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.ObservableScrollView;
import com.wallet.bo.wallets.ui.weiget.TakePhotoPopWin;
import com.wallet.bo.wallets.ui.weiget.riv.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author:ggband
 * date:2017/7/28 14:26
 * email:ggband520@163.com
 * desc:我的信息
 */

public class MyInformationActivity extends BaseSwipeNoStatusActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.im_head)
    RoundedImageView imHead;
    @BindView(R.id.tv_nName)
    TextView tvNName;
    @BindView(R.id.tv_uName)
    TextView tvUName;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_units)
    TextView tvUnits;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.titleBarParent)
    LinearLayout titleBarParent;
    @BindView(R.id.minformarion_iv)
    ImageView minformarionIv;
    private Bitmap mBitmap;
    View popupView;
    TakePhotoPopWin takePhotoPopWin;

    private Uri photoUri;

    private int height = 20;


    private static final int TAKE_PHOTO = 0;
    private static final int PICK_PHOTO = 1;
    private static final int CROP_SMALL_PICTURE = 2;

    @Override
    protected int getContentView() {
        return R.layout.activity_minformation;
    }

    @Override
    protected void initView() {
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        StatusBarUtil.setTranslucentForCoordinatorLayout(activity, 0);


        {

            titleBarParent.setAlpha(1);
            titleBarParent.setBackgroundColor(Color.argb(0, 0x00, 0x00, 0x00));
            scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
                @Override
                public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

                    if (y <= height && y >= 0) {
                        float scale = (float) y / height;
                        float alpha = (255 * scale);
                        //layout全部透明
                        //    easeTitlebar.setAlpha(scale);
                        //只是layout背景透明(仿知乎滑动效果)
                        titleBarParent.setBackgroundColor(Color.argb((int) alpha, 0xE2, 0x56, 0x4a));
                    } else if (y > height) {
                        titleBarParent.getBackground().setAlpha(255);
                    }

                }

            });
        }

        if (hasSoftKeys(getWindowManager())) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, getNavigationBarHeight(activity));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void setUpView() {
        Log.e("head", MyApplication.getLogin().getHead_img());
        Picasso.with(context)
                .load(MyApplication.getLogin().getHead_img())
                .resize(DpUtils.dpToPx(this, 80), DpUtils.dpToPx(this, 80))
//                .placeholder(R.drawable.im_myhead)
                .error(R.drawable.im_myhead)
                .into(imHead);

        popupView = LayoutInflater.from(context).inflate(R.layout.camera_pop_menu, null);
        takePhotoPopWin = new TakePhotoPopWin(popupView, context, null);

    }

    @OnClick({R.id.tv_phone, R.id.tv_nName, R.id.im_head, R.id.tv_email})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.tv_phone:
//                new DisOpenDialog(context).show();
                //   startActivity(new Intent(this, UpdateTelActivity.class));

                final CommonDialog dialog = new CommonDialog(MyInformationActivity.this);
                dialog.setTitle("提示");
                dialog.setContent("此功能暂未开通");
                dialog.setCancelClickListener("取消", new CommonDialog.CancelClickListener() {
                    @Override
                    public void clickCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.setConfirmClickListener("确定", new CommonDialog.ConfirmClickListener() {
                    @Override
                    public void clickConfirm() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;

            case R.id.tv_nName:
                startActivity(new Intent(this, UpdateNickNameActivity.class));
                break;

            case R.id.tv_email:
                startActivity(new Intent(this, UpdateEmailActivity.class));
                break;

            case R.id.im_head:
                showPopwindow();

                break;
        }

    }


    private void showPopwindow() {

        takePhotoPopWin.setFocusable(true);
        takePhotoPopWin.setOutsideTouchable(false);// 设置允许在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
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
                        intentp.setType("image/jpg");
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


    /**
     * 自定义图片名，获取照片的file
     */
    private File createImgFile() {
        //创建文件
        String fileName = "temp_image.jpg";//确定文件名
        File tempFile = new File(new File(Environment.getExternalStorageDirectory()
                + Config.FILEPATH), fileName);
        FileUtils.createFileByDeleteOldFile(tempFile);
        return tempFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    cutImage(photoUri);
                    break;
                case PICK_PHOTO:
                    photoUri = data.getData();
                    cutImage(photoUri);
                    break;

                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }


    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置输出的地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", false);
        //不启用人脸识别
        intent.putExtra("noFaceDetection", true);

        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    private void updateImage() {


        String fileName = "temp_image.png";//确定文件名


        File file = new File(new File(Environment.getExternalStorageDirectory()
                + Config.FILEPATH), fileName);
        FileUtils.createFileByDeleteOldFile(file);
        ImageUtils.save(mBitmap, file, Bitmap.CompressFormat.PNG);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody uidbody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), MyApplication.getLogin().getUserid());
        final Map<String, RequestBody> params = new HashMap<String, RequestBody>();
        params.put("uid", uidbody);
        params.put("img\"; filename=\"" + file.getName() + "", requestBody);
        mainDialog.show();
        httpLoader.updateHeadImg(params, new ApiBaseResponseCallback<String>() {
            @Override
            public void onSuccessful(String path) {
                Login login = MyApplication.getLogin();
                login.setHead_img(path);
                MyApplication.setLogin(login);
                EventBus.getDefault().post(new Head());
                Picasso.with(context).load(path)
                        .resize(DpUtils.dpToPx(MyInformationActivity.this, 80), DpUtils.dpToPx(MyInformationActivity.this, 80))
                        .error(R.drawable.im_myhead)
                        .into(imHead);
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                mainDialog.dismiss();
            }
        });
    }

    /**
     * 保存裁剪之后的图片数据
     */

    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = decodeUriBitmap(photoUri);
//                    extras.getParcelable("data");
            updateImage();

        }

    }

    private Bitmap decodeUriBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    private void init() {

        Login login = MyApplication.getLogin();
        if (login == null)
            return;
        tvNName.setText(login.getCname());
        tvUName.setText(login.getUserName());
        tvCard.setText(login.getIdCard());
        tvUnits.setText(login.getUnits());
        tvPhone.setText(TextUtils.dosubtext424(login.getPhone()));
        tvEmail.setText(login.getMail());
        tvAddress.setText(login.getAddress());

        //选择会员logo
        switch (MyApplication.getLogin().getGrade()) {
            case "1":
                Picasso.with(context).load(R.mipmap.normal).into(minformarionIv);
                break;
            case "2":
                Picasso.with(context).load(R.mipmap.silver).into(minformarionIv);
                break;
            case "3":
                Picasso.with(context).load(R.mipmap.gold).into(minformarionIv);
                break;
            case "4":
                Picasso.with(context).load(R.mipmap.diamond).into(minformarionIv);
                break;
        }
    }


    private void setHeadView(String url) {
        if (url == null)
            return;
        try {
            Bitmap bitmap = Picasso.with(context).load(url).placeholder(R.drawable.im_myhead).error(R.drawable.im_myhead).get();
            RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(activity.getResources(), bitmap);
            bitmapDrawable.setCornerRadius(mBitmap.getWidth() / 2); //设置圆角半径为正方形边长的一半
            bitmapDrawable.setAntiAlias(true);
            imHead.setImageDrawable(bitmapDrawable);//显示图片
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean hasSoftKeys(WindowManager windowManager) {
        Display d = windowManager.getDefaultDisplay();


        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);


        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;


        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);


        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;


        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
