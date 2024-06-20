package com.example.controller;

import com.example.domain.Review;
import com.example.form.ReviewForm;
import com.example.service.ReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * レビュー機能の処理の制御用コントローラ.
 *
 * @author rui.inoue
 */
@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * レビュー投稿画面の作成.
     *
     * @param model 商品idの格納
     * @param itemId 商品id
     * @param form バリデーションチェック
     * @return レビュー投稿画面
     */
    @GetMapping()
    public String showReviewPost(Model model, Integer itemId, ReviewForm form){
        model.addAttribute("itemId", itemId);
        return "review";
    }

    /**
     * レビューの保存.
     *
     * @param form レビューの入力情報
     * @param result バリデーションチェック
     * @param model レビュー投稿画面に渡す
     * @return 商品詳細ページへリダイレクト
     */
    @PostMapping("/add")
    public String add(@Validated ReviewForm form, BindingResult result, Model model){
        if(result.hasErrors()){
            return showReviewPost(model, form.getItemId(), form);
        }

        Review review = new Review();
        BeanUtils.copyProperties(form, review);
        reviewService.addReview(review);
        return "redirect:/show-item-detail?id=" + form.getItemId();
    }
}
