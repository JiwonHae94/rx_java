package com.jwhae.rx_java

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_practice.*


class PracticeActivity : AppCompatActivity() {
    private val subject = PublishSubject.create<String>()
    private val observable = Observable.just("test")
    private val observableList = ObservableList.create<String>()
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        subject.subscribe{
            Log.d("Practice", it)
        }

        observableList.subscribe(object : Observer<String> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable?) {}

            override fun onNext(t: String?) {
                t?.let{
                    Log.d("practice", t)
                }
            }
            override fun onError(e: Throwable?) {}
        })

        btn_add.setOnClickListener {
            observableList.add("test${count}")
            subject.onNext("test${count}")
            count+= 1
        }

        btn_del.setOnClickListener {
            observableList.pop()
            subject.onNext("test${count}")
            count-= 1
        }

        btn_print.setOnClickListener {
            Log.d("practice", observableList.toString())
        }
    }
}