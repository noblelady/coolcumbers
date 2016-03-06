var UI = require('ui');
var Vibe = require('ui/vibe'); 
var light = require('ui/light');
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
  text: 'Press Down to Set Phrase!',
  textAlign: 'center'
});
mainActivity.add(textfield);
mainActivity.show();

pebble.addEventListener('click', 'down', function(e) {
    Voice.dictate('start', false, function(e){
      if(e.err)
      {
        console.warn('Error: ' + e.err); 
        Pebble.showSimpleNotificationOnPebble('Coolcumber', 'Setting Phrase Failed.'); 
        return; 
      }
      console.log(e.transcription); 
      Settings.data('user_phrase', e.transcription);  
  });
  mainActivity.hide(); 
});

Accel.peek(function(e){
  if(e.accel.x >= 10 || e.accel.y >= 10) {
    console.log('send text'); 
  }
}); 