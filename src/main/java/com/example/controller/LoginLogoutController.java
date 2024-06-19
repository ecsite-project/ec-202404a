package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン・ログアウトをするコントローラ.
 *
 * @author YusakuTerashima
 */
@Controller
@RequestMapping("")
public class LoginLogoutController {

  /**
   * ログインする.
   *
   * @param error ログイン失敗時のリクエストパラメータ
   * @param model リクエストスコープ
   * @return ログイン画面へフォワード
   */
  @GetMapping("/login")
  public String toLogin(String error, Model model) {
    if ("true".equals(error)){
      model.addAttribute("notMatchError","メールアドレスまたはパスワードが一致しません");
      return "login";
    }

    return "login";
  }

  /**
   * ログアウトする.
   *
   * @return ログイン画面へフォワード
   */
  @GetMapping("/logout")
  public String toLogout(){
    return "login";
  }
}
