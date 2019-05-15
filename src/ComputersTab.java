import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;

public class ComputersTab extends Tab {
    JLabel nameLabel = new JLabel("Name:");
    JLabel cpuLabel = new JLabel("CPU:");
    JLabel gpuLabel = new JLabel("GPU:");
    JLabel ramLabel = new JLabel("RAM:");
    JLabel hddLabel = new JLabel("HDD:");
    JLabel coolingLabel = new JLabel("Cooling:");
    JLabel typeLabel = new JLabel("Type:");
    JLabel priceLabel = new JLabel("Price:");

    JTextField nameTField = new JTextField();
    JTextField priceTField = new JTextField();
    String[] typeContent = {"","Gaming PC","2 in 1","Super computer"};
    JComboBox<String> typeCombo = new JComboBox<>(typeContent);
    JComboBox<String> cpuCombo;
    JComboBox<String> gpuCombo;
    JComboBox<String> ramCombo;
    JComboBox<String> hddCombo;
    JComboBox<String> coolingCombo;

    public ComputersTab() {
        cpuCombo = new JComboBox<>(getParts("CPU").toArray(new String[0]));
        gpuCombo = new JComboBox<>(getParts("GPU").toArray(new String[0]));
        ramCombo = new JComboBox<>(getParts("RAM").toArray(new String[0]));
        hddCombo = new JComboBox<>(getParts("HDD").toArray(new String[0]));
        coolingCombo = new JComboBox<>(getParts("Cooling").toArray(new String[0]));

        priceTField.setEditable(false);
        upPanel.setLayout(new GridLayout(8, 2));
        upPanel.add(nameLabel);
        upPanel.add(nameTField);
        upPanel.add(cpuLabel);
        upPanel.add(cpuCombo);
        upPanel.add(gpuLabel);
        upPanel.add(gpuCombo);
        upPanel.add(ramLabel);
        upPanel.add(ramCombo);
        upPanel.add(hddLabel);
        upPanel.add(hddCombo);
        upPanel.add(coolingLabel);
        upPanel.add(coolingCombo);
        upPanel.add(typeLabel);
        upPanel.add(typeCombo);
        upPanel.add(priceLabel);
        upPanel.add(priceTField);
        table.setModel(DBHelper.getCompModel());
        table.getColumnModel().getColumn(4).setHeaderValue("CPU");
        table.getColumnModel().getColumn(5).setHeaderValue("GPU");
        table.getColumnModel().getColumn(6).setHeaderValue("RAM");
        table.getColumnModel().getColumn(7).setHeaderValue("HDD");
        table.getColumnModel().getColumn(8).setHeaderValue("COOLING");
        scroller.setPreferredSize(new Dimension(850, 100));
    }

    public int getCompId(String name) {
    	int id = -1;
        String sql = "select id from computers where name=?";
        PreparedStatement state = null;
        Connection conn = DBHelper.getConnection();
        try {
            state = conn.prepareStatement(sql);
            state.setString(1, name);
            ResultSet result = state.executeQuery();

            while (result.next()) {
                id = result.getInt("id");
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
        return id;
    }
    
    private ArrayList<String> getParts(String part) {

        ArrayList<String> parts = new ArrayList<>();
        String sql = "select name from parts where type = ?";
        PreparedStatement state = null;
        Connection conn = DBHelper.getConnection();

        try {
            state = conn.prepareStatement(sql);
            state.setString(1, part);
            ResultSet result = state.executeQuery();

            while (result.next()) {
                String partName = result.getString("name");
                parts.add(partName);
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
        return parts;
    }

    public double getTotalPrice(int... partIds) {
        double price = 0;
        String sql = "select price from parts where id in (?, ?, ?, ?, ?)";
        PreparedStatement state = null;
        Connection conn = DBHelper.getConnection();

        try {
            state = conn.prepareStatement(sql);
            int index = 1;
            for (int id : partIds) {
                state.setInt(index, id);
                index++;
            }
            ResultSet result = state.executeQuery();

            while (result.next()) {
                double itemPrice = result.getDouble("price");
                price += itemPrice;
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
        return price;
    }

    public void clearForm() {
        nameTField.setText("");
        priceTField.setText("");
        cpuCombo.setSelectedIndex(0);
        gpuCombo.setSelectedIndex(0);
        ramCombo.setSelectedIndex(0);
        hddCombo.setSelectedIndex(0);
        coolingCombo.setSelectedIndex(0);
        typeCombo.setSelectedIndex(0);
    }
}