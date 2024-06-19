'use strict'

$(function () {
  $('#searchAddressBtn').on('click', () => {
    $.ajax({
      url: 'https://zipcoda.net/api',
      type: 'get',
      dataType: 'json',
      data: {
        zipcode: $('#zipcode').val(),
      },
      async: true,
    })
      .done((data) => {
        $('#prefecture').val(data.items[0].components[0])
        $('#municipalities').val(data.items[0].components[1])
        $('#address').val(data.items[0].components[2])
      })
      .fail((XMLHttpRequest, textStatus, errorThrown) => {
        console.log(XMLHttpRequest)
        console.log(textStatus)
        console.log(errorThrown)
      })
  })
})
