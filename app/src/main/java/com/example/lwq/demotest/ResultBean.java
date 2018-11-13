package com.example.lwq.demotest;

import com.google.gson.annotations.SerializedName;

/**
 * @author lwq
 * @date 2018-11-07 13:25
 * introduction:
 */
public class ResultBean {

    /**
     * score : a
     * span : b
     */

    @SerializedName("score")
    public String score;
    @SerializedName("span")
    public String span;
}
