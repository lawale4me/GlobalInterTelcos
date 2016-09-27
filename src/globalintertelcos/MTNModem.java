/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package globalintertelcos;

import core.AdminRepositoryImpl;
import core.AppManager;
import entities.Othermessages;
import entities.Transactions;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.IUSSDNotification;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.USSDDcs;
import org.smslib.USSDRequest;
import org.smslib.USSDResponse;
import org.smslib.USSDResultPresentation;
import org.smslib.USSDSession;
import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author Ahmed
 */
public class MTNModem {
    
    
    String model, strmanufacturer, comPort;
    int baudRate;
    private static boolean DONE = false;
    USSDSession ussdSession;

    
     Service srv ;
     SerialModemGateway gateway ;
     
    private  void initialize() throws Exception {
         srv = Service.getInstance();        
        gateway = new SerialModemGateway("MTN", comPort, baudRate, strmanufacturer, model);                
        gateway.getATHandler().setStorageLocations("SMME");                	                
        gateway.setProtocol(AGateway.Protocols.PDU);                
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");
        gateway.setSmscNumber("+234803000000");
        srv.addGateway(gateway);

        USSDNotification ussdNotification = new USSDNotification();
        OutboundNotification outboundNotification = new OutboundNotification();
        
        srv.setOutboundMessageNotification(outboundNotification);        
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
        
        USSDRequest request = new USSDRequest(USSDResultPresentation.PRESENTATION_ENABLED, "*556#", USSDDcs.UNSPECIFIED_7BIT, gateway.getGatewayId());
        Boolean b = gateway.sendUSSDRequest(request);
        
    }
   
    
    public MTNModem(int baudRate, String model, String strmanufacturer, String comPort) {
        this.baudRate = baudRate;
        this.model = model;
        this.strmanufacturer = strmanufacturer;
        this.comPort = comPort;
        ussdSession=new USSDSession();
    }

    public void doIt() throws Exception {
        
        //TRANSFER ---  Transfer 08031234567 1500 1234
        //BALANCE  ---  *556#       
        
        AppManager appManager = new AppManager(new AdminRepositoryImpl());   
        Transactions tranx=appManager.checkPending("MTN");
        if(tranx!=null){
            
         OutboundMessage msg,msg1;
         //Transfer 08031111222 120 3245
//          msg = new OutboundMessage("777", "Transfer "+tranx.getDestinationNumber()+" "+tranx.getAmount()+" 1234");
//                    try {
//                        boolean b=srv.sendMessage(msg);
//                        System.out.println("Message Error:"+msg.getErrorMessage());
//                        System.out.println("Message has been sent: "+"{"+b+"} |"+msg.getText());
////                        Thread.currentThread().wait(500);
//                        msg1 = new OutboundMessage("777", "YES");                        
//                        boolean b2=srv.sendMessage(msg1);                       
//                        tranx.setStatus(2);
//                    } catch(Exception e) {
//                        e.printStackTrace();
//                        System.out.println("Failed to send "+e);
//                    }           
            //USSD
            //*600*Recipient’s Number*Amount*PIN#
//            USSDRequest request = new USSDRequest(USSDResultPresentation.PRESENTATION_ENABLED, "*600*"+tranx.getDestinationNumber()+"*"+tranx.getAmount()+"*1234#", USSDDcs.UNSPECIFIED_7BIT, gateway.getGatewayId());
//            Boolean b = gateway.sendUSSDRequest(request);            
//            System.out.println("USSD sent:"+b);
//            if(b){ 
//                tranx.setStatus(2);
//                appManager.updateTranx(tranx);
//            }
            ///END OF USSD                        
        }
        else{
//            System.out.println("TRANX IS NULL ");
        }        
    }

    public class USSDNotification implements IUSSDNotification {

        @Override
        public void process(AGateway gateway, USSDResponse response) {
            System.out.println("USSD handler called from Gateway:  " + gateway.getGatewayId());
            System.out.println("Content:" + response.getContent()); 
            try {                
//                USSDRequest request = new USSDRequest(USSDResultPresentation.PRESENTATION_ENABLED, "1", USSDDcs.UNSPECIFIED_7BIT, gateway.getGatewayId());
//                Boolean b = gateway.sendUSSDRequest(request);               
            }  catch (Exception ex) {
                Logger.getLogger(MTNModem.class.getName()).log(Level.SEVERE, null, ex);
            }
            DONE=true;
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
            
            
            ///ASHARE 50 08024403945
            
            System.out.println("FROM:"+msg.getOriginator());
            System.out.println(msg);            
            AppManager appManager = new AppManager(new AdminRepositoryImpl());
            try 
            {
                if(msg.getText().contains("ASHARE")){                  
                  String req[]=Util.splitMessage(msg.getText());
                  if(req!=null){
                  String destNo=req[2],amount=req[1],destNetwork=Util.getNetwork(req[2]);                  
                  try{
                  appManager.insertTranx(new Transactions(msg.getOriginator(),destNo,amount,gateway.getGatewayId(),destNetwork,msg.getText(),msg.getUuid()));
                  }catch(Exception ex){
                    System.out.println(ex);
                  }
                  }
                  gateway.deleteMessage(msg);
                }
                else if(msg.getText().contains("was transferred to you from")&&msg.getOriginator().equalsIgnoreCase("777")){                                    
                //  0      1    2        3   4   5       6         7    8    9  10    11
                //N50.00 was transferred to you from 2348038430865 via share n sell service. 
                 String req[]=Util.splitConfirmMessageMTN(msg.getText());  
                 if(req!=null){                                   
                  try{
                 boolean bb= appManager.updateTranx(req[0].replaceAll(".00", "").replaceAll("N", ""),Util.formatDestination(req[6]).substring(1),msg.getText());
                  if(bb){gateway.deleteMessage(msg);}
                  }catch(Exception ex){
                    System.out.println(ex);
                  }
                  }                 
                }
                else{             
                     System.out.println("<<<OTHER MESSAGES>>>");
                     appManager.insertOtherMsg(new Othermessages(msg.getText(),msg.getUuid()+" "+msg.getGatewayId()));
                     gateway.deleteMessage(msg);
                }                                
            } catch (Exception ex) {
                System.out.println("Oops!!! Something gone bad...");
                System.out.println(ex.getMessage());
                ex.printStackTrace();
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
        MTNModem app = new MTNModem(11200, "HSDPA Modem", "WCDMA", "COM19");
        try {
            app.initialize();
            while(true){
            app.doIt();
            
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public class OutboundNotification implements IOutboundMessageNotification
	{
            /**
             *
             * @param gateway
             * @param msg
             */
            public void process(AGateway gateway, OutboundMessage msg)
		{
			System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
			System.out.println(msg);
		}
	}
    
    
}
