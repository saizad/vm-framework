package com.saizad.mvvm.model;

public class TypeModel<M> {

    private final M model;

    public TypeModel(M model) {
        this.model = model;
    }

    public M getModel() {
        return model;
    }

}
