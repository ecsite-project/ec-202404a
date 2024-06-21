package com.example.controller;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.UserMyPageUpdateForm;
import com.example.service.UserMyPageService;
import com.example.service.UserRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  @Autowired
  private UserMyPageService userMyPageService;

  /**
   * ユーザマイページ画面を表示します.
   *
   * @param model Requestスコープの準備
   * @param form ユーザ情報更新のためのフォームの準備
   * @param loginUser ログイン情報
   * @return ユーザマイページ画面
   */
  @GetMapping("/to-my-page")
  public String toMyPage(Model model, UserMyPageUpdateForm form,@AuthenticationPrincipal LoginUser loginUser) {
    if (loginUser == null){
      return ":redirect/show-item-list";
    }
    User user = loginUser.getUser();
    BeanUtils.copyProperties(user, form);
    return "my-page";
  }

  /**
   * ユーザ情報の編集を行います.
   *
   * @param form ユーザの入力フォーム情報
   * @param result バリデーションチェック結果
   * @param loginUser ログイン情報
   * @param model Requestスコープの準備
   * @return ユーザマイページ画面
   */
  @PostMapping("/my-page")
  public String myPage(@Validated UserMyPageUpdateForm form, BindingResult result, @AuthenticationPrincipal LoginUser loginUser , Model model){

    String inputEmail = form.getEmail();
    boolean isEmailChanged = !(inputEmail.equals(loginUser.getUser().getEmail()));
    if(userRegisterService.checkEmail(inputEmail) && isEmailChanged){
      result.rejectValue("email", "duplicate", "メールアドレスが既に使用されています");
    }

    if(result.hasErrors()){
      return toMyPage(model, form,loginUser);
    }

    User newUserInfo = new User();
    BeanUtils.copyProperties(form, newUserInfo);
    userMyPageService.updateUserInfoById(newUserInfo,loginUser.getUser().getId());

    BeanUtils.copyProperties(newUserInfo, form);
    return "my-page";
  }
}
