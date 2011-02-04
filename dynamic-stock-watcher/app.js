require.paths.unshift(__dirname+'/node_modules');
require.paths.unshift(__dirname);
var express = require('express');
var io = require ('socket.io');
var stocks = require ('stocks')


var app = module.exports = express.createServer();

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
          consumer.bindQueue(msg.routingKey, function(amqpMsg){
            client.send(
              {key:msg.routingKey,
               message:amqpMsg
              });
          });
        }else if(msg.command == 'stop'){
          consumer.stop(msg.routingKey);
        }
      });
    });
  });  
}


