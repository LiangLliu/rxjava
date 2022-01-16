package com.lianglliu.rxjava.operatpr;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 创建操作符
 */
public class CreateOperatorDemo {
    public static void main(String[] args) {
        System.out.println("================");
        new CreateOperatorDemo().testFromMaybe();
        System.out.println("================");
    }

    /**
     * 观察者
     */
    private Observer<Object> observer = new Observer<>() {

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

        // 创建Observable
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {

                // 事件产生的地方，发送事件
                emitter.onNext("发送事件1");
                emitter.onNext("发送事件2");
                emitter.onNext("发送事件3");

                emitter.onError(new Throwable("手动触发异常"));
                emitter.onComplete();
            }
        }).subscribe(observer);

        /*
        onError回调和onComplete是互斥的
         */

        /*
        ================
        onSubscribe.....
        onNext.....
        onNext.....
        onNext.....
        onComplete.....
         */
    }

    private void test2() {

        // 创建Observable

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {

                // 事件产生的地方，发送事件
                emitter.onNext("发送事件1");
                emitter.onNext("发送事件2");
                emitter.onNext("发送事件3");

                emitter.onError(new Throwable("手动触发异常"));

                // 异步操作都放在这里
            }
            // 可以有多个 Consumer
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                System.out.println("accept: " + o);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                System.out.println("throwable: " + throwable);
            }
        });

        disposable.dispose();
    }

    /**
     * just中的参数就是事件，底层实现就是发射器发射事件
     * just操作符是基于 fromArray 操作符的
     */
    private void testJust() {
        Observable.just("1", 2.2, Arrays.asList("122", "234234"))
                .subscribe(observer);
    }

    private void testFromArray() {
        Observable.fromArray("1", 2.2, Arrays.asList("122", "234234"))
                .subscribe(observer);
    }

    private void testFromIterable() {
        Observable.fromIterable(Arrays.asList("213", "1231", "213214"))
                .subscribe(observer);
    }

    /**
     * 兼容java的Future接口
     */
    private void testFromFuture() {
        Observable.fromFuture(new Future<Object>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Object get() throws ExecutionException, InterruptedException {
                return "null";
            }

            @Override
            public Object get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return null;
            }
        }).subscribe(observer);
    }

    /**
     * 兼容java的Callable接口
     */
    private void testFromCallable() {
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "null";
            }
        }).subscribe(observer);
    }

    /**
     * fromAction
     */
    private void testFromAction() {
        Observable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                System.out.println("在testFromAction中执行");
            }
        }).subscribe(observer);
    }

    /**
     * fromAction
     */
    private void testFromMaybe() {
        Observable.fromMaybe(new Maybe<Object>() {
            @Override
            protected void subscribeActual(@NonNull MaybeObserver<? super Object> observer) {
                System.out.println("在 testFromMaybe 中执行" + observer);
            }
        }).subscribe(observer);
    }

}
