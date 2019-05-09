import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;
import java.util.HashMap;

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

    HashMap<Integer, String> cpuParts = getParts("CPU");
    HashMap<Integer, String> gpuParts = getParts("GPU");
    HashMap<Integer, String> ramParts = getParts("RAM");
    HashMap<Integer, String> hddParts = getParts("HDD");
    HashMap<Integer, String> coolingParts = getParts("Cooling");

    public ComputersTab() {
        cpuCombo = new JComboBox<>(cpuParts.values().toArray(new String[0]));
        gpuCombo = new JComboBox<>(gpuParts.values().toArray(new String[0]));
        ramCombo = new JComboBox<>(ramParts.values().toArray(new String[0]));
        hddCombo = new JComboBox<>(hddParts.values().toArray(new String[0]));
        coolingCombo = new JComboBox<>(coolingParts.values().toArray(new String[0]));

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
        table.setModel(DBHelper.getAllModel("computers"));
    }

    private HashMap<Integer, String> getParts(String part) {

        HashMap<Integer, String> parts = new HashMap<>();
        String sql = "select id, name from parts where type = ?";
        PreparedStatement state = null;
        Connection conn = DBHelper.getConnection();

        try {
            state = conn.prepareStatement(sql);
            state.setString(1, part);
            ResultSet result = state.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String partName = result.getString("name");
                parts.put(id, partName);
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