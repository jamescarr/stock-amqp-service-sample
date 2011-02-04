require.paths.unshift(__dirname);
var stocks = require ('stocks');
var event = require ('events')

var emitter = new event.EventEmitter();

stocks.createConsumer('stock.notifications', 
  function(consumer){
    var msgsSeen = 0;
    consumer.bindQueue('stock.#.down', function(msg){
      console.log(msg);
        consumer.stop('stock.#.down');
        console.log('destroying queue');
      }
    });
});





