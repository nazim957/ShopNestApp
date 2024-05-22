package com.newsapp.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPassword {

    private String securityQuestion;
    private String securityQuestionAnswer;
    private String newPassword;

}
