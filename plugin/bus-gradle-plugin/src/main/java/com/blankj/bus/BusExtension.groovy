package com.blankj.bus;

class BusExtension {

    boolean abortOnError = true;
    String busUtilsClass = "com.blankj.utilcode.util.BusUtils";

    @Override
    String toString() {
        return "BusExtension { " +
                "abortOnError: " + abortOnError +
                ", busUtilsClass: " + busUtilsClass +
                " }";
    }
}
