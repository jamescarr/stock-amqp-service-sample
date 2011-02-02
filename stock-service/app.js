[__dirname,__dirname + '/node_modules'].forEach(function(p){
  require.paths.unshift(p);
});
var stocks = require ('stockranges'),
  express = require ('express');
  
var app = express.createServer();

app.get('/quote/:ticker', function(req, res){
  stocks.getQuote(req.params.ticker, function(price){
    res.send(price);
  });
});

app.listen(8080);
