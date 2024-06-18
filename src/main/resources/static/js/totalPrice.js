"use strict";

$(function(){
    const updateTotalPrice = () => {
        $("#total-price").text(`この商品金額：${new Intl.NumberFormat('ja-JP').format(calc())}円（税抜き）`);
    };

    const calc = () => {
        let totalVal = 0;
        const size = $("input[type=radio]:checked").data("price");
        $('input[name=toppingList]:checked').each((index, element) => {
            totalVal += $(element).data('price');
        })
        const num = $("option:selected").val();
        return (size + totalVal) * num;
    };
    $("#total-price").text(`この商品金額：${new Intl.NumberFormat('ja-JP').format($("input[type=radio]:checked").data("price"))}円（税抜き）`);
    $("input[type=radio], input[type=checkbox], select[name=quantity]").on("change", updateTotalPrice);
});