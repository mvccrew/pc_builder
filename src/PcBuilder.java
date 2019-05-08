import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

public class PcBuilder extends JFrame{
	
	Connection conn = null;
	PreparedStatement state = null;
	int id = -1;

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	PartsTab parts = new PartsTab();
	ComputersTab computers = new ComputersTab();

	public PcBuilder() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 1000);

		tabbedPane.add("Parts", getTabContent(parts));
		tabbedPane.add("Computers", getTabContent(computers));
		this.add(tabbedPane);

		parts.addBtn.addActionListener(new AddAction());
		parts.delBtn.addActionListener(new DeleteAction());
		parts.table.addMouseListener(new MouseTableAction());
		parts.searchBtn.addActionListener(new SearchAction());

	}//end constructor

	private static JPanel getTabContent(Tab tab) {
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new GridLayout(4, 1));
		containerPanel.setPreferredSize(new Dimension(1000, 1000));

		for (JPanel panel : tab.getPanels()) {
			containerPanel.add(panel);
		}

		return containerPanel;
	}

	class SearchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String search = parts.searchTField.getText();
			parts.table.setModel(DBHelper.getFilteredByName(search, "parts"));
			parts.clearForm();
		}
	}
	
	class MouseTableAction implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = parts.table.getSelectedRow();
			id = Integer.parseInt(parts.table.getValueAt(row, 0).toString());
			if(e.getClickCount() > 1) {
				parts.nameTField.setText(parts.table.getValueAt(row, 1).toString());
				String type = parts.table.getValueAt(row, 5).toString();
				parts.typeCombo.setSelectedItem(type);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class DeleteAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String sql = "delete from parts where id=?";
			conn = DBHelper.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				id = -1;
				parts.table.setModel(DBHelper.getAllModel("parts"));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
	}
	
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = parts.nameTField.getText();
			int price = Integer.parseInt(parts.priceTField.getText());
			String type = parts.typeCombo.getSelectedItem().toString();
			String sql = "insert into parts values (null,?,?,?);";
			
			conn = DBHelper.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, name);
				state.setInt(2, price);
				state.setString(3, type);
				state.execute();
				parts.table.setModel(DBHelper.getAllModel("parts"));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			parts.clearForm();
			
		}
		
	}

}//end class PcBuilder
