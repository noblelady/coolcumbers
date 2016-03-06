var UI = require('ui');
var Vibe = require('ui/vibe'); 
var light = require('ui/light');
var Voice = require('ui/voice');
var Accel = require('ui/accel');
var Vector2 = require('vector2');
var Settings = require('settings'); 


// Set for Navigivation // 
var options = {
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

/*
Accel.peek(function(e){ 
  console.log("This is axis: " + e.accel.axis + "Direction: " + e.accel.direction); 
  console.log("This is the x: " + e.accel.x + " " + "This is the Y: " + e.accel.y); 
}); 

Accel.on('data', function(e) {
  console.log('Just received ' + e.samples + ' from the accelerometer.');
});

Accel.on('tap', function(e) {
  console.log('Tap event on axis: ' + e.axis + ' and direction: ' + e.direction);
  Settings.data('Tap', e.transcription);
});
*/ 

Pebble.addEventListener('appmessage', function(e) {
  console.log('Recieved Message' + JSON.stringify(e.payload)); 
});

function success(pos)
{
  var lat = pos.coords.latitude; 
  var long = pos.coords.longitude; 
}

function error(err)
{
  console.warn('Error(' + err.code + '):' + err.message); 
}
navigator.geolocation.getCurrentPosition(success, error, options); 

var transactionId = Pebble.sendAppMessage({ '0': lat, '1': 'Location of Geo-Coords'}, 
                                         function(e) { console.log('Sent location'); });

 

/*
mainActivity.on('click', 'down', function(e){
  Voice.dictate('start', false, function(e){
    if(e.err)
    {  
      console.warn('Error: ' + e.err); 
      Pebble.showSimpleNotificationOnPebble('Coolcumber', 'Setting Phrase Failed.'); 
      return; 
    }
    console.log(e.transcription); 
    Pebble.showSimpleNotificationOnPebble('Coolcumber', 'Setting Phrase Worked.'); 
    Settings.data('user_phrase', e.transcription);  
  });
}); 
*/


// pebble.addEventListener('click', 'down', function(e) {
//     console.log('At least pressed')
//     Voice.dictate('start', false, function(e){
//       if(e.err)
//       {
//         console.warn('Error: ' + e.err); 
//         Pebble.showSimpleNotificationOnPebble('Coolcumber', 'Setting Phrase Failed.'); 
//         return; 
//       }
//       console.log(e.transcription); 
//       //Pebble.showSimpleNotificationOnPebble('Coolcumber', 'Setting Phrase Worked.'); 
//       Settings.data('user_phrase', e.transcription);  
//   });
// });

// pebble.addEventListener('click', 'up', function(e){
//   var card = new UI.Card();
//   card.title('A Card');
//   card.subtitle('Is a Window');
//   card.body('The simplest window type in Pebble.js.');
//   card.show();
// }); 


// Accel.peek(function(e){
//   if(e.accel.x >= 10 || e.accel.y >= 10) {
//     console.log('send text'); 
//   }
// }); 