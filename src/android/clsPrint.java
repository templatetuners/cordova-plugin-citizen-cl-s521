package org.apache.cordova.clsprint;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;



import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;

import android.widget.Toast;
import android.content.Context;


import com.citizen.sdk.labelprint.LabelConst;
import com.citizen.sdk.labelprint.LabelPrinter;
import com.citizen.sdk.labelprint.LabelDesign;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class clsPrint extends CordovaPlugin {

    // Constructor
    
    public printplugin() {
    }
    

        //final String DEFAULT_IP_ADDRESS = "192.168.128.212";
        private LabelPrinter printer;

        // Get Address
        String address = "5.0.203.4";

    // Connect
    //private void connect(LabelPrinter printer, int portType, String address){
    private void connect(LabelPrinter printer, int portType, String address, CallbackContext callbackContext){

        // Connect printer
        int result = LabelConst.CLS_SUCCESS;

        //WiFi or Bluetooth
        result = printer.connect(portType, address);

        if (LabelConst.CLS_SUCCESS == result) {
            // Connect Success
            //Toast.makeText(MainActivity.this, "Connect Success :" + Integer.toString(result), Toast.LENGTH_LONG).show();
            //callbackContext.error("Connect Success :" + Integer.toString(result));
            callbackContext.success();
        } else {
            // Connect Error
            //Toast.makeText(MainActivity.this, "Connect or Printer Error :" + Integer.toString(result), Toast.LENGTH_LONG).show();
            callbackContext.error("Connect or Printer Error :" + Integer.toString(result));
        }

        //saveAddress(portType,  address);
    }

    public void isConnected() {
        // Manage connection checking here
       callbackContext.success();
    }



    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        } else if(action.equals('connect')){


            // Connect printer
            //connect(printer, LabelConst.CLS_PORT_WiFi, address, callbackContext);
            this.isConnected();



        } else if(action.equals('disconnect')){

        } else if(action.equals('print')){

        } else {
            return false;
        }

        // Only alert and confirm are async.
        callbackContext.success();
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
