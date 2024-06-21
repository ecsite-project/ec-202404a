"use strict";

$(function() {
    if($("#bookmark-icon").data("flag") === true){
        $("#bookmark-icon").css("color", "blue");
    }

    $("#bookmark-icon").on("click", () => {
        if($("#bookmark-icon").data("flag") === true){
            $("#bookmark-icon").css("color", "red");
            $("#bookmark-icon").data("flag", false);
        }else{
            $("#bookmark-icon").css("color", "blue");
            $("#bookmark-icon").data("flag", true);
        }

        $.ajax({
            url: "http://localhost:8080/ec-202404a/bookmark",
            type: "post",
            dataType: "json",
            data: {
                itemId: $("#bookmark-icon").data("itemid")
            },
            async: true
        }).done((data) => {
            console.log(data);
        }).fail((XMLHttpRequest, textStatus, errorThrown) => {
            console.log(XMLHttpRequest);
            console.log(textStatus);
            console.log(errorThrown);
        })
    })
})