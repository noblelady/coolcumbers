/**
 * Welcome to Pebble.js!
 *
 * This is where you write your app.
 */
var locationOptions = { "timeout": 30000, "maximumAge": 60000, "enableHighAccuracy": true };
var lastLatitude = 0;
var lastLongitude = 0;

function force_location_update()
{
	var options = { enableHighAccuracy: true, maximumAge: 100, timeout: 60000 };
	var watchID = navigator.geolocation.watchPosition( locationSuccess, locationError, options );
	setTimeout( function() { navigator.geolocation.clearWatch( watchID ); }, 1000 );
}

Pebble.addEventListener("ready", function(e)
{
	force_location_update();
	navigator.geolocation.getCurrentPosition(locationSuccess, locationError, locationOptions);
});

function locationSuccess(pos)
{
	var coordinates = pos.coords;
	console.log("Coordinates!:");
	console.log(coordinates.latitude);
	console.log(coordinates.longitude);

	lastLatitude = coordinates.latitude;
	lastLongitude = coordinates.longitude;
}

function locationError(err)
{
	console.warn('Location error (' + err.code + '): ' + err.message);
	Pebble.showSimpleNotificationOnPebble("SEIZURE DETECT", "GPS ERROR: (" + err.code + "): " + err.message);
}

function send_text_message(messageBody, phoneNumber){
	messageBody = "Body=" + messageBody + "+at+location:+http://maps.apple.com/?q=" + lastLatitude + "," + lastLongitude + "&From=%22%2B1[YOUR TWILIO PHONE NUMBER]%22&To=%22%2B1" + phoneNumber + "%22";

	var req = new XMLHttpRequest();
	req.open("POST", "https://api.twilio.com/2010-04-01/Accounts/ACaf7616e8bf23974f5b25e50292af6691/SMS/Messages.json", true);
	req.setRequestHeader("Authorization", "Basic ba0654e46fd2d996a13c82fce8950c44 ");
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.setRequestHeader("Content-length", messageBody.length);
	req.setRequestHeader("Connection", "close");
	req.onload = function(e)
	{
		if ((req.readyState == 4) && ((req.status == 200) || (req.status == 201)))
		{
			console.log(req.responseText);
		}
		else
		{
			console.log("HTTP post error!  Body: " + messageBody + " Number: " + phoneNumber + " req: " + JSON.stringify(req));
			Pebble.showSimpleNotificationOnPebble("SEIZURE DETECT", "HTTP POST ERROR. See console log.");
		}
	};
	req.send(messageBody);
}


var UI = require('ui');
var Vector2 = require('vector2');

var main = new UI.Card({
  title: 'Pebble.js',
  icon: 'images/menu_icon.png',
  subtitle: 'Coolcumber!',
  body: 'Press any button.',
  subtitleColor: 'indigo', // Named colors
  bodyColor: '#9a0036' // Hex colors
});

main.show();

main.on('click', 'select', function(e) {
  var wind = new UI.Window({
    fullscreen: true,
  });
  var location = navigator.geolocation.getCurrentPosition();
  var textfield = new UI.Text({
    position: new Vector2(0, 65),
    size: new Vector2(144, 30),
    font: 'gothic-24-bold',
    text: 'Sending Text! \n' + location,
    textAlign: 'center'
  });
  wind.add(textfield);
  wind.show();
  force_location_update();
	navigator.geolocation.getCurrentPosition(locationSuccess, locationError, locationOptions);
  send_text_message("NAME+pushed+the+panic+button", "5102899092");
});
