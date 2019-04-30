package com.devop.aashish.java.myapplication.application.utility;

import org.springframework.stereotype.Component;

@Component
    public class OTPGenerator {

    public Integer generateOTP() {
        return ((int) (Math.random() * 9000) + 1000);
    }
}
