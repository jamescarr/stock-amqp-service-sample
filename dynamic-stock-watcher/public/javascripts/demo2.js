$(function(){
  var socket = new io.Socket();
  socket.connect();

  $('#watch').click(function(){
    var banding = $('#criteria option:selected').val()
    var command = {
      command:'watch',
      ticker:$('#ticker').val(),
      direction:$('input[name="direction"]:checked').val(),
      percentage:$('input[name="percent"]').val(),
      banding:banding=='gt'?'>=':'<='
    };
    console.log(command)
    command.routingKey = 'stock.'+command.ticker+'.'+banding+ (""+command.percentage).replace(".", "");
    var routingKey = command.routingKey;
    $('input').val('');  

    $('#output').append("<div class='routing' title='"+routingKey+"'>\
        <h3>"+routingKey+"</h3>\
        <div class='content'></div>\
        <div><span class='count'>0</span> messages recevied</div>\
      </div>");
    socket.send(command);
  });

  socket.on('message', function(msg){
    var selector = 'div[title="'+msg.key+'"]';
    $(selector+" .content").prepend(
      "<div class='"+determineClass(msg.message)+"'>\
        <span><b>Stock:</b>"+msg.message.ticker+"</span>\
        <span><b>Price:</b>"+msg.message.price+"</span>\
        <span><b>Previous:</b>"+msg.message.previous+"</span>\
       </div>"
    );
    $(selector+" .content div:gt(5)").remove();
    var count = parseInt($(selector + " .count").text());
    $(selector + " .count").text(count+1);
  });   
  function determineClass(stock){
    return stock.change > 0? 'up' : 'down';
  }
});
