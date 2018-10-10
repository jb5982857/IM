package com.nanshan.we12talk.common.http;


import com.nanshan.we12talk.common.exception.APIException;
import com.nanshan.we12talk.common.web.response.BaseResponseEntity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by jiangbo on 2018/9/21.
 * 转换网络请求的Observable对象
 */

public class ApiFilteredFactory {
    //请求超时时间，单位20s
    private static final int TIME_OUT = 20;
    private static SimpleTransformer transformer = new SimpleTransformer();

    @SuppressWarnings("unchecked")
    public static <T> Observable<T> compose(Observable<BaseResponseEntity<T>> observable) {
        return observable.compose(transformer);
    }

    private static class SimpleTransformer<T> implements Observable.Transformer<BaseResponseEntity<T>, T> {

        @Override
        public Observable<T> call(final Observable<BaseResponseEntity<T>> observable) {
            return observable.subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .timeout(TIME_OUT, TimeUnit.SECONDS)
                    .retry(3)
                    .flatMap(new Func1<BaseResponseEntity<T>, Observable<T>>() {
                        @Override
                        public Observable<T> call(BaseResponseEntity<T> tBaseResponseEntity) {
                            return flatObservable(tBaseResponseEntity);
                        }
                    });
        }

        private static <T> Observable<T> flatObservable(final BaseResponseEntity<T> entity) {
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(Subscriber<? super T> subscriber) {
                    if (entity.isSuccess()) {
                        subscriber.onNext(entity.getData());
                        subscriber.onCompleted();
                        return;
                    }
                    subscriber.onError(new APIException(entity.getCode(), entity.getDescribe(), entity.getMessage()));
                }
            });
        }
    }

}
