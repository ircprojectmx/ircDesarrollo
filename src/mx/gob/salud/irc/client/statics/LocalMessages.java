package mx.gob.salud.irc.client.statics;

import com.google.gwt.i18n.client.Messages;

public interface LocalMessages extends Messages {

	String okProc();
	String error();
	String errorNetWork();
	String errorDB();
	String errorSecurityLogin();
	String errorSecurityPrivileges();
	String errorReadingHashTable(String proc);
	String errorRequiredField(String field);
	String errorNotExistData(String data);
	String errorBuilding(String data);
	String errorRuningLocalProc(String proc);
	String errorRuningRemoteProc(String proc);
	
	String userErrorTitle();
	
	String appName();
	String appCode();
	String appNameLongName();
	
	/* Mensajes del Módulo de Seguridad */
	String secLoginRequest();
	String secLabelUser();
	String secLabelPassword();
	String secUrlExitSystem();
	String secEnterSystem();
	String secValidate();
	String secCheking();
	
	/* Estilos */
	String styleTitle();
	String styleTitleBold();
	String styleTitleLight();
	String styleTitlePanel();
	String styleSubTitlePanel();
	String styleWaitMsg();
	String styleWaitContent();
	String styleOkMsg();
	String styleErrorMsg();
	String styleErrorContent();
	String styleMenu();
	String styleFormTitle();
	String styleFormSubTitle();
	String styleFormHeader();
	String styleFormSubHeader();
	String styleFormTable();
	String styleFormTableOff();
	String styleFormTableCellPad();
	String styleFormTableCellSpa();
	String styleFormLabel();
	String styleFormData();
	String styleFormError();
	String stylePrompt();
	
	
	/* Botones */
	String buttonOk();
	String buttonCancel();
	String buttonYes();
	String buttonNo();
	String buttonEnter();
	String buttonClose();
	String buttonExit();
	
	/* Form Component */
	String formTitle();
	
	/* Wait Messages */
	String loadingData(String data);
	String readingDataBase();
	String runningProc(String proc);
	String validating();
	String generatingData();
	String sending();

	/* Main Menu */
	String menFicha();
	String menConsulta();
	String menLaboratorio();
	String menGabinete();
	String menCitas();
	String menCx();
	String menInterConsulta();
	String menAlta();
	String menSubsecuente();
	String menTamizaje();
	String menUs();
	String menCistou();
	String menGamag();
	String menTAC();
	String menCistosiopia();
	
	/* Ficha Alta */
	String fhaTitle();
	String fhaSubTitle();
}
