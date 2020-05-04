package com.saizad.mvvmexample;

import com.saizad.mvvm.model.UserInfo;
import com.saizad.mvvm.utils.Utils;

import org.junit.Test;

public class Example {
    @Test
    public void example() {
        System.out.println(Utils.printDataToForm(UserInfo.class));
    }
}
