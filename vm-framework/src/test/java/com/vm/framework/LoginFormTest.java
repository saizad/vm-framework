package com.vm.framework;

import com.sa.easyandroidform.fields.BaseField;
import com.sa.easyandroidform.form.BaseModelFormTest;
import com.vm.framework.model.LoginBody;

public class LoginFormTest extends BaseModelFormTest<LoginBody.UsernameForm> {


    @Override
    public LoginBody.UsernameForm initForm() {
        return null;
    }

    @Override
    public BaseField<?> changeFormFieldToAnyValue() {
        return null;
    }

    @Override
    public void setValidValue(BaseField<?> field) {

    }
}
