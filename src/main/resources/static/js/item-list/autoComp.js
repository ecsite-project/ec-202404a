'use strict'

$(function () {
  $('#btn_change_status').on('click', function () {
    console.log()
    $.ajax({
      type: 'POST',
      url: 'http://localhost:8080/check-depo/change-status',
      data: {
        status: $('#status').text(),
      },
      dataType: 'JSON',
    })
      .done(function (data) {
        $('#status').text(data.status)
        $('#btn_change_status').text(data.btn_status + 'へ変更')
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        alert('エラー発生')
        console.log(XMLHttpRequest.status)
        console.log(textStatus)
        console.log(errorThrown.message)
      })
  })
})
