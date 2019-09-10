// based on https://www.outsystems.com/blog/posts/how-to-create-a-cordova-plugin-from-scratch/

// Empty constructor
function CLSPrinter() {}

// Print
// The function that passes work along to native shells
// Message is a string
CLSPrinter.prototype.print = function(message, address, successCallback, errorCallback) {
  	var options = {};
  	options.message = message;
  	options.address = address;
  	cordova.exec(successCallback, errorCallback, 'CLSPrinter', 'print', [options]);
}

// Connect
CLSPrinter.prototype.connect = function(message, address, successCallback, errorCallback) {
  	var options = {};
  	options.message = message;
  	options.address = address;
  	cordova.exec(successCallback, errorCallback, 'CLSPrinter', 'connect', [options]);
}

// Disonnect
CLSPrinter.prototype.disconnect = function(message, address, successCallback, errorCallback) {
  	var options = {};
  	options.message = message;
  	options.address = address;
  	cordova.exec(successCallback, errorCallback, 'CLSPrinter', 'disconnect', [options]);
}

// Status
CLSPrinter.prototype.status = function(message, address, successCallback, errorCallback) {
  	var options = {};
  	options.message = message;
  	options.address = address;
  	cordova.exec(successCallback, errorCallback, 'CLSPrinter', 'status', [options]);
}

// Installation constructor that binds CLSPrinter to window
CLSPrinter.install = function() {
  	if (!window.plugins) {
    	window.plugins = {};
  	}
  	window.plugins.clsPrinter = new CLSPrinter();
  	return window.plugins.clsPrinter;
};
cordova.addConstructor(CLSPrinter.install);
