package com.sopark.chapter2;

import com.sopark.util.Printer;
import io.reactivex.subjects.PublishSubject;

public class PublishSubjectExample {
    public void marbleDiagram(){
        Printer.print();
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        subject.onNext("5");
        subject.onComplete();
    }

    public static void main(String[] args) {
        PublishSubjectExample publishSubjectExample = new PublishSubjectExample();
        publishSubjectExample.marbleDiagram();
    }
}
