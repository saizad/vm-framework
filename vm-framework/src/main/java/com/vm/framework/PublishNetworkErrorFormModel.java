package com.vm.framework;

import com.sa.easyandroidform.fields.BaseField;
import com.sa.easyandroidform.form.FormModel;
import com.vm.framework.model.FieldError;
import com.vm.framework.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public abstract class PublishNetworkErrorFormModel<T> extends FormModel<T> {
    protected transient final List<BaseField<?>> fields;

    public PublishNetworkErrorFormModel(ArrayList<BaseField<?>> fields) {
        super(fields);
        this.fields = fields;
    }

    public void publishNetworkError(List<FieldError> errors){
        for (BaseField field : fields) {
            final FieldError error = Utils.compareFindItem(errors, fieldError -> fieldError.getField().equalsIgnoreCase(field.getFieldId()));
            if(error != null){
                field.networkErrorPublish(error.getMessage().get(0));
            }
        }
    }
}
