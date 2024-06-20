package com.example.controller;

import com.example.domain.User;
import com.example.form.UserMyPageUpdateForm;
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
public class UserMyPageController {

  @Autowired
  private UserRegisterService userRegisterService;

  @GetMapping("/to-my-page")
  public String toMyPage(Model model, UserMyPageUpdateForm form) {
    return "my-page";
  }

  @PostMapping("/my-page")
  public String register(@Validated UserMyPageUpdateForm form, BindingResult result, Model model){

    if(userRegisterService.checkEmail(form.getEmail())){
      result.rejectValue("email", "duplicate", "メールアドレスが既に使用されています");
    }

    if(result.hasErrors()){
      return toMyPage(model, form);
    }

    User user = new User();
    BeanUtils.copyProperties(form, user);
    user.setName(form.getName());

    userRegisterService.insert(user);

    // 一旦ユーザ登録画面へリダイレクト
    return "redirect:/toMyPage";
  }
}
