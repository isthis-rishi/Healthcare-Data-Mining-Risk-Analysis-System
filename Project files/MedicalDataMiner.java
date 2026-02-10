import java.io.*;
import java.util.*;

public class MedicalDataMiner {
    private ArrayList<Patient> allPatients;
    private HashMap<String, ArrayList<Patient>> diagnosisMap;
    private HashMap<String, Integer> bloodGroupCount;
    
    public MedicalDataMiner() {
        allPatients = new ArrayList<>();
        diagnosisMap = new HashMap<>();
        bloodGroupCount = new HashMap<>();
    }
    
    public boolean loadDataFromFile(String filename) {
        allPatients.clear();
        diagnosisMap.clear();
        bloodGroupCount.clear();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    int bp = Integer.parseInt(parts[3]);
                    int chol = Integer.parseInt(parts[4]);
                    String bloodGroup = parts[5];
                    String diagnosis = parts[6];
                    
                    Patient patient = new Patient(id, name, age, bp, chol, bloodGroup, diagnosis);
                    allPatients.add(patient);
                    
                    if (!diagnosisMap.containsKey(diagnosis)) {
                        diagnosisMap.put(diagnosis, new ArrayList<>());
                    }
                    diagnosisMap.get(diagnosis).add(patient);
                    
                    bloodGroupCount.put(bloodGroup, bloodGroupCount.getOrDefault(bloodGroup, 0) + 1);
                }
            }
            reader.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String calculateBasicStatistics() {
        if (allPatients.isEmpty()) return "No data loaded";
        
        int totalAge = 0, totalBP = 0, totalChol = 0;
        int minAge = Integer.MAX_VALUE, maxAge = Integer.MIN_VALUE;
        int minBP = Integer.MAX_VALUE, maxBP = Integer.MIN_VALUE;
        int minChol = Integer.MAX_VALUE, maxChol = Integer.MIN_VALUE;
        
        for (Patient p : allPatients) {
            totalAge += p.getAge();
            totalBP += p.getBloodPressure();
            totalChol += p.getCholesterol();
            
            minAge = Math.min(minAge, p.getAge());
            maxAge = Math.max(maxAge, p.getAge());
            minBP = Math.min(minBP, p.getBloodPressure());
            maxBP = Math.max(maxBP, p.getBloodPressure());
            minChol = Math.min(minChol, p.getCholesterol());
            maxChol = Math.max(maxChol, p.getCholesterol());
        }
        
        int count = allPatients.size();
        StringBuilder result = new StringBuilder();
        result.append("=== MEDICAL DATA STATISTICS ===\n");
        result.append("Total Patients: ").append(count).append("\n\n");
        result.append("Age Statistics:\n");
        result.append("  Average: ").append(totalAge / count).append(" years\n");
        result.append("  Range: ").append(minAge).append(" - ").append(maxAge).append("\n\n");
        result.append("Blood Pressure Statistics:\n");
        result.append("  Average: ").append(totalBP / count).append(" mmHg\n");
        result.append("  Range: ").append(minBP).append(" - ").append(maxBP).append("\n\n");
        result.append("Cholesterol Statistics:\n");
        result.append("  Average: ").append(totalChol / count).append(" mg/dL\n");
        result.append("  Range: ").append(minChol).append(" - ").append(maxChol).append("\n");
        
        return result.toString();
    }
    
    public String performRiskAnalysis() {
        if (allPatients.isEmpty()) return "No data loaded";
        
        int lowRisk = 0, mediumRisk = 0, highRisk = 0;
        ArrayList<Patient> highRiskPatients = new ArrayList<>();
        
        for (Patient p : allPatients) {
            int riskScore = calculateRiskScore(p);
            if (riskScore <= 3) {
                lowRisk++;
            } else if (riskScore <= 6) {
                mediumRisk++;
            } else {
                highRisk++;
                highRiskPatients.add(p);
            }
        }
        
        StringBuilder result = new StringBuilder();
        result.append("=== RISK CLASSIFICATION ANALYSIS ===\n");
        result.append("Total Patients Analyzed: ").append(allPatients.size()).append("\n\n");
        result.append("Risk Distribution:\n");
        result.append("  HIGH RISK: ").append(highRisk).append(" patients (")
              .append((highRisk * 100) / allPatients.size()).append("%)\n");
        result.append("  MEDIUM RISK: ").append(mediumRisk).append(" patients (")
              .append((mediumRisk * 100) / allPatients.size()).append("%)\n");
        result.append("  LOW RISK: ").append(lowRisk).append(" patients (")
              .append((lowRisk * 100) / allPatients.size()).append("%)\n\n");
        
        if (!highRiskPatients.isEmpty()) {
            result.append("High Risk Patients (Need Immediate Attention):\n");
            for (int i = 0; i < Math.min(5, highRiskPatients.size()); i++) {
                Patient p = highRiskPatients.get(i);
                result.append("  - ").append(p.getName())
                      .append(" (Age: ").append(p.getAge())
                      .append(", BP: ").append(p.getBloodPressure())
                      .append(", Cholesterol: ").append(p.getCholesterol())
                      .append(")\n");
            }
        }
        
        return result.toString();
    }
    
    private int calculateRiskScore(Patient p) {
        int score = 0;
        if (p.getAge() > 60) score += 2;
        else if (p.getAge() > 45) score += 1;
        
        if (p.getBloodPressure() > 140) score += 3;
        else if (p.getBloodPressure() > 130) score += 2;
        
        if (p.getCholesterol() > 200) score += 3;
        else if (p.getCholesterol() > 180) score += 1;
        
        if (!p.getDiagnosis().equals("Healthy")) score += 2;
        
        return score;
    }
    
    public String findPatterns() {
        if (allPatients.isEmpty()) return "No data loaded";
        
        StringBuilder result = new StringBuilder();
        result.append("=== PATTERN ANALYSIS ===\n\n");
        
        int highBPHighChol = 0;
        int highBPWithHeart = 0;
        int diabetesOver50 = 0;
        int healthyUnder30 = 0;
        
        for (Patient p : allPatients) {
            if (p.getBloodPressure() > 140 && p.getCholesterol() > 200) {
                highBPHighChol++;
                if (p.getDiagnosis().contains("Heart")) {
                    highBPWithHeart++;
                }
            }
            if (p.getAge() > 50 && p.getDiagnosis().equals("Diabetes")) {
                diabetesOver50++;
            }
            if (p.getAge() < 30 && p.getDiagnosis().equals("Healthy")) {
                healthyUnder30++;
            }
        }
        
        result.append("Discovered Patterns:\n");
        result.append("1. Patients with High BP (>140) AND High Cholesterol (>200): ")
              .append(highBPHighChol).append("\n");
        if (highBPHighChol > 0) {
            result.append("   - Of these, ").append(highBPWithHeart)
                  .append(" have heart-related issues (")
                  .append((highBPWithHeart * 100) / highBPHighChol).append("%)\n");
        }
        result.append("\n2. Diabetes Cases in Age > 50: ").append(diabetesOver50).append("\n");
        result.append("3. Healthy Individuals Under 30: ").append(healthyUnder30).append("\n\n");
        
        result.append("Age Group Analysis:\n");
        int[] ageGroups = new int[4];
        for (Patient p : allPatients) {
            if (p.getAge() < 30) ageGroups[0]++;
            else if (p.getAge() < 45) ageGroups[1]++;
            else if (p.getAge() < 60) ageGroups[2]++;
            else ageGroups[3]++;
        }
        result.append("  Under 30: ").append(ageGroups[0]).append(" patients\n");
        result.append("  30-44: ").append(ageGroups[1]).append(" patients\n");
        result.append("  45-59: ").append(ageGroups[2]).append(" patients\n");
        result.append("  60 and above: ").append(ageGroups[3]).append(" patients\n");
        
        return result.toString();
    }
    
    public String analyzeFrequency() {
        if (allPatients.isEmpty()) return "No data loaded";
        
        StringBuilder result = new StringBuilder();
        result.append("=== FREQUENCY ANALYSIS ===\n\n");
        
        result.append("Diagnosis Distribution:\n");
        for (Map.Entry<String, ArrayList<Patient>> entry : diagnosisMap.entrySet()) {
            int count = entry.getValue().size();
            int percentage = (count * 100) / allPatients.size();
            result.append("  ").append(entry.getKey()).append(": ")
                  .append(count).append(" cases (").append(percentage).append("%)\n");
        }
        
        result.append("\nBlood Group Distribution:\n");
        for (Map.Entry<String, Integer> entry : bloodGroupCount.entrySet()) {
            int percentage = (entry.getValue() * 100) / allPatients.size();
            result.append("  ").append(entry.getKey()).append(": ")
                  .append(entry.getValue()).append(" patients (").append(percentage).append("%)\n");
        }
        
        String mostCommon = "";
        int maxCount = 0;
        for (Map.Entry<String, ArrayList<Patient>> entry : diagnosisMap.entrySet()) {
            if (entry.getValue().size() > maxCount) {
                maxCount = entry.getValue().size();
                mostCommon = entry.getKey();
            }
        }
        
        result.append("\nMost Common Diagnosis: ").append(mostCommon)
              .append(" (").append(maxCount).append(" cases)\n");
        
        return result.toString();
    }
    
    public String predictDisease(int age, int bp, int cholesterol) {
        StringBuilder prediction = new StringBuilder();
        prediction.append("=== DISEASE PREDICTION ===\n");
        prediction.append("Input: Age=").append(age)
                  .append(", BP=").append(bp)
                  .append(", Cholesterol=").append(cholesterol).append("\n\n");
        
        String risk = "";
        String recommendation = "";
        
        if (bp > 160 || (bp > 140 && age > 55)) {
            risk = "High Risk of Hypertension";
            recommendation = "Immediate medical consultation recommended";
        } else if (cholesterol > 240 && age > 45) {
            risk = "High Risk of Heart Disease";
            recommendation = "Cardiac evaluation suggested";
        } else if (bp > 130 || cholesterol > 200) {
            risk = "Moderate Risk - Pre-condition Stage";
            recommendation = "Lifestyle changes and regular monitoring needed";
        } else if (age < 30 && bp < 120 && cholesterol < 180) {
            risk = "Low Risk - Healthy Range";
            recommendation = "Maintain current lifestyle, annual check-ups";
        } else {
            risk = "Needs Monitoring";
            recommendation = "Regular health check-ups every 6 months";
        }
        
        prediction.append("Prediction: ").append(risk).append("\n");
        prediction.append("Recommendation: ").append(recommendation).append("\n");
        
        return prediction.toString();
    }
    
    public void generateReport(String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.println("MEDICAL DATA MINING REPORT");
            writer.println("Generated on: " + new Date());
            writer.println("=" .repeat(50));
            writer.println();
            writer.println(calculateBasicStatistics());
            writer.println(performRiskAnalysis());
            writer.println(findPatterns());
            writer.println(analyzeFrequency());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Patient> getAllPatients() {
        return allPatients;
    }
    
    public ArrayList<Patient> getHighRiskPatients() {
        ArrayList<Patient> highRisk = new ArrayList<>();
        for (Patient p : allPatients) {
            if (calculateRiskScore(p) > 6) {
                highRisk.add(p);
            }
        }
        return highRisk;
    }
    
    public ArrayList<Patient> getPatientsByDiagnosis(String diagnosis) {
        return diagnosisMap.getOrDefault(diagnosis, new ArrayList<>());
    }
}
