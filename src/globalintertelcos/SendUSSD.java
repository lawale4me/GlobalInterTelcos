/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalintertelcos;

/**
 *
 * @author Ahmed
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IUSSDNotification;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.USSDDcs;
import org.smslib.USSDRequest;
import org.smslib.USSDResponse;
import org.smslib.USSDResultPresentation;
import org.smslib.USSDSessionStatus;
import org.smslib.modem.SerialModemGateway;

public class SendUSSD {

    String model, strmanufacturer, comPort;
    int baudRate;
    private static boolean DONE=false;

    public SendUSSD(int baudRate, String model, String strmanufacturer, String comPort) {
        this.baudRate = baudRate;
        this.model = model;
        this.strmanufacturer = strmanufacturer;
        this.comPort = comPort;
    }

    public void doIt(String phone, String ussdcode) throws Exception {
        Service srv;
//        System.out.println("Example: Send USSD Command from a serial gsm modem.");
//        System.out.println(Library.getLibraryDescription());
//        System.out.println("Version: " + Library.getLibraryVersion());
        srv = Service.getInstance();
        SerialModemGateway gateway = new SerialModemGateway("modem.com1", comPort, baudRate, strmanufacturer, model);        
        
        gateway.getATHandler().setStorageLocations("SMME");
        
        gateway.setProtocol(AGateway.Protocols.PDU);                
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");
        srv.addGateway(gateway);

        USSDNotification ussdNotification = new USSDNotification();

        srv.setInboundMessageNotification(new InboundNotification());
        srv.setCallNotification(new CallNotification());
        srv.setGatewayStatusNotification(new GatewayStatusNotification());
        srv.setUSSDNotification(ussdNotification);

        srv.startService();

        System.out.println();
        System.out.println("Modem Information:");
        System.out.println("  Manufacturer: " + gateway.getManufacturer());
        System.out.println("  Model: " + gateway.getModel());
        System.out.println("  Serial No: " + gateway.getSerialNo());
        System.out.println("  SIM IMSI: " + gateway.getImsi());
        System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
        System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
        System.out.println();

//        String resp = gateway.sendUSSDCommand("#124*1#"); // not working
//        String resp = gateway.sendCustomATCommand("AT+CUSD=1,\"*133*"+phone+"#\",15\r"); // working        
//        String resp = gateway.sendCustomATCommand("AT+CUSD=1,\"*"+ussdcode+"*"+phone+"#\",15\r"); // working
        USSDRequest request = new USSDRequest(USSDResultPresentation.PRESENTATION_ENABLED, "*123#", USSDDcs.UNSPECIFIED_7BIT, gateway.getGatewayId());
        Boolean b = gateway.sendUSSDRequest(request);

//        OutboundMessage outmsg =new OutboundMessage("432","2U 008128237092 100 1234");
//        gateway.sendMessage(outmsg);
//       
        //System.out.println("Request:" + request.getRawRequest());
        //System.out.println("Response:" + b);        
        System.in.read();

        srv.stopService();
    }

    public class USSDNotification implements IUSSDNotification {
 
        @Override
        public void process(AGateway gateway, USSDResponse response) {
            try {
                System.out.println("USSD handler called from Gateway: " + gateway.getGatewayId());
                System.out.println("Content:" + response.getContent());
                ///TRYING TO respond the second time
                if(response.getSessionStatus().equals(USSDSessionStatus.FURTHER_ACTION_REQUIRED))
                    System.out.println("FURTHER ACTION IS REQUIRED");
                {
                    //response.getSessionStatus();                      
                    gateway.setInbound(true);
                    gateway.setOutbound(true);
                    gateway.setProtocol(AGateway.Protocols.PDU); 
                    gateway.sendUSSDCommand("1");                    
                }
                //SECOND CHANCE ENDS HERE
                DONE=true;
            } catch (Exception ex) {
                Logger.getLogger(SendUSSD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class CallNotification implements ICallNotification {

        public void process(AGateway gateway, String callerId) {
            System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
        }
    }

    public class InboundNotification implements IInboundMessageNotification {

        public void process(AGateway gateway, org.smslib.Message.MessageTypes msgType, InboundMessage msg) {
            if (msgType == org.smslib.Message.MessageTypes.INBOUND) {
                System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
            } else if (msgType == org.smslib.Message.MessageTypes.STATUSREPORT) {
                System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
            }
            System.out.println("FROM:"+msg.getOriginator());
            System.out.println(msg);
            try 
            {
                if(msg.getText().contains("LAA")){
                OutboundMessage outmsg =new OutboundMessage("131","07055932235*50*12345");
                gateway.sendMessage(outmsg);
                gateway.deleteMessage(msg);
                }
                else{
                  gateway.deleteMessage(msg);
                }                                
            } catch (Exception e) {
                System.out.println("Oops!!! Something gone bad...");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public class GatewayStatusNotification implements IGatewayStatusNotification {

        public void process(AGateway gateway, org.smslib.AGateway.GatewayStatuses oldStatus, org.smslib.AGateway.GatewayStatuses newStatus) {
            System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }

    public static void main(String args[]) 
    {       
        SendUSSD app = new SendUSSD(11200, "HSDPA Modem", "WCDMA", "COM9");
        try {
            app.doIt("08038430865", "140");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
