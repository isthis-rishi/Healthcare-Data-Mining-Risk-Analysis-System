public class Patient {
    private int patientID;
    private String name;
    private int age;
    private int bloodPressure;
    private int cholesterol;
    private String bloodGroup;
    private String diagnosis;
    
    public Patient(int patientID, String name, int age, int bloodPressure, 
                   int cholesterol, String bloodGroup, String diagnosis) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.bloodPressure = bloodPressure;
        this.cholesterol = cholesterol;
        this.bloodGroup = bloodGroup;
        this.diagnosis = diagnosis;
    }
    
    public int getPatientID() { return patientID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public int getBloodPressure() { return bloodPressure; }
    public int getCholesterol() { return cholesterol; }
    public String getBloodGroup() { return bloodGroup; }
    public String getDiagnosis() { return diagnosis; }
}
