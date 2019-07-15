package com.blankj.api

class ApiExtension {

    boolean abortOnError = true
    String apiUtilsClass = "com.blankj.utilcode.util.ApiUtils";

    @Override
    String toString() {
        return "BusExtension { " +
                "abortOnError: " + abortOnError +
                ", apiUtilsClass: " + apiUtilsClass +
                " }";
    }

}
