package com.example.controller;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.AddItemForm;
import com.example.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * カートの処理の制御用のコントローラ.
 *
 * @author rui.inoue
 */
@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private HttpSession session;

    static final int MARGIN = 10000;// 仮IDが登録済ユーザのユーザIDと重複しないためのマージン
    static final int MAX_TMP_ID = 2000000000; // 仮IDがint型の最大値を超えないようにするための条件値(20億)

    /**
     * カート画面の表示.
     *
     * @param model 注文情報の格納
     * @return カート画面
     */
    @GetMapping("")
    public String toShoppingCart(Model model, HttpSession session, @AuthenticationPrincipal LoginUser user) {
        String sessionId = session.getId();

        if (user == null) {
            Integer tmpUserId = extractNumbers(sessionId);
            Order order = shoppingCartService.showOrder(tmpUserId);
            if (order == null || order.getOrderItemList().isEmpty()) {
                model.addAttribute("noOrder", "カートに商品は1つもありません");
            } else {
                model.addAttribute("order", order);
            }
            session.setAttribute("tmpUserId", tmpUserId);
            session.setAttribute("notLoginUser", "true");
            return "shopping-cart";
        }else {
            session.setAttribute("notLoginUser", "false");
            Order order = shoppingCartService.showOrder(user.getUser().getId());
            if (order == null || order.getOrderItemList().isEmpty()) {
                model.addAttribute("noOrder", "カートに商品は1つもありません");
            } else {
                model.addAttribute("order", order);
            }
            return "shopping-cart";
        }
    }

    /**
     * 商品をカートに追加.
     *
     * @param form 入力情報
     * @return カートの画面へリダイレクト
     */
    @PostMapping("/add-item")
    public String addItem(AddItemForm form, HttpSession session, @AuthenticationPrincipal LoginUser loginUser) {
        if (loginUser == null) {
            String sessionId = session.getId();
            Integer tmpUserId = extractNumbers(sessionId);
            shoppingCartService.addItem(tmpUserId, form);

            return "redirect:/shopping-cart";
        }else {
            User user = loginUser.getUser();
            shoppingCartService.addItem(user.getId(), form);

            return "redirect:/shopping-cart";
        }
    }

    /**
     * カート内の商品削除.
     *
     * @param orderItemId 商品id
     * @return カート一覧画面にリダイレクト
     */
    @PostMapping("/delete-item")
    public String deleteItem(Integer orderItemId) {
        shoppingCartService.deleteItem(orderItemId);
        return "redirect:/shopping-cart";
    }

    /**
     * セッションIDから仮のユーザIDを生成する.
     * 文字列から数字を取り出し，本来のユーザIDと重複しないよう10000を足して返す。
     *
     * @param str セッションID
     * @return 仮のユーザID
     */
    public int extractNumbers(String str) {
        int tmpId = 0;

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            if (tmpId < Long.parseLong(matcher.group()) && Long.parseLong(matcher.group()) < MAX_TMP_ID) {
                tmpId = Integer.parseInt(matcher.group());
            }
        }

        tmpId += MARGIN; // 登録されてるユーザとの重複を避けるため

        return tmpId;
    }
}
