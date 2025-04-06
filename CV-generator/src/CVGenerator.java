import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CVGenerator {
    public static void main(String[] args) {
        new CVForm();
    }
}

class CVForm {
    private JFrame frame;
    private JTextField nameField, phoneField, emailField, addressField;
    private JTextField companyField, refNameField, refContactField, skillsField;
    private JTable educationTable;

    public CVForm() {
        frame = new JFrame("Resume/CV Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#B5B4D9"));

        // Personal Details
        JPanel personalPanel = createTitledPanel("Personal Details", 30, 20, 920, 130);
        frame.add(personalPanel);
        nameField = addLabeledTextField(personalPanel, "Name:", 20, 30);
        phoneField = addLabeledTextField(personalPanel, "Phone:", 470, 30);
        emailField = addLabeledTextField(personalPanel, "Email:", 20, 80);
        addressField = addLabeledTextField(personalPanel, "Address:", 470, 80);

        // Educational Details  (Table)
        JPanel educationPanel = createTitledPanel("Educational Details", 30, 160, 920, 180);
        frame.add(educationPanel);
        String[] columnNames = {"Degree", "Board", "Institute", "Pass. Year", "Grade"};
        Object[][] rowData = {
            {"S.S.C", "", "", "", ""},
            {"H.S.C", "", "", "", ""},
            {"B.Sc", "", "", "", ""},
            {"M.Sc", "", "", "", ""}
        };
        educationTable = new JTable(rowData, columnNames);
        educationTable.setGridColor(Color.BLACK);
        educationTable.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(educationTable);
        scrollPane.setBounds(20, 45, 870, 100);
        educationPanel.add(scrollPane);

        // Job Experience 
        JPanel jobPanel = createTitledPanel("Job Experience & Skills", 30, 360, 920, 120);
        frame.add(jobPanel);
        companyField = addLabeledTextField(jobPanel, "Company:", 20, 45);
        skillsField = addLabeledTextField(jobPanel, "Skills:", 470, 45);

        // Reference 
        JPanel referencePanel = createTitledPanel("Reference", 30, 500, 920, 120);
        frame.add(referencePanel);
        refNameField = addLabeledTextField(referencePanel, "Name:", 20, 45);
        refContactField = addLabeledTextField(referencePanel, "Details:", 470,45);

        // Generate Button
        JButton generateButton = new JButton("Generate");
        generateButton.setBounds(380, 620, 150, 40);
        generateButton.setBackground(Color.decode("#90EE90"));
        generateButton.setForeground(Color.BLACK);
        frame.add(generateButton);

        generateButton.addActionListener(e -> {
            frame.dispose();
            new CVPreview(
                    nameField.getText(), phoneField.getText(), emailField.getText(), addressField.getText(),
                    educationTable.getModel(),
                    companyField.getText(), skillsField.getText(), refNameField.getText(), refContactField.getText()
            );
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private JPanel createTitledPanel(String title, int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(x, y, width, height);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title));
        panel.setBackground(new Color(153, 153, 153));
        return panel;
    }

    private JTextField addLabeledTextField(JPanel panel, String label, int x, int y) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(x, y, 80, 30);
        jLabel.setForeground(Color.BLACK);
        panel.add(jLabel);

        JTextField textField = new JTextField();
       textField.setBounds(x + 70, y, 300, 35);
       //textField.setBounds(x + 80, y, 300, 45);
        panel.add(textField);

        return textField;
    }
}

class CVPreview {
    private JFrame frame;

    public CVPreview(String name, String phone, String email, String address,
                     TableModel educationModel,
                     String company, String skills, String refName, String refContact) {

        frame = new JFrame("CV Preview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#9CD3D9"));

        // Personal Details 
        JPanel personalPanel = createTitledPanel("Personal Details", 30, 20, 920, 130);
        frame.add(personalPanel);
        addTextLabel(personalPanel, "Name: " + name, 20, 30);
        addTextLabel(personalPanel, "Phone: " + phone, 470, 30);
        addTextLabel(personalPanel, "Email: " + email, 20, 80);
        addTextLabel(personalPanel, "Address: " + address, 470, 80);

