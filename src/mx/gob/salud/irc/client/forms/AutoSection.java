package mx.gob.salud.irc.client.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.forms.fields.PushButtonField;
import mx.gob.salud.irc.client.utils.UserMessage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AutoSection extends VerticalPanel {

	public static final String SECTION = "SEC_";
	public static final String COUNTER_FOR = "COUNTER_FOR";
	private String title = null;
	
	private PushButtonField add = null;
	private SectionControl mainSection = null;
	private HashMap<String,VerticalPanel> sectionsPanel = null;
	private HashMap<String,SectionControl> sections = null;
	
	public void setSection(SectionControl sec){
		mainSection = sec;
		title = sec.getSectionDescription().getDescription();
		sectionsPanel = new HashMap<String,VerticalPanel>();
		sections = new HashMap<String,SectionControl>();
		HorizontalPanel header = new HorizontalPanel();
		header.setStyleName(Resources.msg.styleFormSubHeader());
		Label lab = new Label(title);
		lab.setStyleName(Resources.msg.styleFormSubHeader());
		header.add(lab);
		add = new PushButtonField(new Image(GWT.getModuleBaseURL()+"images/insert.png"));
		add.setCmd(new Command(){
			public void execute() {
				addSection();
			}});
		add.setWidth("24px");
		header.add(add);
		header.setCellHorizontalAlignment(lab, HasHorizontalAlignment.ALIGN_LEFT);
		header.setCellVerticalAlignment(lab, HasVerticalAlignment.ALIGN_MIDDLE);
		header.setCellHorizontalAlignment(add, HasHorizontalAlignment.ALIGN_RIGHT);
		
		header.setWidth("100%");
		//setWidth("100%");
		add(header);
	}
	
	public void addSection(){
		SectionControl newSec = new SectionControl();
		VerticalPanel vP = null;
		HorizontalPanel hP = new HorizontalPanel();
		String idSec = null;
		//PushButtonField addBtn = new PushButtonField(new Image(GWT.getModuleBaseURL()+"images/insert.png"));
		final PushButtonField delBtn = new PushButtonField(new Image(GWT.getModuleBaseURL()+"images/error00.png"));
		
		SectionDescription tmp = mainSection.getSectionDescription();
		tmp.setTypeVisualControl(VisualControl.VC_PNL_VERT);
		newSec.generateFromDescription(tmp);
		vP = (VerticalPanel) newSec.getVisualControl().getControl();
		
		if (getWidgetCount() % 2 > 0){
			vP.setStyleName(Resources.msg.styleFormTable());
			hP.setStyleName(Resources.msg.styleFormTable());
		}
		else{
			vP.setStyleName(Resources.msg.styleFormTableOff());
			hP.setStyleName(Resources.msg.styleFormTableOff());
		}
		vP.setWidth("100%");
		
		idSec = AutoSection.SECTION + getWidgetCount();
		//addBtn.setInternalId(idSec);
		delBtn.setInternalId(idSec);
		
		/*addBtn.setCmd(new Command(){
			@Override
			public void execute() {
				addSection();
			}});*/
		delBtn.setCmd(new Command(){
			public void execute() {
				delSection(delBtn.getInternalId());
			}});
		
		//hP.add(addBtn);
		hP.add(delBtn);
		
		vP.insert(hP, 0);
		//vP.add(hP);
		vP.setCellHorizontalAlignment(hP, HasHorizontalAlignment.ALIGN_RIGHT);
		//vP.add(newSec.getBody());
		
		sectionsPanel.put(idSec, vP);
		sections.put(idSec, newSec);
		
		add(vP);
		UserMessage.waitHide();
	}
	
	public void delSection(String idSection){
		VerticalPanel vp = sectionsPanel.get(idSection);
		remove(vp);
		sectionsPanel.remove(idSection);
		sections.remove(idSection);
	}
	
	public ArrayList<SectionControl> getSections(){
		ArrayList<SectionControl> value = new ArrayList<SectionControl>();
		Iterator iterator = null;
		String key = null;
		
		iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value.add(sections.get(key));
        }
		return (value);
	}
	
	public void generateSections(){
		int index = 0, size = 0;
		ArrayList<SectionControl> list = null;
		
		list = getSections();
		size = list.size();
		mainSection.clearSubSections();
		for (index = 0; index < size; index++){
			mainSection.addSubSection(list.get(index));
		}
		mainSection.getSection().setEmpty(true);
	}
	
	public void generateCounter(){
		int index = 0, size = mainSection.getNumFields(), pos = -1;
		String name = null;
		
		//Buscando campo con identificador automatico
		for (index = 0; index < size; index++){
			name = mainSection.getSectionDescription().getFieldDescription(index).getStaticValues();
			if (name.equals(AutoSection.COUNTER_FOR)){
				pos = index;
				break;
			}
		}
		
		if (pos > -1){
			for (index = 0; index < size; index++){
				mainSection.getSection().getField(pos).setValue(""+index);
			}
		}
	}
	
	public void setEnabled(boolean enabled){
		add.setEnabled(enabled);
		Iterator iterator = null;
		String key = null;
		
		iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            sections.get(key).setEnabled(enabled);
        }
	}
}
