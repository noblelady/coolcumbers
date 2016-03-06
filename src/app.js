var UI = require('ui');
var Vibe = require('ui/vibe'); 
var light = require('ui/light');
var Voice = require('ui/voice');
var Accel = require('ui/accel');
var Vector2 = require('vector2');
var Settings = require('settings'); 

var id; 
// Set for Navigivation // 
var locationOptions = {
  enableHighAccuracy: true, 
  timeout: 5000 
}; 

Pebble.addEventListener('ready', function(){
    //Splash Activity// 
    var win = new UI.Window({
        fullscreen: true,
    });
  
    var textfield = new UI.Text({
      position: new Vector2(0, 65),
      size: new Vector2(144, 30),
      font: 'gothic-24-bold',
      text: 'Loading...',
      textAlign: 'center'
    });
    win.add(textfield);
    light.trigger(); 
    win.show();
 
    setTimeout(function(){
      win.hide(); 
    }, 500); 
}); 

var mainActivity = new UI.Window(); 
var textfield = new UI.Text({
  position: new Vector2(0, 65),
  size: new Vector2(144, 30),
  font: 'gothic-24-bold',
  text: 'Shake your Pass!',
  textAlign: 'center', 
  color: 'white'
});
mainActivity.add(textfield);
mainActivity.show();

var pos = textfield.position(); 
pos.y += 20; 
textfield.animate('position', pos, 1000);
pos.x -= 20; 
textfield.animate('position', pos, 1000); 
pos.x += 20; 
textfield.animate('position', pos, 1000);

var circle = new UI.Circle({
  position: new Vector2(72, 55), 
  radius: 25, 
  brackgroundColor: 'red', 
});
mainActivity.add(circle); 
mainActivity.show()

var rad = circle.radius(); 
rad -= 25;
circle.animate('radius', rad, 10000); 
mainActivity.hide(circle); 


/*Accel.peek(function(e){ 
  console.log("This is axis: " + e.accel.axis + "Direction: " + e.accel.direction); 
  console.log("This is the x: " + e.accel.x + " " + "This is the Y: " + e.accel.y); 
}); 

Accel.on('data', function(e) {
  console.log('Just received ' + e.samples + ' from the accelerometer.');
});

Accel.on('tap', function(e) {
  console.log('Tap event on axis: ' + e.axis + ' and direction: ' + e.direction);
  Settings.data('Tap', e.transcription);
});*/

Pebble.addEventListener('appmessage', function(e) {
  console.log('Recieved Message' + JSON.stringify(e.payload)); 
});

var latitude, longitude; 
function locationSuccess(pos)
{
  latitude = pos.coords.latitude; 
  longitude = pos.coords.longitude; 
}

function locationError(err)
{
  console.warn('Error(' + err.code + '):' + err.message); 
}

Pebble.addEventListener('ready',
  function(e) {
    id = navigator.geolocation.watchPosition(locationSuccess, locationError, locationOptions);    
}); 

//navigator.geolocation.getCurrentPosition(success, error, options); 

var transactionId = Pebble.sendAppMessage({ '0': latitude, '1': longitude}, 
                                         function(e) { console.log('Successfully delivered message with transactionId=' + e.data.transactionId) }, 
                                         function(e) { console.log('Unable to deliver message with transactionId=' + e.data.transactionId + ' Error is: ' + e.data.error.message)});

