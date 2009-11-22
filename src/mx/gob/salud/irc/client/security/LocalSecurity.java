package mx.gob.salud.irc.client.security;

import java.util.HashMap;
import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.services.CommonCallBack;
import mx.gob.salud.irc.client.services.remote.Security;
import mx.gob.salud.irc.client.services.remote.SecurityAsync;
import mx.gob.salud.irc.client.statics.Codes;
import mx.gob.salud.irc.client.utils.UserMessage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class LocalSecurity {
	
	private String user = null;
	private String rol = null;
	private String paterno = null;
	private String materno = null;
	private String nombre = null;
	private String telefono = null;
	private String email = null;
	private String fhResetPwd = null;
	private String fhAlta = null;
	private String status = null; 
	

	public void userLogin(){
		DialogBox dialogBox = createDialogBox();
		dialogBox.center();
        dialogBox.show();
	}
	
	private DialogBox createDialogBox() {
	    // Create a dialog box and set the caption text
	    final DialogBox dialogBox = new DialogBox();
	    dialogBox.ensureDebugId("cwDialogBox");
	    dialogBox.setText(Resources.msg.appName());

	    // Create a table to layout the content
	    HorizontalPanel dialogContents = new HorizontalPanel();
	    //dialogContents.setSpacing(4);
	    dialogBox.setWidget(dialogContents);
	    
	    // Add some text to the top of the dialog
	    Image logo = new Image(GWT.getModuleBaseURL()+"images/FIRMA.JPG");
	    dialogContents.add(logo);
	    //dialogContents.setCellHorizontalAlignment(details,HasHorizontalAlignment.ALIGN_CENTER);

	    FlexTable dialogBody = new FlexTable();
	    //dialogContents.setSpacing(4);
	    dialogContents.setStyleName(Resources.msg.styleTitleLight());
	    dialogBody.addStyleName(Resources.msg.styleTitleLight());
	    dialogContents.add(dialogBody);
	    
	    
	    final TextBox userTx = new TextBox();
	    final PasswordTextBox pwdTx = new PasswordTextBox();
	    userTx.setText("sistemas");
	    pwdTx.setText("sistemas");
	    //Image top = new Image(GWT.getModuleBaseURL()+"images/TOP.GIF");
	    //dialogBody.setWidget(0, 0, top);
	    //Label labelTitle = new Label(Resources.msg.secLoginRequest());
	    //labelTitle.setStylePrimaryName(Resources.msg.styleTitleBold());
	    dialogBody.setWidget(0, 0, new Label(Resources.msg.secLoginRequest()));
	    dialogBody.setWidget(1, 0, new Label(Resources.msg.secLabelUser()));
	    dialogBody.setWidget(1, 1, userTx);
	    dialogBody.setWidget(2, 0, new Label(Resources.msg.secLabelPassword()));
	    dialogBody.setWidget(2, 1, pwdTx);
	    
	    dialogBody.getFlexCellFormatter().setColSpan(0, 0, 2);
	    dialogBody.getFlexCellFormatter().addStyleName(0, 0, Resources.msg.styleTitleBold());
	    //dialogBody.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
	    

	    // Add an image to the dialog
	    //Image image = Showcase.images.jimmy().createImage();
	    
	    Button enterButton = new Button(Resources.msg.buttonEnter(),
		        new ClickHandler() {
		          public void onClick(ClickEvent event) {
		        	  if (userTx.getText().trim().length() <= 0)
		        		  UserMessage.warning(Resources.msg.secEnterSystem(), Resources.msg.errorRequiredField(Resources.msg.secLabelUser()), null);
		        	  else if (pwdTx.getText().trim().length() <= 0)
		        		  UserMessage.warning(Resources.msg.secEnterSystem(), Resources.msg.errorRequiredField(Resources.msg.secLabelPassword()), null);
		        	  else
			            login(userTx.getText(), pwdTx.getText(),new CommonCallBack() {
							public void onFailure() {
								UserMessage.error(Resources.msg.userErrorTitle(), Resources.msg.secEnterSystem(), getFullMessage(), null);
							}
							public void onSuccess() {
								dialogBox.hide();
								Resources.initApplication();
							}
							});
					}
	              }
		        );
		    dialogBody.setWidget(4,0,enterButton);
	    
	    // Add a close button at the bottom of the dialog
	    Button closeButton = new Button(Resources.msg.buttonExit(),
	        new ClickHandler() {
	          public void onClick(ClickEvent event) {
	        	  Window.open(Resources.msg.secUrlExitSystem(), "_self", ""); 
	          }
	        });
	    dialogBody.setWidget(4,1,closeButton);
	    dialogBody.getCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
	    
	    
	    /*if (LocaleInfo.getCurrentLocale().isRTL()) {
	      dialogContents.setCellHorizontalAlignment(closeButton,
	          HasHorizontalAlignment.ALIGN_LEFT);

	    } else {
	      dialogContents.setCellHorizontalAlignment(closeButton,
	          HasHorizontalAlignment.ALIGN_RIGHT);
	    }*/

	    dialogBox.setAnimationEnabled(true);
	    
	    // Return the dialog box
	    return dialogBox;
	  }


	
	public void login(String usr, String password, final CommonCallBack ccb) {
		SecurityAsync sec = GWT.create(Security.class);
		UserMessage.wait(Resources.msg.secEnterSystem(), Resources.msg.secValidate(), Resources.msg.secCheking());
		
		//ID_ROL, CD_LOGIN, NB_PATERNO, NB_MATERNO, NB_NOMBRE, CD_TELEFONO, CD_EMAIL, FH_RESET_PWD, FH_ALTA, CD_ESTATUS
		sec.login(usr, password,
				new AsyncCallback<HashMap<String,String>>() {
					public void onFailure(Throwable caught) {
						ccb.setCode(Codes.ERROR_NETWORK);
						ccb.setMessage(Resources.msg.errorNetWork());
						ccb.setDetail(caught.getMessage());
						ccb.setMethodName("login");
						ccb.onFailure();
						UserMessage.waitHide();
					}
					public void onSuccess(HashMap<String, String> result) {
						if (result == null){
							ccb.setCode(Codes.ERROR_SECURITY_LOGIN);
							ccb.setMessage(Resources.msg.errorSecurityLogin());
							ccb.setMethodName("login");
							ccb.onFailure();
						}
						else{
							try{
								rol = result.get("id_rol");
								user = result.get("cd_login");
								paterno = result.get("nb_paterno");
								materno = result.get("nb_materno");
								nombre = result.get("nb_nombre");
								telefono = result.get("cd_telefono");
								email = result.get("cd_email");
								fhResetPwd = result.get("fh_reset_pwd");
								fhAlta = result.get("fh_alta");
								status = result.get("cd_estatus");
								
								ccb.onSuccess();
							}catch(Exception e){
								ccb.setCode(Codes.ERROR);
								ccb.setMessage(Resources.msg.error());
								ccb.setDetail(Resources.msg.errorReadingHashTable("Reading Response Data")+" - "+e.getMessage());
								ccb.setMethodName("login");
								ccb.onFailure();
							}
						}
						UserMessage.waitHide();
					}
				});
	}

	public String getUser() {
		return user;
	}

	public String getRol() {
		return rol;
	}

	public String getPaterno() {
		return paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}

	public String getFhResetPwd() {
		return fhResetPwd;
	}

	public String getFhAlta() {
		return fhAlta;
	}

	public String getStatus() {
		return status;
	}
}
