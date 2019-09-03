var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'clsPrint', 'coolMethod', [arg0]);
};

/*
module.exports = {
	connect: function(strdata, success, failure) {
		exec(success, failure, "clsPrint", "connect", [strdata]);
    }
}
*/



/*
var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'printplugin', 'coolMethod', [arg0]);
};

module.exports = {
	printWithText: function (strdata, success, failure) {
   		// alert("enter to"+ imgFile);
        exec(success, failure, "Bluetoothconnection", "printWithText", [strdata]);
    }
    ,printWithLogo: function (strdata,imgFile, success, failure) {
    	// alert("enter to"+ imgFile);
        exec(success, failure, "Bluetoothconnection", "printWithLogo", [strdata,imgFile]);
    }
}
*/

