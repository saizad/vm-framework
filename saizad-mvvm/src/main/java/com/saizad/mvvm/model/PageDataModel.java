package com.saizad.mvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PageDataModel<M, P> extends DataModel<List<M>> {

  @SerializedName("page") public P page;
  @SerializedName("next_page") public P next_page;
  @SerializedName("previous_page") public P previous_page;
  @SerializedName("count") public int count;
  @SerializedName("max_pages") public int maxPages;
  @SerializedName("total_count") public int total;

}