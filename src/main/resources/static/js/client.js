 // Запускаем функцию при изменении специализации
$(function(){
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"
  $('#specSelect').on('change', function(){
    let spec_Id =  $(this).val();
    console.log(spec_Id);
    $.ajax({
      type: "GET",
      dataType: 'json',
      url: '/client/doctors?specId=' + spec_Id
    }).done(function(doctors){
      fillSelect(doctors, "doctorSelect");
    })
})
})


// Запускаем функцию при изменении врача
$(function(){
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"
  $('#doctorSelect').on('change'  , function(){
    let doc_Id =  $(this).val();
    console.log(doc_Id);
    $.ajax({
      type: "GET",
      dataType: 'json',
      url: '/client/dates?docId=' + doc_Id
    }).done(function(tickets){
      fillSelect(tickets, "dateSelect");
    })
})
})


// Запускаем функцию при изменении даты
$(function(){
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"
  $('#dateSelect').on('change', function(){
     let ticketId =  $(this).val();
     console.log('ticketId ' + ticketId);
     $.ajax({
       type: "GET",
       dataType: 'json',
       url: '/client/times?ticketId=' + ticketId
     }).done(function(listTicketsByDate){
       fillSelect(listTicketsByDate, "timeSelect");
     })
})
})


//Функция заполняет выпадающий список
function fillSelect(arr, nameSelect){
var select = document.getElementById(nameSelect);
select.options.length = 1;
var textContent;
var value;
  for(var i = 0; i < arr.length; i++) {
    if(nameSelect == "doctorSelect"){
          textContent = arr[i].surname + ' ' + arr[i].name + ' '  + arr[i].patronymic;
          value = arr[i].id;
    }
    if(nameSelect == "dateSelect"){
            textContent = arr[i].date;
            value = arr[i].id;
            console.log(textContent);
    }
    if(nameSelect == "timeSelect"){
            textContent = arr[i].time;
            value = arr[i].id;
    }
     var el = document.createElement("option");
      el.textContent = textContent;
      el.value = value;
      select.appendChild(el);
  }
}
