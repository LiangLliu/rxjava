package com.lianglliu.rxjava;

import com.lianglliu.rxjava.impl.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=================================");
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(Emitter<Object> emitter) {
                System.out.println("subscribe.....");
                emitter.onNext("11111");
                emitter.onNext("22222");
                emitter.onNext("22222");
                emitter.onNext("33333");
                emitter.onCompleted();
            }
        }).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                return o + "-ooo";
            }
        }).flatMap(new Function<Object, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(Object o) {
                return Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(Emitter<Object> emitter) {
                        emitter.onNext("处理过的：" + o);
                    }
                });
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe() {
                System.out.println("onSubscribe.....");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext....." + o);
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted.....");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError.....");
            }
        });

        System.out.println("=================================");
    }
}
