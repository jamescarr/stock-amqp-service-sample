(function(m){
  var stocks = {
    'ORCL':{high:32.20, low:31.84},
    'GOOG':{high:604.47, low:595.55},
    'MSFT':{high: 27.90, low:27.42},
    'SAP':{high:58.29, low:57.73},
    'IBM':{high:162.00, low:158.68}
  };

  m.exports = {
    getQuote:function(ticker,cb){
      var range = stocks[ticker];
      var x = 0;
      for(x = 0; x < range.low || x > range.high; x = Math.random() * 1000);
      cb(new Number(x.toFixed(2)));
    }
  };
})(module);

