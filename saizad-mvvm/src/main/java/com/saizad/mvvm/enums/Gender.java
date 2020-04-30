package com.saizad.mvvm.enums;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({ Gender.MALE, Gender.FEMALE })
public @interface Gender {

  String MALE = "Male";
  String FEMALE = "Female";

  class ToList {
    public static List<String> getList() {
      ArrayList<String> genders = new ArrayList<>();
      genders.add(Gender.MALE);
      genders.add(Gender.FEMALE);
      return genders;
    }
  }
}
