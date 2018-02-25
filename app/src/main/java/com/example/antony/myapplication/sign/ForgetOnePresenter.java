package com.example.antony.myapplication.sign;

/**
 * Created by antony on 2018/2/21.
 */

public class ForgetOnePresenter {
    private ForgetOneView forgetOneView;
    private ModelSignSource modelSignSource;

    ForgetOnePresenter(ForgetOneView forgetOneView){
        this.forgetOneView = forgetOneView;
        modelSignSource =new ModelSignSource();
    }

    public void checkVerificationPresenter(){//检查验证码是否正确

    }
}
