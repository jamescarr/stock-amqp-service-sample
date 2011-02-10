require.paths.unshift(__dirname+'/node_modules');
require.paths.unshift(__dirname);
var express = require('express');
var io = require ('socket.io');
var stocks = require ('stocks');
var events = require ('events');


var app = module.exports = express.createServer(),
  emitter = new events.EventEmitter();

// Configuration

app.configure(function(){
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.use(express.bodyDecoder());
  app.use(express.methodOverride());
  app.use(app.router);
  app.use(express.staticProvider(__dirname + '/public'));
});

app.get('/', function(req, res){
  res.render('index');
});
app.get('/complex', function(req, res){
  res.render('complex');
});

if (!module.parent) {
  app.listen(3000);
  console.log("Express server listening on port %d", app.address().port);
  var socket = io.listen(app);
  stocks.createConsumer('stock.notifications', function(consumer){
    socket.on('connection', function(client){
      client.on('disconnect', function(){
        consumer.destroyAll();
      });
      client.on('message', function(msg){
        if(msg.command == 'bind'){
          emitter.emit('bind', msg, consumer, client);
        }else if(msg.command == 'watch'){
          emitter.emit('watch', msg,consumer, client);
        }else if(msg.command == 'stop'){
          consumer.stop(msg.routingKey);
        }
      });
    });
  });  
}

emitter.on('bind', function(msg, consumer, client){ 
  consumer.bindQueue(msg.routingKey, function(amqpMsg){
    client.send(
      {key:msg.routingKey,
       message:amqpMsg
      });
  });
});

emitter.on('watch', function(msg, consumer, client){
  var queue = msg.routingKey;
  consumer.bindQueue(queue, function(resp){
    client.send({
      key:queue,
      message:resp
    });
  });
  consumer.publish('stock.watch.specific', msg);

});
