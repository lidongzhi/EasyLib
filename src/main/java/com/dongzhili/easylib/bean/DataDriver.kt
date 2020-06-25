package com.dongzhili.easylib.bean

/**
 *
 * create by Jianan at 2020-04-08
 **/
interface DataDriver<I, O> {
    val input: I
    val output: O
}