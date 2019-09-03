package org.apache.cordova.clsprint;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;



import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;

/*
import android.widget.Toast;
import android.content.Context;


import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
*/

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
    /*
    public printplugin() {
    }
    */
    

        //final String DEFAULT_IP_ADDRESS = "192.168.128.212";
        private LabelPrinter printer;

        // Get Address
        String address = "5.0.203.4";


    // Print
    private void print(LabelPrinter printer, CallbackContext callbackContext){
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
            //Toast.makeText(MainActivity.this, errmsg, Toast.LENGTH_LONG).show();
            callbackContext.error(errmsg);
        }

    }


    // Disonnect
    private void printDisconnect(LabelPrinter printer){
        // Disconnect printer
        int result = printer.disconnect();
        if (LabelConst.CLS_SUCCESS == result) {
            // Disconnect Success
            //Toast.makeText(MainActivity.this, "Disconnect Success :" + Integer.toString(result), Toast.LENGTH_LONG).show();
            //callbackContext.success("Disconnect Success :" + Integer.toString(result));
        }
        else {
            // Disconnect Error
            //Toast.makeText(MainActivity.this, "Disconnect Error :" + Integer.toString(result), Toast.LENGTH_LONG).show();
            //callbackContext.error("Disconnect Error :" + Integer.toString(result));
        }
    }


    // Status
    private void printerStatus(LabelPrinter printer){
        String msg = "";
        int result;

        // Checks the status and populates into each property
        result = printer.printerCheck();
        if (LabelConst.CLS_SUCCESS != result){
            // SendData Error
            //alertDialog("Result", "Send Error : " + Integer.toString(result));
            //callbackContext.error("Send Error : " + Integer.toString(result));
            return;
        }

        msg = msg + Integer.toString(printer.getCommandInterpreterInAction()) + ":\tCommand interpreter in action" + "\r\n";
        msg = msg + Integer.toString(printer.getPaperError()) + ":\tPaper error" + "\r\n";
        msg = msg + Integer.toString(printer.getRibbonEnd()) + ":\tRibbon end" + "\r\n";
        msg = msg + Integer.toString(printer.getBatchProcessing()) + ":\tBatch processing" + "\r\n";
        msg = msg + Integer.toString(printer.getPrinting()) + ":\tPrinting" + "\r\n";
        msg = msg + Integer.toString(printer.getPause()) + ":\tPause" + "\r\n";
        msg = msg + Integer.toString(printer.getWaitingForPeeling()) + ":\tWaiting for peeling" + "\r\n";

        // alertDialog("Status data", msg);
        //callbackContext.success("Status data" + msg);
    }


    // Connect
    //private void connect(LabelPrinter printer, int portType, String address){
    private void printConnect(LabelPrinter printer, int portType, String address){

        // Connect printer
        int result = LabelConst.CLS_SUCCESS;

        //WiFi or Bluetooth
        result = printer.connect(portType, address);

        if (LabelConst.CLS_SUCCESS == result) {
            // Connect Success
            //Toast.makeText(MainActivity.this, "Connect Success :" + Integer.toString(result), Toast.LENGTH_LONG).show();
            //callbackContext.error("Connect Success :" + Integer.toString(result));
            //callbackContext.success("Connect Success :" + Integer.toString(result));
        } else {
            // Connect Error
            //Toast.makeText(MainActivity.this, "Connect or Printer Error :" + Integer.toString(result), Toast.LENGTH_LONG).show();
            //callbackContext.error("Connect or Printer Error :" + Integer.toString(result));
        }

        //saveAddress(portType,  address);
    }

    /*
    public void isConnected() {
        // Manage connection checking here
       callbackContext.success();
    }
    */


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        } else if(action.equals("printConnect")){

            // Connect printer
            printConnect(printer, LabelConst.CLS_PORT_WiFi, address, callbackContext);
            //this.isConnected();

        } else if(action.equals("printDisconnect")){
            // Disconnect
            printDisconnect(printer);

        } else if(action.equals("printLabel")){

            // Create an instance( LabelDesign class )
            LabelDesign design = new LabelDesign();
            // Text
            design.drawTextPtrFont("Sample Print", LabelConst.CLS_LOCALE_JP, LabelConst.CLS_PRT_FNT_TRIUMVIRATE_B, LabelConst.CLS_RT_NORMAL, 1, 1, LabelConst.CLS_PRT_FNT_SIZE_24, 20, 300);
            // QRCode
            design.drawQRCode("DrawQRCode", LabelConst.CLS_ENC_CDPG_US_ASCII, LabelConst.CLS_RT_NORMAL, 4, LabelConst.CLS_QRCODE_EC_LEVEL_H, 20, 220);
            // BarCode
            design.drawBarCode("0123456789", LabelConst.CLS_BCS_CODE128, LabelConst.CLS_RT_NORMAL, 3, 3, 30, 20, 70, LabelConst.CLS_BCS_TEXT_SHOW);
            int printDarkness = printer.getPrintDarkness();
            if (LabelConst.CLS_PROPERTY_DEFAULT == printDarkness) {
                // Set Properties
                printer.setPrintDarkness(10);
            }
            // Print Label
            int result = printer.print(design, 0001);
            if (LabelConst.CLS_SUCCESS != result) {
                // Print Error
                //Toast.makeText(MainActivity.this, "Print Error :" + Integer.toString(result), Toast.LENGTH_LONG).show();
                callbackContext.error("Print Error :" + Integer.toString(result));
            }


        } else if(action.equals("printerStatus")){
            // Status
            printerStatus(printer);
        } /*else {
            return false;
        }*/

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
