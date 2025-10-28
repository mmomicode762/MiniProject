
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Vector;

    public class StudentManagementFileGUI extends JFrame {
        private JTextField idField, nameField, ageField, deptField;
        private DefaultTableModel tableModel;
        private JTable table;
        private static final String FILE_NAME = "students.txt";

        public StudentManagementFileGUI() {
            setTitle("🎓 Student Management System");
            setSize(700, 500);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10, 10));

            // 🧠 Top panel for inputs
            JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
            inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Student"));

            idField = new JTextField();
            nameField = new JTextField();
            ageField = new JTextField();
            deptField = new JTextField();

            inputPanel.add(new JLabel("Student ID:"));
            inputPanel.add(idField);
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Age:"));
            inputPanel.add(ageField);
            inputPanel.add(new JLabel("Department:"));
            inputPanel.add(deptField);

            // 🧱 Table setup
            tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Department"}, 0);
            table = new JTable(tableModel);
            JScrollPane tableScroll = new JScrollPane(table);

            // 🎮 Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton addButton = new JButton("Add Student");
            JButton deleteButton = new JButton("Delete Selected");
            JButton clearButton = new JButton("Clear Fields");
            JButton saveButton = new JButton("Save Now");
            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(clearButton);
            buttonPanel.add(saveButton);

            // 🎨 Add to Frame
            add(inputPanel, BorderLayout.NORTH);
            add(tableScroll, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            // ⚙️ Button Actions
            addButton.addActionListener(this::addStudent);
            deleteButton.addActionListener(this::deleteStudent);
            clearButton.addActionListener(e -> clearFields());
            saveButton.addActionListener(e -> saveToFile());

            // 🔄 Load data on startup
            loadFromFile();
        }

        // ➕ Add student
        private void addStudent(ActionEvent e) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String dept = deptField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || age.isEmpty() || dept.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vector<String> row = new Vector<>();
            row.add(id);
            row.add(name);
            row.add(age);
            row.add(dept);
            tableModel.addRow(row);
            clearFields();
            saveToFile();
            JOptionPane.showMessageDialog(this, "✅ Student added & saved successfully!");
        }

        // 🗑️ Delete student
        private void deleteStudent(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                saveToFile();
                JOptionPane.showMessageDialog(this, "🗑️ Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Please select a row to delete!");
            }
        }

        // 💾 Save all table data to file
        private void saveToFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String id = tableModel.getValueAt(i, 0).toString();
                    String name = tableModel.getValueAt(i, 1).toString();
                    String age = tableModel.getValueAt(i, 2).toString();
                    String dept = tableModel.getValueAt(i, 3).toString();
                    writer.write(id + "," + name + "," + age + "," + dept);
                    writer.newLine();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error saving file: " + ex.getMessage());
            }
        }

        // 📂 Load data from file
        private void loadFromFile() {
            File file = new File(FILE_NAME);
            if (!file.exists()) return; // no file yet

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    tableModel.addRow(parts);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error loading file: " + ex.getMessage());
            }
        }

        // 🧹 Clear input fields
        private void clearFields() {
            idField.setText("");
            nameField.setText("");
            ageField.setText("");
            deptField.setText("");
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                StudentManagementFileGUI frame = new StudentManagementFileGUI();
                frame.setVisible(true);
            });
        }
    }


