'use strict'

$(function () {
  $('#searchWord').on('keyup', function () {
    $.ajax({
      type: 'POST',
      url: 'http://localhost:8080/ec-202404a/get-item-info',
      data: {
        input: $('#searchWord').val(),
      },
      dataType: 'JSON',
    })
      .done(function (data) {
        let names = data.itemNameList
        $('#searchWord ').autocomplete({
          source: names,
        })
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        alert('エラー発生')
        console.log(XMLHttpRequest.status)
        console.log(textStatus)
        console.log(errorThrown.message)
      })
  })
})
