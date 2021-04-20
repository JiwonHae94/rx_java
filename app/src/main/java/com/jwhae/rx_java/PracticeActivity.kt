package com.jwhae.rx_java

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_practice.*


class PracticeActivity : AppCompatActivity() {
    private val subject = PublishSubject.create<String>()
    private val observable = Observable.just("test")
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        subject.subscribe{
            Log.d("Practice", it)
        }

        btn_add.setOnClickListener {
            subject.onNext("test${count}")
            count+= 1
        }

        btn_del.setOnClickListener {
            subject.onNext("test${count}")
            count-= 1
        }
    }
}