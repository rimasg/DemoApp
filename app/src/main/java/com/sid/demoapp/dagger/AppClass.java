package com.sid.demoapp.dagger;

import javax.inject.Inject;

import dagger.Component;

/**
 * Created by Okis on 2017.01.22.
 */

public class AppClass {
    @Inject PrintClass printClass;
    @Inject OtherClass otherClass;
    @Inject DrawClass drawClass;

    public AppClass() {
        DaggerInjector.get().inject(this);
    }

    @Component
    public interface AppComponent {
        PrintClass printer();

        DrawClass drawer();
    }

    public void print() {
        printClass.print();
    }

    public void otherPrint() {
        printClass.print();
    }

    public void draw() {
        drawClass.draw();
    }

    public static void main(String[] args) {
        // Two different implementations: via internal and external @Component
        final AppClass appClass = new AppClass();
        appClass.print();
        appClass.otherPrint();
        appClass.draw();

        final AppComponent component = DaggerAppClass_AppComponent.builder().build();
        component.printer().print();
        component.drawer().draw();
    }
}
