package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	// implementaçao dos botoes do
	@FXML
	private TextField txtId; 
	
	@FXML
	private TextField txtName; 
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave; 
	
	@FXML
	private Button btCancel; 
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void subscribleDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	// abaixo métodos para tratar dos botoes
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
		entity = getFormData();
		service.saveOrUpdate(entity); // salvar no banco de dados
		notifyDataChangeListeners();
		Utils.currentStage(event).close(); // fechar a janela 
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close(); // fechar a janela
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	//limitando as caixas de id e nome. Id somente inteiros e texto com maximo de 30 caracteres
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updatFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
}
