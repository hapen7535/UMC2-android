package com.example.example1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ThreadExm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_exm)

        val a = A()
        val b = B()

        a.start() //Thread 시작
        a.join() //join을 해주면 A Thread가 끝난 후에 B Thread갸 실행된다.
        b.start()

        //Thread가 실행될 때 정해진 순서를 갖고 실행되지 않는다.
        // Log를 보면 무작위의 숫자가 뜨고, A가 실행됐다가 B가 실행되기도 한다.
        //이것을 Context Switching이라고 한다.
        //사실 두 Thread가 왔다갔다 하면서 실행되지만, 빠른 속도로 실행되기 때문에 동시에 실행되는 것처럼 보인다.
        //Thread가 실행되는 순서를 우리가 임의로 정할 수는 없다.
        //다만 강제로 join이나 asyncTask를 통해 순서를 정해줄 수 있다.

    }

    class A : Thread() {
        override fun run() {
            super.run() //thread가 시작함과 동시에 실행되는 함수
            for ( i in 1 .. 1000){
                Log.d("test","first: $i")
            }
       }
    }

    class B : Thread() {
        override fun run() {
            super.run() //thread가 시작함과 동시에 실행되는 함수
            for ( i in 1000 downTo 1){
                Log.d("test","second: $i")
            }
        }
    }

}