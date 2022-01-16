package com.lianglliu.rxjava.operatpr;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 组合操作符
 */
public class MergeOperatorDemo {
    public static void main(String[] args) {
        System.out.println("================================");
        new MergeOperatorDemo().test3();
        System.out.println("================================");
    }

    /**
     * 观察者
     */
    private Observer<Object> observer = new Observer<Object>() {

        // 建立联系就会被调用
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            System.out.println("onSubscribe....." + d);
        }

        @Override
        public void onNext(@NonNull Object o) {
            System.out.println("onNext....." + o);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            System.out.println("onError....." + e);
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete.....");
        }
    };

    /**
     * concat 合并被观察者
     * 串行
     */
    private void test1() {
        Observable.concat(Observable.just("111"), Observable.just("111"))
                .subscribe(observer);
        /*
        ================================
        onSubscribe.....0
        onNext.....111
        onNext.....111
        onComplete.....
        ================================
         */
    }

    /**
     * merge 合并被观察者
     * 并行
     */
    private void test2() {
        Observable.merge(
                Observable.just("111"),
                Observable.just("222"),
                Observable.just("333")
        ).subscribe(observer);
        /*
        ================================
        onSubscribe.....0
        onNext.....111
        onNext.....111
        onComplete.....
        ================================
         */
    }

    /**
     * zip 压缩两个观察者
     * 并行
     */
    private void test3() {
        Observable.zip(
                Observable.just("111"),
                Observable.just("222"),
                (s, s2) -> s + s2
        ).subscribe(observer);
        /*
        ================================
        onSubscribe.....0
        onNext.....111222
        onComplete.....
        ================================
         */
    }
}
