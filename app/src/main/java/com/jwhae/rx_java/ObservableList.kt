package com.jwhae.rx_java

import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.StringBuilder

class ObservableList<T> private constructor() : ObservableSource<T> {
    private val list = ArrayList<T>()
    private val observer = PublishSubject.create<T>()

    internal fun add(var1 : T){
        synchronized(this) {
            list.add(var1)
        }
        observer.onNext(var1)
    }

    internal fun addAll(var1 : ArrayList<T>){
        synchronized(this){
            list.addAll(var1)
        }

        var1.forEach {
            observer.onNext(it)
        }
    }

    override fun subscribe(observer: Observer<in T>?){
        this.observer.subscribe(observer)
    }

    internal fun subscribe(consumer : Consumer<in T>) : Disposable{
        return this.observer.subscribe(consumer)
    }

    internal fun subscribe(callback : (T) -> Unit) : Disposable{
        return this.observer.subscribe {
            callback(it)
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder("ObservableList")
        list.forEachIndexed { index, t ->
            stringBuilder.append("$index : ", t.toString())
        }
        return stringBuilder.toString()
    }

    companion object{
        fun <T> create() : ObservableList<T>{
            return ObservableList()
        }
    }
}