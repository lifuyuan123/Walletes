package com.wallet.bo.wallets.http;




import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;


/**
 *
 */
public abstract class ApiCommonCallback extends ApiCallback<ResponseBody> {

    public abstract void onSuccess(ResponseBody responseBody);

    public abstract void onFailure(String msg);

    public abstract void onFinish();

    private Disposable disposable;

    private int type;


    public ApiCommonCallback setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();

            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg);
        } else {
            onFailure(e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        onSuccess(responseBody);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.setDisposable(d);
    }

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    public Disposable getDisposable() {
        return this.disposable;
    }
}
