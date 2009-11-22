package mx.gob.salud.irc.client.utils;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.services.CommonCallBack;
import mx.gob.salud.irc.client.statics.Codes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class UserMessage {

	public static DialogBox dlgWait = null;
	
	public static void ok(String title, String message, CommonCallBack ccb){
		UserMessage usrMsg = new UserMessage(); 
		usrMsg.okMsg(title, message, ccb);
	}
	
	public void okMsg(String title, String message, final CommonCallBack ccb){
		final DialogBox dlgOk = new DialogBox();
		dlgOk.ensureDebugId("cwDialogBoxOk");
		
		FlexTable tBody = new FlexTable();
	    tBody.setStyleName(Resources.msg.styleOkMsg());
	    dlgOk.add(tBody);
	    
	    tBody.setCellPadding(5);
	    
	    Button closeButton = new Button(Resources.msg.buttonOk(),
		        new ClickHandler() {
		          public void onClick(ClickEvent event) {
		        	  if (ccb != null)
		        		  if (ccb.getCode() == Codes.OK)
		        			  ccb.onSuccess();
		        		  else
		        			  ccb.onFailure();
		        	  dlgOk.hide(); 
		          }
		        });
	    
	    tBody.setWidget(0, 0, new Image(GWT.getModuleBaseURL()+"images/ok.png"));
	    tBody.setWidget(0, 1, new Label(message));
	    tBody.setWidget(1, 0, closeButton);
	    tBody.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    tBody.getFlexCellFormatter().setColSpan(1, 0, 2);
	    
	    
	    dlgOk.setText(title);
		dlgOk.setAnimationEnabled(true);
		dlgOk.center();
		dlgOk.show();
	}
	
	public static void warning(String title, String message, CommonCallBack ccb){
		UserMessage usrMsg = new UserMessage(); 
		usrMsg.warningMsg(title, message, ccb);
	}
	
	public void warningMsg(String title, String message, final CommonCallBack ccb){
		final DialogBox dlgWar = new DialogBox();
		dlgWar.ensureDebugId("cwDialogBoxWarning");
		
		FlexTable tBody = new FlexTable();
	    tBody.setStyleName(Resources.msg.styleOkMsg());
	    dlgWar.add(tBody);
	    
	    tBody.setCellPadding(5);
	    
	    Button closeButton = new Button(Resources.msg.buttonOk(),
		        new ClickHandler() {
		          public void onClick(ClickEvent event) {
		        	  if (ccb != null)
		        		  if (ccb.getCode() == Codes.OK)
		        			  ccb.onSuccess();
		        		  else
		        			  ccb.onFailure();
		        	  dlgWar.hide(); 
		          }
		        });
	    
	    tBody.setWidget(0, 0, new Image(GWT.getModuleBaseURL()+"images/warning.png"));
	    tBody.setWidget(0, 1, new Label(message));
	    tBody.setWidget(1, 0, closeButton);
	    tBody.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    tBody.getFlexCellFormatter().setColSpan(1, 0, 2);
	    
	    
	    dlgWar.setText(title);
	    dlgWar.setAnimationEnabled(true);
	    dlgWar.center();
	    dlgWar.show();
	}
	
	public static void error(String title, String message, String content, CommonCallBack ccb){
		UserMessage usrMsg = new UserMessage(); 
		usrMsg.errorMsg(title, message, content, ccb);
	}
	
	public void errorMsg(String title, String message, String content, final CommonCallBack ccb){
		final DialogBox dlgError = new DialogBox();
		dlgError.ensureDebugId("cwDialogBoxWait");
	    
	    FlexTable tBody = new FlexTable();
	    tBody.setStyleName(Resources.msg.styleErrorMsg());
	    dlgError.add(tBody);
	    
	    tBody.setCellPadding(5);
	    
	    tBody.setWidget(0, 0, new Image(GWT.getModuleBaseURL()+"images/error.png"));
	    tBody.getFlexCellFormatter().setRowSpan(0, 0, 2);
	    tBody.setWidget(0, 1, new Label(message));
	    if (content != null){
		    tBody.setWidget(1, 0, new Label(content));
		    tBody.getFlexCellFormatter().setStyleName(1, 0, Resources.msg.styleErrorContent());
	    }
	    
	    Button closeButton = new Button(Resources.msg.buttonOk(),
		        new ClickHandler() {
		          public void onClick(ClickEvent event) {
		        	  if (ccb != null)
		        		  if (ccb.getCode() == Codes.OK)
		        			  ccb.onSuccess();
		        		  else
		        			  ccb.onFailure();
		        	  dlgError.hide(); 
		          }
		        });
	    
	    tBody.setWidget(2, 0, closeButton);
	    tBody.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    tBody.getFlexCellFormatter().setColSpan(2, 0, 2);
	    
	    dlgError.setText(title);
	    dlgError.setAnimationEnabled(true);
	    dlgError.center();
	    dlgError.show();
	}
	
	public static void wait(String title, String message, String content){
		if (dlgWait == null){
			dlgWait = new DialogBox();
			dlgWait.ensureDebugId("cwDialogBoxWait");
		    
		    FlexTable tBody = new FlexTable();
		    //dlgBox.setStyleName(Resources.msg.styleWaitMsg());
		    tBody.setStyleName(Resources.msg.styleWaitMsg());
		    dlgWait.add(tBody);
		    
		    tBody.setCellPadding(5);
		    //tBody.setBorderWidth(2);
		    
		    tBody.setWidget(0, 0, new Image(GWT.getModuleBaseURL()+"images/wait.gif"));
		    tBody.getFlexCellFormatter().setRowSpan(0, 0, 2);
		    tBody.setWidget(0, 1, new Label(message));
		    tBody.setWidget(1, 0, new Label(content));
		    tBody.getFlexCellFormatter().setStyleName(1, 0, Resources.msg.styleWaitContent());
		    
		    dlgWait.setText(title);
		    dlgWait.setAnimationEnabled(true);
		    dlgWait.center();
		    dlgWait.show();
		}
		else{
			FlexTable tBody = null;
			Label lab = null;
			tBody =  (FlexTable)dlgWait.getWidget();
			lab = (Label)tBody.getWidget(0, 1);
			lab.setText(message);
			lab = (Label)tBody.getWidget(1, 0);
			lab.setText(content);
			
			dlgWait.setText(title);
			if (!dlgWait.isVisible()){
				dlgWait.center();
				dlgWait.show();
			}
		}
	}
	
	public static void waitHide(){
		if (dlgWait != null)
			dlgWait.hide();
		dlgWait = null;
	}
}
