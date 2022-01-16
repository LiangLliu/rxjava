package com.lianglliu.rxjava.operatpr;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ToolOperatorDemo {
    public static void main(String[] args) {
        System.out.println("================================");
        new ToolOperatorDemo().test1();
        System.out.println("================================");
    }

    private void test1() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                System.out.println("subscribe: " + Thread.currentThread());
                emitter.onNext("name");
                emitter.onNext("111");
                emitter.onNext("haha");
                emitter.onComplete();
            }
        })
//                决定了事件发射所处的线程
                .subscribeOn(Schedulers.newThread()) // 方法子线程去操作
//                来决定下游事件被处理时所处的线程
                .observeOn(Schedulers.computation())
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        System.out.println("map apply: " + Thread.currentThread());
                        return o + "aaa";
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println(d + " onSubscribe: " + Thread.currentThread());
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        System.out.println(o + " onNext: " + Thread.currentThread());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println(e + " onError: " + Thread.currentThread());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete: " + Thread.currentThread());
                    }
                });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
