package com.nanshan.we12talk.module;

/**
 * Created by jiangbo on 2018/9/27.
 * 为了兼容activity和fragment都能对应到一个presenter，所以设定了这个接口
 */

public interface IView<T extends BasePresenter> {
    T onCreatePresenter();
}
