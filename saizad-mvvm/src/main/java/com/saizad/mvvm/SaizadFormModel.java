package com.saizad.mvvm;

import com.sa.easyandroidfrom.fields.BaseField;
import com.sa.easyandroidfrom.form.FormModel;
import com.saizad.mvvm.model.ErrorModel;
import com.saizad.mvvm.utils.Utils;

import java.util.List;


public abstract class SaizadFormModel<T> extends FormModel<T> {
    protected transient final List<BaseField> fields;

    public SaizadFormModel(List<BaseField> fields) {
        super(fields);
        this.fields = fields;
    }

    public void publishNetworkError(List<ErrorModel.FieldError> errors){
        for (BaseField field : fields) {
            final ErrorModel.FieldError error = Utils.compareFindItem(errors, fieldError -> fieldError.field.equalsIgnoreCase(field.getFieldId()));
            if(error != null){
                field.networkErrorPublish(error.message.get(0));
            }
        }
    }
}
