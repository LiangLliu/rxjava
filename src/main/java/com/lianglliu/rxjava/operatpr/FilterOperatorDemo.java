package com.lianglliu.rxjava.operatpr;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;

public class FilterOperatorDemo {
    public static void main(String[] args) {
        System.out.println("================================");
        new FilterOperatorDemo().test1();
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

    private void test1() {
        Observable
                .range(1, 10)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Throwable {
                        return integer > 5;
                    }
                })
                .subscribe(observer);

        /*
        ================================
        onSubscribe.....io.reactivex.rxjava3.internal.operators.observable.ObservableFilter$FilterObserver@a67c67e
        onNext.....6
        onNext.....7
        onNext.....8
        onNext.....9
        onNext.....10
        onComplete.....
        ================================
         */
    }
}
