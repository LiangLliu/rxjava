package com.lianglliu.rxjava.operatpr;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

/**
 * 传唤操作符
 * map
 * flatmap
 * concatMap
 * buffer
 * compose
 */
public class TransOperatorDemo {
    public static void main(String[] args) {
        System.out.println("================================");
        new TransOperatorDemo().test4();
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
     * map, 对发射的事件做出处理，并产生新事件
     */
    private void test1() {
        Observable.just("aaa")
                .map(new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Throwable {
                        return "bbb";
                    }
                })
                .subscribe(observer);
        /*
        ================================
        onSubscribe.....io.reactivex.rxjava3.internal.operators.observable.ObservableMap$MapObserver@21213b92
        onNext.....bbb
        onComplete.....
        ================================
         */
    }

    /**
     * flatmap, 产生一个新的 ObservableSource 被观察者
     */
    private void test2() {
        Observable.just("注册")
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Throwable {
                        System.out.println(s + " 成功");
                        return Observable.just("请求登录");
                    }
                })
                .subscribe(observer);
        /*
            ================================
            注册 成功
            onSubscribe.....0
            onNext.....请求登录
            onComplete.....
            ================================
         */
    }


    /**
     * concatMap, 产生一个新的 ObservableSource 被观察者
     * 保证顺序
     */
    private void test3() {
        Observable.just("注册", "111", "2222")
                .concatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Throwable {
                        return Observable.just(s + " new");
                    }
                })
                .subscribe(observer);
        /*
            onSubscribe.....io.reactivex.rxjava3.observers.SerializedObserver@13a5fe33
            onNext.....注册 new
            onNext.....111 new
            onNext.....2222 new
            onComplete.....
            ================================
         */
    }

    /**
     * concatMap, 产生一个新的 ObservableSource 被观察者
     * 保证顺序
     */
    private void test4() {
        Observable.just("注册", "111", "2222", "注册", "111", "注册", "111", "2222", "注册", "111")
                .buffer(3)
                .subscribe(observer);
        /*
            ================================
            onSubscribe.....io.reactivex.rxjava3.internal.operators.observable.ObservableBuffer$BufferExactObserver@5383967b
            onNext.....[注册, 111, 2222]
            onNext.....[注册, 111, 注册]
            onNext.....[111, 2222, 注册]
            onNext.....[111]
            onComplete.....
            ================================
         */
    }

}
