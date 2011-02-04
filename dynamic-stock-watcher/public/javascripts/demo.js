$(function(){
  var socket = new io.Socket();
  socket.connect();

  $('#bind').click(function(){
    var routingKey = $('#routing_key').val();
    $('#routing_key').val('');
    $('fieldset').append("<div class='routing' title='"+routingKey+"'>\
        <h3>"+routingKey+"</h3>\
        <div class='content'></div>\
        <div><span class='count'>0</span> messages recevied</div>\
      </div>");
    socket.send({
      command:'bind',
      routingKey:routingKey
    });
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
