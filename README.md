# cordova-plugin-clsprint
Cordova Plugin for Citizen CL-S521 Printer

Citizen Product and SDK
https://www.citizen-systems.com/en/products/printer/label/cl-s521/

## Installation

    cordova plugin add https://github.com/templatetuners/cordova-plugin-clsprinter/

## Supported Platforms

- Android

## Quick Example

    window.plugins.clsPrinter.connect('Connected!', '0.0.0.0', function() {
        console.log('Works!');
    }, function(err) {
        console.log('Uh oh... ' + err);
    });

