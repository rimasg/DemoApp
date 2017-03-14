package com.sid.demoapp.dagger;

import javax.inject.Inject;

/**
 * Created by Okis on 2017.03.11.
 */

public class OtherClass {
    OtherPrintClass otherPrintClass;

    @Inject
    public OtherClass(OtherPrintClass otherPrintClass) {
        this.otherPrintClass = otherPrintClass;
    }

    public void print() {
        otherPrintClass.print();
    }
}
