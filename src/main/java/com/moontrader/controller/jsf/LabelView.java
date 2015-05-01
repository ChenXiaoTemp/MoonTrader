package com.moontrader.controller.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

import com.moontrader.dto.Label;
import com.moontrader.service.LabelService;
import com.moontrader.service.ServiceException;

@ManagedBean
@ViewScoped
public class LabelView implements Serializable, EventSource {
	private static final long serialVersionUID = 4623161408531289936L;
	private Logger logger = Logger.getLogger(LabelView.class);
	private List<EventListener> eventListeners = new ArrayList<EventListener>();
	private boolean newLabel;

	public boolean isNewLabel() {
		return newLabel;
	}

	public void setNewLabel(boolean newLabel) {
		this.newLabel = newLabel;
	}

	public List<Label> getAllLabels() {
		return allLabels;
	}

	public void setAllLabels(List<Label> allLabels) {
		this.allLabels = allLabels;
	}

	private String updateIds;

	public String getUpdateIds() {
		return updateIds;
	}

	public void setUpdateIds(String updateIds) {
		this.updateIds = updateIds;
	}

	private List<Label> allLabels;
	private DualListModel<Label> targetLabels = new DualListModel<Label>();

	public DualListModel<Label> getTargetLabels() {
		return targetLabels;
	}

	public void setTargetLabels(DualListModel<Label> targetLabels) {
		this.targetLabels = targetLabels;
	}

	public List<Label> getSelectedLabels() {
		List<Label> result = new ArrayList<Label>();
		List<Label> temp = this.targetLabels.getTarget();
		Map<String, Label> indexLabels = new HashMap<String, Label>();
		for (Label label : this.allLabels) {
			indexLabels.put(label.getName(), label);
		}
		Set<String> resultNames = new HashSet<String>();
		if (this.newLabel) {
			if (indexLabels.containsKey(this.label.getName())) {
				resultNames.add(this.label.getName());
				result.add(indexLabels.get(this.label.getName()));
			} else {
				result.add(this.label);
			}
		}
		for (Label label : temp) {
			if (resultNames.contains(label.getName())) {
				continue;
			}
			if (indexLabels.containsKey(label.getName())) {
				resultNames.add(label.getName());
				result.add(indexLabels.get(label.getName()));
			}
		}
		return result;
	}

	private Label label = new Label();

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	@ManagedProperty("#{labelService}")
	private LabelService labelService;
	
	private List<Label> selectedLabels=null;

	public LabelService getLabelService() {
		return labelService;
	}

	public void setLabelService(LabelService labelService) {
		this.labelService = labelService;
	}

	public void onInsertLabel() throws Exception {
		if(this.label.getName()==null||this.label.getName().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "名称不能为空！"));
			return ;
		}
		for(Label label:this.allLabels){
			if(label.getName().equals(this.label.getName())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "名称重复"));
				return;
			}
		}
		this.labelService.insert(this.label);
		this.updateLabels();
		Event event=new LabelEvent(Event.NEW_LABEL,this.label,this,this.updateIds);
		this.dispatch(event);
	}
	
	public void updateLabels() throws ServiceException{
		this.prepareLabels(selectedLabels, updateIds);
	}
	
	public void onSubmit() throws Exception{
		System.out.println("Debug");
		Event event=new LabelEvent(Event.SUBMIT,this.label,this,this.updateIds);
		this.dispatch(event);
	}

	public void prepareLabels(List<Label> selectedLabels, String updateIds)
			throws ServiceException {
		this.newLabel = false;
		this.updateIds = updateIds;
		this.label = new Label();
		this.allLabels = this.labelService.list();
		if (selectedLabels == null) {
			selectedLabels = new ArrayList<Label>();
		}
		this.selectedLabels=selectedLabels;
		this.targetLabels.setTarget(selectedLabels);
		List<Label> avaliableLabels = new ArrayList<Label>();
		Map<Integer, Label> labels = new HashMap<Integer, Label>();
		for (Label label : selectedLabels) {
			System.out.println(label.getId() + "," + label.getName());
			labels.put(label.getId(), label);
		}
		for (Label label : this.allLabels) {
			System.out.println(label.getId() + "," + label.getName());
			if (!labels.containsKey(label.getId())) {
				avaliableLabels.add(label);
			}
		}
		this.targetLabels.setSource(avaliableLabels);

	}

	@PostConstruct
	public void init() {
		try {
			allLabels = labelService.list();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispatch(Event event) throws Exception {
		for (EventListener listener : this.eventListeners) {
			if (event.getType() == listener.getType()) {
				listener.handle(event);
			}
		}
	}

	@Override
	public void addEventListener(EventListener listener) {
		for (EventListener l : this.eventListeners) {
			if (l.getId().equals(listener.getId())) {
				return;
			}
		}
		this.eventListeners.add(listener);
	}
}
