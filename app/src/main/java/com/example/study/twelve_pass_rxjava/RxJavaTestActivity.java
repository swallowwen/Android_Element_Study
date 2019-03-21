package com.example.study.twelve_pass_rxjava;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.study.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class RxJavaTestActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Observer observer = new Observer<String>() {
//            Disposable disposable;
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("tag", "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("tag", "onComplete");
//            }
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("tag", "onSubscribe");
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e("tag", "onNext=" + s);
//                if ("!".equals(s)) {
//                    disposable.dispose();
//                }
//            }
//        };
//
//        Consumer consumer = new Consumer<String>() {
//
//            @Override
//            public void accept(String s) throws Exception {
//
//            }
//        };
//
//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("Hello");
//                emitter.onNext("World");
//                emitter.onNext("!");
//                emitter.onComplete();
//            }
//        });
//
//        //将依次调用onNext("Hello");onNext("World");onComplete();
//        Observable observable1 = Observable.just("Hello", "World");
//
//        //同just()
//        Observable observable2 = Observable.fromArray(new String[]{"Hello", "World"});
//
//        observable.subscribe(observer);
//        //或
//        observable.subscribe(consumer);
//
//        Action1<String> onNextAction = new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//            }
//        };
//
//        Action1<Throwable> throwableAction = new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//
//            }
//        };
//
//        Action0 onCompleteAction = new Action0() {
//            @Override
//            public void call() {
//
//            }
//        };
//        //使用 onNextAction 来定义 onNext()
//        observable.subscribe(onNextAction);
//        //使用 onNextAction 和 throwableAction 来定义 onNext() 和 onError()
//        observable.subscribe(onNextAction, throwableAction);
//        //使用 onNextAction、 throwableAction 和 onCompleteAction 来定义 onNext()、 onError() 和 onCompleted()
//        observable.subscribe(onNextAction, throwableAction, onCompleteAction);

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello");
                emitter.onNext("world");
                emitter.onNext("!");
                emitter.onComplete();
                Log.e("tag", "发送线程为：" + Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("tag", s);
                        Log.e("tag", "接收线程为：" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Observable.just(R.mipmap.ic_launcher).map(new Function<Integer, Bitmap>() {
            @Override
            public Bitmap apply(Integer integer) throws Exception {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), integer);
                return bitmap;
            }
        }).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {

            }
        });

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.e("tag","onSubscribe");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("tag","onNext:"+integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("tag","onError:"+t.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("tag","onComplete");
                    }
                });
    }


    private void backpressure() {
        Flowable.range(0,300)
                .onBackpressureDrop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void filter() {
        Observable.just("a", "b", "c", "d").filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return "d".equals(s);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("accept:"+s);
            }
        });
    }

    private void flatMap(){
        Observable.just(new Group(1))
                .flatMap(new Function<Group, ObservableSource<Student>>() {
                    @Override
                    public ObservableSource<Student> apply(Group group) throws Exception {
                        return Observable.fromIterable(group.getStudents());
                    }
                }).subscribe(new Observer<Student>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Student student) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
