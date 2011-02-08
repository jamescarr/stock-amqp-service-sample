# AMQP Enabled Stock Service
This is yet another example illustrating amqp with multiple different aspects of system integration. This project has three main components

 * stock-service - a service written in node.js that simulates a webservice that returns a current stock quote for a given ticker. For example, hitting http://localhost:8080/quote/IBM will return a random price within a constrained range.
 * stock-monitor - java based application that utilizes spring-amqp to monitor stock prices by calling the service with a set of tickers at a specified interval and publishing the quotes out on an exchange. The routing keys for this also indicate if the stock price has gone up or down.
 * dynamic-stock-watcher - another node.js based app that has a web interface that allows a user to bind queues with user provided routign keys. For example, to listen for IBM price increases one would enter "stock.IBM.up" or to see all increases "#.up".

## Running the Examples
To run the node.js based components node 0.3.6 or greater is required. cd into the directories for them and type node app.js to start them up.

To run the java based producer, run gradle eclipse to generate eclipse artifacts and then import the project as an existing project in eclipse. With it imported and resolved, run the StockMonitorModule java class. 

With everything booted up, head over to http://localhost:3000 and have fun. Here are a few example routing keys to enter

	*.ORCL.*
	#.down
	stock.GOOG.*
	stock.#
	#.IBM.up

I'll add further examples as time allows.
