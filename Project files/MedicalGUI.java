import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class MedicalGUI extends JFrame {
    private MedicalDataMiner dataMiner;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextArea resultsArea;
    private JButton loadButton, analyzeButton, reportButton;
    private JMenuBar menuBar;
    
    public MedicalGUI() {
        dataMiner = new MedicalDataMiner();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Medical Data Mining System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        createMenuBar();
        createControlPanel();
        createDataTable();
        createResultsPanel();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void createMenuBar() {
        menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load Data");
        JMenuItem saveReportItem = new JMenuItem("Save Report");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        loadItem.addActionListener(e -> loadDataFile());
        saveReportItem.addActionListener(e -> saveReport());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(loadItem);
        fileMenu.add(saveReportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu analysisMenu = new JMenu("Analysis");
        JMenuItem statsItem = new JMenuItem("Basic Statistics");
        JMenuItem riskItem = new JMenuItem("Risk Analysis");
        JMenuItem patternItem = new JMenuItem("Find Patterns");
        JMenuItem frequencyItem = new JMenuItem("Frequency Analysis");
        JMenuItem predictItem = new JMenuItem("Predict Disease");
        
        statsItem.addActionListener(e -> showStatistics());
        riskItem.addActionListener(e -> showRiskAnalysis());
        patternItem.addActionListener(e -> showPatterns());
        frequencyItem.addActionListener(e -> showFrequency());
        predictItem.addActionListener(e -> showPrediction());
        
        analysisMenu.add(statsItem);
        analysisMenu.add(riskItem);
        analysisMenu.add(patternItem);
        analysisMenu.add(frequencyItem);
        analysisMenu.addSeparator();
        analysisMenu.add(predictItem);
        
        JMenu viewMenu = new JMenu("View");
        JMenuItem allPatientsItem = new JMenuItem("Show All Patients");
        JMenuItem highRiskItem = new JMenuItem("Show High Risk Only");
        JMenuItem clearResultsItem = new JMenuItem("Clear Results");
        
        allPatientsItem.addActionListener(e -> showAllPatients());
        highRiskItem.addActionListener(e -> showHighRiskPatients());
        clearResultsItem.addActionListener(e -> resultsArea.setText(""));
        
        viewMenu.add(allPatientsItem);
        viewMenu.add(highRiskItem);
        viewMenu.addSeparator();
        viewMenu.add(clearResultsItem);
        
        menuBar.add(fileMenu);
        menuBar.add(analysisMenu);
        menuBar.add(viewMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        
        loadButton = new JButton("Load File");
        analyzeButton = new JButton("Quick Analysis");
        reportButton = new JButton("Generate Report");
        
        loadButton.addActionListener(e -> loadDataFile());
        analyzeButton.addActionListener(e -> performQuickAnalysis());
        reportButton.addActionListener(e -> saveReport());
        
        analyzeButton.setEnabled(false);
        reportButton.setEnabled(false);
        
        controlPanel.add(loadButton);
        controlPanel.add(analyzeButton);
        controlPanel.add(reportButton);
        
        add(controlPanel, BorderLayout.NORTH);
    }
    
    private void createDataTable() {
        String[] columnNames = {"ID", "Name", "Age", "BP", "Cholesterol", "Blood Group", "Diagnosis"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        dataTable = new JTable(tableModel);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Patient Data"));
        tableScrollPane.setPreferredSize(new Dimension(950, 300));
        
        add(tableScrollPane, BorderLayout.CENTER);
    }
    
    private void createResultsPanel() {
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane resultsScrollPane = new JScrollPane(resultsArea);
        resultsScrollPane.setBorder(BorderFactory.createTitledBorder("Analysis Results"));
        resultsScrollPane.setPreferredSize(new Dimension(950, 250));
        
        add(resultsScrollPane, BorderLayout.SOUTH);
    }
    
    private void loadDataFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
            }
            public String getDescription() {
                return "CSV Files (*.csv)";
            }
        });
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (dataMiner.loadDataFromFile(selectedFile.getPath())) {
                updateTable(dataMiner.getAllPatients());
                analyzeButton.setEnabled(true);
                reportButton.setEnabled(true);
                resultsArea.setText("Data loaded successfully from: " + selectedFile.getName() + "\n");
                resultsArea.append("Total patients loaded: " + dataMiner.getAllPatients().size() + "\n");
            } else {
                JOptionPane.showMessageDialog(this, "Error loading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateTable(ArrayList<Patient> patients) {
        tableModel.setRowCount(0);
        for (Patient p : patients) {
            Object[] row = {
                p.getPatientID(),
                p.getName(),
                p.getAge(),
                p.getBloodPressure(),
                p.getCholesterol(),
                p.getBloodGroup(),
                p.getDiagnosis()
            };
            tableModel.addRow(row);
        }
    }
    
    private void performQuickAnalysis() {
        resultsArea.setText("");
        resultsArea.append(dataMiner.calculateBasicStatistics() + "\n");
        resultsArea.append(dataMiner.performRiskAnalysis() + "\n");
        resultsArea.setCaretPosition(0);
    }
    
    private void showStatistics() {
        resultsArea.setText(dataMiner.calculateBasicStatistics());
        resultsArea.setCaretPosition(0);
    }
    
    private void showRiskAnalysis() {
        resultsArea.setText(dataMiner.performRiskAnalysis());
        resultsArea.setCaretPosition(0);
    }
    
    private void showPatterns() {
        resultsArea.setText(dataMiner.findPatterns());
        resultsArea.setCaretPosition(0);
    }
    
    private void showFrequency() {
        resultsArea.setText(dataMiner.analyzeFrequency());
        resultsArea.setCaretPosition(0);
    }
    
    private void showPrediction() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField ageField = new JTextField();
        JTextField bpField = new JTextField();
        JTextField cholField = new JTextField();
        
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Blood Pressure:"));
        inputPanel.add(bpField);
        inputPanel.add(new JLabel("Cholesterol:"));
        inputPanel.add(cholField);
        
        int result = JOptionPane.showConfirmDialog(this, inputPanel, 
                "Enter Patient Values for Prediction", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int age = Integer.parseInt(ageField.getText());
                int bp = Integer.parseInt(bpField.getText());
                int chol = Integer.parseInt(cholField.getText());
                
                resultsArea.setText(dataMiner.predictDisease(age, bp, chol));
                resultsArea.setCaretPosition(0);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAllPatients() {
        updateTable(dataMiner.getAllPatients());
        resultsArea.setText("Showing all patients (" + dataMiner.getAllPatients().size() + " records)\n");
    }
    
    private void showHighRiskPatients() {
        ArrayList<Patient> highRisk = dataMiner.getHighRiskPatients();
        updateTable(highRisk);
        resultsArea.setText("Showing high risk patients only (" + highRisk.size() + " records)\n");
    }
    
    private void saveReport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setSelectedFile(new File("medical_report.txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            dataMiner.generateReport(file.getPath());
            JOptionPane.showMessageDialog(this, "Report saved successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MedicalGUI());
    }
}
