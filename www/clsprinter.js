// based on https://www.outsystems.com/blog/posts/how-to-create-a-cordova-plugin-from-scratch/

// Empty constructor
function CLSPrinter() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
CLSPrinter.prototype.show = function(message, duration, successCallback, errorCallback) {
  	var options = {};
  	options.message = message;
  	options.duration = duration;
  	cordova.exec(successCallback, errorCallback, 'CLSPrinter', 'show', [options]);
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