package com.jwhae.rx_java

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Flowable.fromArray
import io.reactivex.rxjava3.core.Observable.fromArray
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnSuccess
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import java.util.function.Consumer

class MainActivity : AppCompatActivity() {
    private val source = Observable.just("Hi", "this", "is","RxJava 101")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // basic()
        // introduction()

        btn_start.setOnClickListener {
            otherObservable()
        }
    }

    private fun basic(){
        val basicRx = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>?) {
                emitter?.let {
                    it.onNext("Hi")
                    it.onNext("this")
                    it.onNext("is")
                    it.onNext("RxJava 101")
                }
            }})

        basicRx.subscribe(object : Observer<String>{
            override fun onSubscribe(d: Disposable?) {
                System.out.print("onSubsribe")
            }

            override fun onComplete() {
                System.out.print("onComplete")
            }


            override fun onNext(t: String?) {
                t?.let{
                    Log.d("Basic", it)
                }
            }

            override fun onError(e: Throwable?) {
                e?.let{
                    Log.e("Basic", it.stackTraceToString())
                }
            }
        })
    }


    /**
     * introductory methods in test
     */
    private fun introduction(){
        btn_start.setOnClickListener {
            source.subscribe{
                Log.d("RxJava", it)
            }
        }
    }

    private fun convert(){
        // from array
        val array = arrayOf("Hi", "this", "is", "RxJava 101")
        val arrayRx = Observable.fromArray(array)

        arrayRx.subscribe {
            Log.d("convert", it.joinToString(" "))
        }


        // from future
        val future = Executors.newSingleThreadExecutor()
            .submit<String> {
                Thread.sleep(5000)
                "This is the future"
            }
        val futureRx = Observable.fromFuture(future)

        futureRx.subscribe {
            Log.d("convert future", it)
        }
    }

    private fun otherObservable(){
        val single = Single.create(object : SingleOnSubscribe<String>{
            override fun subscribe(emitter: SingleEmitter<String>?) {
                emitter?.let{
                    it.onSuccess("other | single")
                }
            }
        })

        single.subscribe({ str ->
            Log.d(str, "success")
        })
    }

}