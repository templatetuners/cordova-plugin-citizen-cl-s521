// based on https://www.outsystems.com/blog/posts/how-to-create-a-cordova-plugin-from-scratch/

package com.justapplications.cordova.plugin;

// The native Toast API
import android.widget.Toast;

/*
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
*/

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.citizen.sdk.labelprint.LabelConst;
import com.citizen.sdk.labelprint.LabelPrinter;
import com.citizen.sdk.labelprint.LabelDesign;

public class CLSPrinter extends CordovaPlugin {

    private static final String DURATION_LONG = "long";

    private LabelPrinter printer;

    // Connect
    private void connect(LabelPrinter printer, int portType, String address){

        printer = new LabelPrinter();

        // Connect printer
        int result = LabelConst.CLS_SUCCESS;
        //WiFi or Bluetooth
        result = printer.connect(portType, address);
        if (LabelConst.CLS_SUCCESS == result) {
          
            // Connect Success
            Toast.makeText(cordova.getActivity(), "Connect Success :" + Integer.toString(result), Toast.LENGTH_LONG).show();







            // Create an instance( LabelDesign class )
            LabelDesign design = new LabelDesign();
            // Text
            //design.drawTextPtrFont("America ta Grant", LabelConst.CLS_LOCALE_JP, LabelConst.CLS_PRT_FNT_TRIUMVIRATE_B, LabelConst.CLS_RT_NORMAL, 1, 1, LabelConst.CLS_PRT_FNT_SIZE_24, 20, 300);
            // QRCode
            //design.drawQRCode("DrawQRCode", LabelConst.CLS_ENC_CDPG_US_ASCII, LabelConst.CLS_RT_NORMAL, 4, LabelConst.CLS_QRCODE_EC_LEVEL_H, 20, 220);
            // BarCode
            design.drawBarCode("0123456789", LabelConst.CLS_BCS_CODE128, LabelConst.CLS_RT_NORMAL, 3, 3, 30, 20, 70, LabelConst.CLS_BCS_TEXT_SHOW);
            
            int printDarkness = printer.getPrintDarkness();
            if (LabelConst.CLS_PROPERTY_DEFAULT == printDarkness) {
                // Set Properties
                printer.setPrintDarkness(10);
            }
            // Print Label
            int printResult = printer.print(design, 0001);
            if (LabelConst.CLS_SUCCESS != printResult) {
                // Print Error
                Toast.makeText(cordova.getActivity(), "Print Error :" + Integer.toString(printResult), Toast.LENGTH_LONG).show();
            }







        } else {
            // Connect Error
            Toast.makeText(cordova.getActivity(), "Connect or Printer Error :" + Integer.toString(result), Toast.LENGTH_LONG).show();
        }
        //saveAddress(portType,  address);
    }

    private void print(LabelPrinter printer){
        LabelDesign design = new LabelDesign();
        int errflg = 0;
        int result;
        String errmsg = "";

        // Text
        result = design.drawTextPtrFont("Sample Print", LabelConst.CLS_LOCALE_JP, LabelConst.CLS_PRT_FNT_TRIUMVIRATE_B, LabelConst.CLS_RT_NORMAL, 1, 1, LabelConst.CLS_PRT_FNT_SIZE_24, 20, 300);
        if (LabelConst.CLS_SUCCESS != result) {
            errmsg = errmsg + Integer.toString(result) + ":\tdrawTextPtrFont Error\r\n";
            errflg = 1;
        }

        // QRCode
        result = design.drawQRCode("DrawQRCode", LabelConst.CLS_ENC_CDPG_US_ASCII, LabelConst.CLS_RT_NORMAL, 4, LabelConst.CLS_QRCODE_EC_LEVEL_H, 20, 220);
        if (LabelConst.CLS_SUCCESS != result){
            errmsg = errmsg + Integer.toString(result) + ":\tdrawQRCode Error\r\n";
            errflg = 1;
        }

        // BarCode
        result = design.drawBarCode("0123456789", LabelConst.CLS_BCS_CODE128, LabelConst.CLS_RT_NORMAL, 3, 3, 30, 20, 70, LabelConst.CLS_BCS_TEXT_SHOW );
        if (LabelConst.CLS_SUCCESS != result){
            errmsg = errmsg + Integer.toString(result) + ":\tdrawBarCode Error\r\n";
            errflg = 1;
        }

        // Print
        result = printer.print(design, 1);
        if (LabelConst.CLS_SUCCESS != result){
            errmsg = errmsg + Integer.toString(result) + ":\tprint Error";
            errflg = 1;
        }

        // Err Message
        if (errflg != 0){
            Toast.makeText(cordova.getActivity(), errmsg, Toast.LENGTH_LONG).show();
        }

    }


    // Disonnect
    private void disconnect(LabelPrinter printer){
        // Disconnect printer
        int result = printer.disconnect();
        if (LabelConst.CLS_SUCCESS == result) {
            // Disconnect Success
            Toast.makeText(cordova.getActivity(), "Disconnect Success :" + Integer.toString(result), Toast.LENGTH_LONG).show();
        }
        else {
            // Disconnect Error
            Toast.makeText(cordova.getActivity(), "Disconnect Error :" + Integer.toString(result), Toast.LENGTH_LONG).show();
        }
    }

    // Status
    private void status(LabelPrinter printer){
        String msg = "";
        int result;

        // Checks the status and populates into each property
        result = printer.printerCheck();
        if (LabelConst.CLS_SUCCESS != result){
            // SendData Error
            //alertDialog("Result", "Send Error : " + Integer.toString(result));
            Toast.makeText(cordova.getActivity(), "Send Error : " + Integer.toString(result), Toast.LENGTH_LONG).show();
            return;
        }

        msg = msg + Integer.toString(printer.getCommandInterpreterInAction()) + ":\tCommand interpreter in action" + "\r\n";
        msg = msg + Integer.toString(printer.getPaperError()) + ":\tPaper error" + "\r\n";
        msg = msg + Integer.toString(printer.getRibbonEnd()) + ":\tRibbon end" + "\r\n";
        msg = msg + Integer.toString(printer.getBatchProcessing()) + ":\tBatch processing" + "\r\n";
        msg = msg + Integer.toString(printer.getPrinting()) + ":\tPrinting" + "\r\n";
        msg = msg + Integer.toString(printer.getPause()) + ":\tPause" + "\r\n";
        msg = msg + Integer.toString(printer.getWaitingForPeeling()) + ":\tWaiting for peeling" + "\r\n";

        //alertDialog("Status data", msg);
        Toast.makeText(cordova.getActivity(), "Status data : " + msg, Toast.LENGTH_LONG).show();
    }




    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
        // Verify that the user sent a 'show' action

/*
        if(action.equals("connect")){

        } else {
            callbackContext.error("\"" + action + "\" is not a recognized action.");
            return false;
        }
*/


        if (!action.equals("show")) {
            callbackContext.error("\"" + action + "\" is not a recognized action.");
            return false;
        }
   
        String message;
        String duration;
        try {
            JSONObject options = args.getJSONObject(0);
            message = options.getString("message");
            duration = options.getString("duration");
        } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
        }
        // Get Address
        String address = "5.2.203.4";
        // Connect printer
        connect(printer, LabelConst.CLS_PORT_WiFi, address);
        // Disconnect
        //disconnect(printer);

        // Status
        //status(printer);




        // Create the toast
        Toast toast = Toast.makeText(cordova.getActivity(), message, DURATION_LONG.equals(duration) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);

        // Display toast
        //toast.show();

        // Send a positive result to the callbackContext
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
        callbackContext.sendPluginResult(pluginResult);
        return true;
    }
}