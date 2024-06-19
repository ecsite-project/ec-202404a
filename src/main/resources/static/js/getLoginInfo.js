"use strict";

$(function() {
    $("#loginInfoBtn").on("click", () => {
        $.ajax({
            url: "http://localhost:8080/ec-202404a/get-user/user-info",
            type: "post",
            dataType: "json",
            data: {
                userId: 1
            },
            async: true,
        }).done((data) => {
            $("#destinationName").val(data.user.name);
            $("#destinationEmail").val(data.user.email);
            $("#destinationZipcode").val(data.user.zipcode);
            $("#destinationPrefecture").val(data.user.prefecture);
            $("#destinationMunicipalities").val(data.user.municipalities);
            $("#destinationAddress").val(data.user.address);
            $("#destinationTel").val(data.user.telephone);
        }).fail((XMLHttpRequest, textStatus, errorThrown) => {
            console.log(XMLHttpRequest);
            console.log(textStatus);
            console.log(errorThrown);
        })
    })
})