        // Educational Details  (Table)
        JPanel educationPanel = createTitledPanel("Educational Details", 30, 160, 920, 180);
        frame.add(educationPanel);
        JTable educationTable = new JTable(educationModel);
        educationTable.setEnabled(false);
        educationTable.setGridColor(Color.BLACK);
        educationTable.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(educationTable);
        scrollPane.setBounds(20, 45, 870, 100);
        educationPanel.add(scrollPane);

        // Job Experience 
        JPanel jobPanel = createTitledPanel("Job Experience & Skills", 30, 360, 920, 120);
        frame.add(jobPanel);
        addTextLabel(jobPanel, "Company: " + company, 20, 30);
        addTextLabel(jobPanel, "Skills: " + skills, 470, 30);

        // Reference 
        JPanel referencePanel = createTitledPanel("Reference", 30, 500, 920, 120);
        frame.add(referencePanel);
        addTextLabel(referencePanel, "Name: " + refName, 20, 30);
        addTextLabel(referencePanel, "Details: " + refContact, 470, 30);

        // Save to TXT Button
        JButton saveButton = new JButton("Download ");
        saveButton.setBounds(380, 620, 150, 40);
        saveButton.setBackground(Color.decode("#FF83F4"));
        saveButton.setForeground(Color.BLACK);
        saveButton.addActionListener(e -> saveToFile(name, phone, email, address, educationModel, company, skills, refName, refContact));
        frame.add(saveButton);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private JPanel createTitledPanel(String title, int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(x, y, width, height);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title));
        return panel;
    }

    private void addTextLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 400, 30);
        label.setForeground(Color.BLACK);
        panel.add(label);
    }

    private void saveToFile(String name, String phone, String email, String address,
                        TableModel educationModel,
                        String company, String skills, String refName, String refContact) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save CV");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
    int userSelection = fileChooser.showSaveDialog(frame);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getAbsolutePath().endsWith(".txt")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
            writer.write("========================================\n");
            writer.write("            CURRICULUM VITAE           \n");
            writer.write("========================================\n\n");
            writer.write("Personal Details:\n");
            writer.write("========================================\n");
            writer.write("Name: " + name + "\n");
            writer.write("Phone: " + phone + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Address: " + address + "\n\n");
            
            writer.write("Educational Details:\n");
            writer.write("========================================\n");
            for (int i = 0; i < educationModel.getRowCount(); i++) {
                for (int j = 0; j < educationModel.getColumnCount(); j++) {
                    writer.write(educationModel.getValueAt(i, j) + "\t");
                }
                writer.write("\n");
            }
            writer.write("\n");
            
            writer.write("Job Experience:\n");
            writer.write("========================================\n");
            writer.write("Company: " + company + "\n");
            writer.write("Skills: " + skills + "\n\n");
            
            writer.write("Reference:\n");
            writer.write("========================================\n");
            writer.write("Name: " + refName + "\n");
            writer.write("Details: " + refContact + "\n");
            writer.write("========================================\n");

            JOptionPane.showMessageDialog(frame, "CV saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

   /*  private void saveToFile(String name, String phone, String email, String address,
                            TableModel educationModel,
                            String company, String skills, String refName, String refContact) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
        int userSelection = fileChooser.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write("Curriculum Vitae\n\n");
                writer.write("Name: " + name + "\nPhone: " + phone + "\nEmail: " + email + "\nAddress: " + address + "\n\n");
                writer.write("Educational Details:\n");
                for (int i = 0; i < educationModel.getRowCount(); i++) {
                    for (int j = 0; j < educationModel.getColumnCount(); j++) {
                        writer.write(educationModel.getValueAt(i, j) + "\t");
                    }
                    writer.write("\n");
                }
                writer.write("\nCompany: " + company + "\nSkills: " + skills + "\n\nReference: " + refName + "\nDetails: " + refContact + "\n");
                JOptionPane.showMessageDialog(frame, "CV saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } */
}
