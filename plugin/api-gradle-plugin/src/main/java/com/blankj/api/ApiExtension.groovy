package com.blankj.api

class ApiExtension {

    String apiUtilsClass = "com.blankj.utilcode.util.ApiUtils";
    String onlyScanLibRegex = ""
    String jumpScanLibRegex = ""

    @Override
    String toString() {
        return "ApiExtension { " +
                "apiUtilsClass: " + apiUtilsClass +
                (onlyScanLibRegex == "" ? "" : ", onlyScanLibRegex: " + onlyScanLibRegex) +
                (jumpScanLibRegex == "" ? "" : ", jumpScanLibRegex: " + jumpScanLibRegex) +
                " }";
    }
}
