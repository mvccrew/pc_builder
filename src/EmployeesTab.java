import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EmployeesTab extends Tab {
	JLabel nameLabel = new JLabel("Name: ");
	JLabel departmentLabel = new JLabel("Department: ");
	JLabel computerLabel = new JLabel("PC: ");
	
    JTextField nameTField = new JTextField();
    String[] departmentContent = {"","Production","Marketing","HR Management", "Accounting and Finance", "Research and Development"};
    JComboBox<String> departmentCombo = new JComboBox<>(departmentContent);
    
    JComboBox<String> computersCombo = new JComboBox<>();
	
	
	public EmployeesTab() {
		updateCombos();
		
		upPanel.setLayout(new GridLayout(4, 2));
        upPanel.add(nameLabel);
        upPanel.add(nameTField);
        upPanel.add(departmentLabel);
        upPanel.add(departmentCombo);
        upPanel.add(computerLabel);
        upPanel.add(computersCombo);
        
        updateTable();
        scroller.setPreferredSize(new Dimension(850, 100));
	}
	
	private ArrayList<String> getComputers(){
		ArrayList<String> computers = new ArrayList<>();
        String sql = "select name from computers";
        PreparedStatement state = null;
        Connection conn = DBHelper.getConnection();

        try {
            state = conn.prepareStatement(sql);
            ResultSet result = state.executeQuery();

            while (result.next()) {
                String compName = result.getString("name");
                computers.add(compName);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                state.close();
                conn.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return computers;
	}

	public void updateCombos() {
        computersCombo.removeAllItems();
        for (String item : getComputers()) {
            computersCombo.addItem(item);
        }
    }

	public void updateTable() {
        table.setModel(DBHelper.getEmpModel());
        table.getColumnModel().getColumn(3).setHeaderValue("COMPUTER");
        hideIdColumn();
    }

	@Override
	void clearForm() {
		nameTField.setText("");
		departmentCombo.setSelectedIndex(0);
		computersCombo.setSelectedIndex(0);
	}

}
