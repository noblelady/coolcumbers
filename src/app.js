/**
 * Welcome to Pebble.js!
 *
 * This is where you write your app.
 */

var UI = require('ui');
var Vector2 = require('vector2');

var main = new UI.Card({
  title: 'CoolCumber!',
  icon: 'images/menu_icon.png',
  subtitle: 'To set your keyphrase:',
  body: 'Press Up and Speak',
  subtitleColor: 'blue', // Named colors
  bodyColor: '#ff4d4d' // Hex colors
});

main.show();

main.on('click', 'up', function(e) {
    // Voice Recogonition // 
  Voice.dictate('start', false, function(e){
    if(e.err)
    {
      console.log('Error: ' + e.err); 
      return; 
    }
    console.log(e.transcription); 
  }); 
});

main.on('click', 'select', function(e) {
  var wind = new UI.Window({
    fullscreen: true,
  });
  var textfield = new UI.Text({
    position: new Vector2(0, 65),
    size: new Vector2(144, 30),
    font: 'gothic-24-bold',
    text: 'Text Anywhere!',
    textAlign: 'center'
  });
  wind.add(textfield);
  wind.show();
});

main.on('click', 'down', function(e) {
  var card = new UI.Card();
  card.title('A Card');
  card.subtitle('Is a Window');
  card.body('The simplest window type in Pebble.js.');
  card.show();
});