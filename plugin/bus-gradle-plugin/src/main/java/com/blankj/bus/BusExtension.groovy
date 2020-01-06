package com.blankj.bus;

class BusExtension {

    String busUtilsClass = "com.blankj.utilcode.util.BusUtils";
    String onlyScanLibRegex = ""
    String jumpScanLibRegex = ""

    @Override
    String toString() {
        return "BusExtension { " +
                "busUtilsClass: " + busUtilsClass +
                (onlyScanLibRegex == "" ? "" : ", onlyScanLibRegex: " + onlyScanLibRegex) +
                (jumpScanLibRegex == "" ? "" : ", jumpScanLibRegex: " + jumpScanLibRegex) +
                " }";
    }
}
