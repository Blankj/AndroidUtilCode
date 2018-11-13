//package com.blankj.utilcode.util
//
//import java.lang.annotation.ElementType
//import java.lang.annotation.RetentionPolicy
//
//
//class BusUtils private constructor() {
//
//    init {
//        throw UnsupportedOperationException("u can't instantiate me...")
//    }
//
//    companion object {
//        fun <T> post(name: String?, vararg objects: Any): T {
//            return if (name == null || name.length == 0) null else null
//        }
//
//    }
//
//    @Target(ElementType.METHOD)
//    @Retention(RetentionPolicy.CLASS)
//    annotation class Subscribe(val name: String = "")
//}