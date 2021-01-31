$(function(){
  // Запускаем функцию при изменении специализации
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"
  $('#specSelect').on('change', function(){
    let spec_Id =  $(this).val();
    console.log(spec_Id);
    $.ajax({
      type: "GET",
      dataType: 'json',
      url: '/client/doctors?specId=' + spec_Id
    }).done(function(doctors){
      fillSelectDoctors(doctors); // выводит второй список по событию из первого селекта
    })
})
})


$(function(){
  // Запускаем функцию при изменении доктора
src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"
  $('#doctorSelect').on('change'  , function(){
    let doc_Id =  $(this).val();
    console.log(doc_Id);
    $.ajax({
      type: "GET",
      dataType: 'json',
      url: '/client/dates?docId=' + doc_Id
    }).done(function(tickets){
      fillSelectDate(tickets); // выводит третий список по событию из вторго селекта
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
       fillSelectTime(listTicketsByDate);
     })
})
})


//Функция заполняет выпадающий список докторами
function fillSelectDoctors(doctors){
console.log("Method fillSelectDoctors");
doctorsArr = doctors;
var doctorSelect = document.getElementById("doctorSelect");
doctorSelect.options.length = 1; // обнуляем options
dateSelect.options.length = 1;
timeSelect.options.length = 1;
  for(var i = 0; i < doctorsArr.length; i++) {
      var doctorFIO = Object(doctors[i].surname + ' ' + doctors[i].name + ' '  + doctors[i].patronymic);
      var idDoctor = Object(doctors[i].id);
      var el = document.createElement("option");
      el.textContent = doctorFIO;
      el.value = idDoctor;
      doctorSelect.appendChild(el);
      console.log(doctorFIO);
  }
}

//Функция заполняет выпадающий список датами
function fillSelectDate(tickets){
console.log(ticketId);
ticketsArr = tickets;
var dateSelect = document.getElementById("dateSelect");
dateSelect.options.length = 1; // обнуляем options
timeSelect.options.length = 1;
  for(var i = 0; i < ticketsArr.length; i++) {
      var ticketId = Object(ticketsArr[i].id);
      var date = Object(ticketsArr[i].date);
      var el = document.createElement("option");
      el.textContent = date;
      el.value = ticketId;
      dateSelect.appendChild(el);
  }
}


//Функция заполняет выпадающий список временем
function fillSelectTime(tickets){
ticketsArr = tickets;
var dateSelect = document.getElementById("timeSelect");
dateSelect.options.length = 1; // обнуляем options
  for(var i = 0; i < ticketsArr.length; i++) {
      var ticketId = Object(ticketsArr[i].id);
      var time = Object(ticketsArr[i].time);
      var el = document.createElement("option");
      el.textContent = time;
      el.value = ticketId;
      dateSelect.appendChild(el);
  }
}