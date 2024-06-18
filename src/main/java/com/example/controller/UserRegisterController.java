package com.example.controller;

import com.example.domain.User;
import com.example.form.UserRegisterForm;
import com.example.service.UserRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UserRegisterController {

  @Autowired
  private UserRegisterService userRegisterService;

  @GetMapping("/toRegister")
  public String toRegister(Model model, UserRegisterForm form) {
    System.out.println(form);
    return "register";
  }

  @PostMapping("/register")
  public String register(@Validated UserRegisterForm form, BindingResult result, Model model){

    System.out.println("エラー処理前: " + form + result);

    if(!form.getPassword().equals(form.getConfirmPassword())){
      result.rejectValue("confirmPassword", "", "確認用パスワードが一致しません");
    }

    if(userRegisterService.checkEmail(form.getEmail())){
      result.rejectValue("email", "duplicate", "メールアドレスが既に使用されています");
    }

    System.out.println("エラー処理後: " + form + result);

    if(result.hasErrors()){
      return toRegister(model, form);
    }

    User user = new User();
    BeanUtils.copyProperties(form, user);
    user.setName(form.getLastName() + form.getLastName());

    userRegisterService.insert(user);

    return "redirect:/toRegister";
  }
}
