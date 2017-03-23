const figlet = require('figlet');

var out = figlet.textSync('Hello World!', {
    font: 'Standard'
});

console.log(out);
