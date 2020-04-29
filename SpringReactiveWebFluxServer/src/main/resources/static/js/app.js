//var s1 = new TimeSeries();

var courbes = [];

/*
 * setInterval(function() { s1.append(new Date().getTime(), Math.random() *
 * 10000); }, 500);
 */

var chart = new SmoothieChart({tooltip:true,timestampFormatter:SmoothieChart.timeFormatter});

chart.streamTo(document.getElementById("chartcanvas"), 500);

var colors = [ {
	strokeStyle : 'rgba(255, 0, 0, 1)',
	fillStyle : 'rgba(255,0 , 0, 0.2)',
	lineWidth : 1
}, {
	strokeStyle : 'rgba(0, 255, 0, 1)',
	fillStyle : 'rgba(0,0 , 255, 0.2)',
	lineWidth : 1
}, {
	strokeStyle : 'rgba(0, 0,255,1)',
	fillStyle : 'rgba(0, 0, 255, 0.2)',
	lineWidth : 1
}, ];


var index=-1;
function onConnect(id) {

	
	if (courbes[id] == undefined) {
		courbes[id] = new TimeSeries(); // créer 1 nouvelle courbe
		chart.addTimeSeries(courbes[id], colorRandom());

		alert(id);

		var connection = new EventSource(
				"http://localhost:8080/streamTransactionsBySociete/" + id);

		console.log(connection);

		connection.onmessage = function(response) {
			var transactionJson = JSON.parse(response.data);
			console.log(transactionJson.price);
			courbes[id].append(new Date().getTime(), transactionJson.price);
		};
	}

}

function colorRandom(){
	++index;
	if(index>colors.lenght)
		index=0 ; 
	
	return colors[index]
}
