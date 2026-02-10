# 🏥 Healthcare Data Mining & Risk Analysis System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-478CBF?style=for-the-badge&logo=swing&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

(https://github.com/isthis-rishi/Healthcare-Data-Mining-Risk-Analysis-System/blob/main/Project%20files/image.png)

A comprehensive Java desktop application for analyzing medical patient data, performing risk assessment, and generating health insights through interactive data mining techniques.

## ✨ Features

- **📊 Data Management**: Load and parse patient data from CSV files
- **⚡ Risk Analysis**: Classify patients as Low/Medium/High risk using scoring algorithms
- **🔍 Pattern Discovery**: Identify correlations between health conditions
- **📈 Predictive Analytics**: Disease prediction based on vital parameters
- **🖥️ User-Friendly GUI**: Intuitive Swing-based interface with real-time analysis
- **📄 Report Generation**: Export comprehensive analysis reports

## 🚀 Quick Start

### Prerequisites
- Java Development Kit (JDK) 8 or higher

### Installation
1. Clone the repository:
```bash
git clone https://github.com/yourusername/healthcare-data-miner.git
cd healthcare-data-miner
```

2. Compile the Java files:
```bash
javac *.java
```

3. Run the application:
```bash
java MedicalGUI
```

## 📁 Project Structure

```
├── MedicalDataMiner.java    # Core data processing and analysis logic
├── MedicalGUI.java          # Swing-based user interface
├── Patient.java             # Patient data model class
└── patient_data.csv         # Sample dataset (50 patients)
```

## 📊 Data Format

The application expects CSV files with the following format:
```csv
ID,Name,Age,BP,Cholesterol,BloodGroup,Diagnosis
101,Rajesh Kumar,45,125,175,O+,Healthy
102,Priya Sharma,52,145,210,A+,Hypertension
```

## 🎯 Usage

### Loading Data
1. Launch the application
2. Click "Load File" or use File → Load Data
3. Select a CSV file with patient data

### Performing Analysis
- **Basic Statistics**: Overview of patient metrics
- **Risk Analysis**: Patient risk classification
- **Find Patterns**: Discover correlations in data
- **Frequency Analysis**: Distribution analysis
- **Predict Disease**: Individual risk assessment

### Generating Reports
Use "Generate Report" to save a comprehensive analysis including all statistics and findings.

## 🔧 Risk Scoring Algorithm

The application calculates risk scores based on:
- **Age**: >60 (2 points), >45 (1 point)
- **Blood Pressure**: >140 (3 points), >130 (2 points)
- **Cholesterol**: >200 (3 points), >180 (1 point)
- **Diagnosis**: Non-healthy (2 points)

**Classification**:
- **Low Risk**: 0-3 points
- **Medium Risk**: 4-6 points
- **High Risk**: 7+ points

## 📋 Sample Output

```
=== MEDICAL DATA STATISTICS ===
Total Patients: 50

Age Statistics:
  Average: 48 years
  Range: 24 - 72

=== RISK CLASSIFICATION ANALYSIS ===
HIGH RISK: 12 patients (24%)
MEDIUM RISK: 18 patients (36%)
LOW RISK: 20 patients (40%)
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## ⚠️ Disclaimer

**This application is for educational and demonstration purposes only.** It is not a certified medical device and should not be used for actual patient diagnosis or treatment decisions. Always consult with qualified healthcare professionals for medical advice.

## 📄 License

This project is available under the MIT License. See the LICENSE file for more info.

---

**Created with ❤️ for healthcare informatics education**

*For questions or support, please open an issue in the repository.*
