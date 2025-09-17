import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        List<HospitalStaff> staff = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader("src/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                HospitalStaff hs = HospitalStaff.fromLine(line);
                staff.add(hs);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (staff.isEmpty()) {
            System.out.println("No staff data loaded. Please check the input file path and contents.");
            return;
        }

        for (int i = 0; i < staff.size() - 1; i++) {
            for (int j = 0; j < staff.size() - i - 1; j++) {
                if (staff.get(j).age > staff.get(j + 1).age) {
                    HospitalStaff temp = staff.get(j);
                    staff.set(j, staff.get(j + 1));
                    staff.set(j + 1, temp);
                }
            }
        }

        System.out.println("\nEmployees sorted by age: ");
        for (int i = 0; i < staff.size(); i++) {
            staff.get(i).info();
        }
        System.out.println();


        HospitalStaff maxSalary = staff.get(0);
        for (int i = 1; i < staff.size(); i++) {
            if (staff.get(i).salary > maxSalary.salary) {
                maxSalary = staff.get(i);
            }
        }
        System.out.println("Employee with the highest salary: ");
        maxSalary.info();
        System.out.println();


        Doctor topDoctor = null;
        for (int i = 0; i < staff.size(); i++) {
            if (staff.get(i) instanceof Doctor) {
                Doctor doctor1 = (Doctor) staff.get(i);
                if (topDoctor == null || doctor1.patientsTreated > topDoctor.patientsTreated) {
                    topDoctor = doctor1;
                }
            }
        }
        System.out.println("Doctor with the most patients treated: ");
        if (topDoctor != null) {
            topDoctor.info();
        }
        System.out.println();


        Nurse topNurse = null;
        for (int i = 0; i < staff.size(); i++) {
            if (staff.get(i) instanceof Nurse) {
                Nurse nurse1 = (Nurse) staff.get(i);
                if (topNurse == null || nurse1.shiftsWorked > topNurse.shiftsWorked) {
                    topNurse = nurse1;
                }
            }
        }
        System.out.println("Most hardworking Nurse: ");
        if (topNurse != null) {
            topNurse.info();
        }
        System.out.println();

        Janitor topJanitor = null;
        for (int i = 0; i < staff.size(); i++) {
            if (staff.get(i) instanceof Janitor) {
                Janitor janitor1 = (Janitor) staff.get(i);
                if (topJanitor == null || janitor1.areaCovered > topJanitor.areaCovered) {
                    topJanitor = janitor1;
                }
            }
        }
        System.out.println("Most hardworking janitor: ");
        if (topJanitor != null) {
            topJanitor.info();
        }
        System.out.println();

    }
}

abstract class HospitalStaff {
    String firstName;
    String lastName;
    int age;
    double salary;

    public HospitalStaff(String firstName, String lastName, int age, double salary) {

        if (age <= 0) {
            throw new IllegalArgumentException("Age must be a positive number.");
        }

        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be a positive number!");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
    }

    public static HospitalStaff fromLine(String line) {
        String[] parts = line.split(",");

        String type = parts[0];
        String firstName = parts[1];
        String lastName = parts[2];
        int age = Integer.parseInt(parts[3].trim());
        double salary = Double.parseDouble(parts[4].trim());

        switch (type) {
            case "Doctor":
                return new Doctor(firstName, lastName, age, salary,
                        parts[5].trim().toCharArray(), Integer.parseInt(parts[6].trim()));
            case "Nurse":
                return new Nurse(firstName, lastName, age, salary,
                        parts[5].trim().toCharArray(), Integer.parseInt(parts[6].trim()));
            case "Janitor":
                return new Janitor(firstName, lastName, age, salary,
                        Integer.parseInt(parts[5].trim()));
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    public abstract void info();
}

class Doctor extends HospitalStaff {
    private char[] specialization;
    public int patientsTreated;
    String specializationString;

    public Doctor(String firstName, String lastName, int age, double salary, char[] specialization, int patientsTreated) {
        super(firstName, lastName, age, salary);
        this.specialization = specialization;
        this.patientsTreated = patientsTreated;
        this.specializationString = new String(specialization);
    }


    @Override
    public void info() {
        System.out.print("\nDoctor: " + firstName + " " + lastName + " - " + specializationString);
        System.out.printf("\nSalary: %.2f lv.%n", salary);
        System.out.println("Age: " + age);
        System.out.println("Patients Treated: " + patientsTreated);
    }

}

class Nurse extends HospitalStaff {
    char[] department;
    int shiftsWorked;
    String departmentString;

    public Nurse(String firstName, String lastName, int age, double salary, char[] department, int shiftsWorked) {
        super(firstName, lastName, age, salary);
        this.department = department;
        this.shiftsWorked = shiftsWorked;
        this.departmentString = new String(department);
    }


    @Override
    public void info() {
        System.out.print("\nNurse: " + firstName + " " + lastName + " - " + departmentString);
        System.out.printf("\nSalary: %.2f lv.%n", salary);
        System.out.println("Age: " + age);
        System.out.println("Shifts Worked: " + shiftsWorked);
    }
}

class Janitor extends HospitalStaff {
    int areaCovered;

    public Janitor(String firstName, String lastName, int age, double salary, int areaCovered) {
        super(firstName, lastName, age, salary);
        this.areaCovered = areaCovered;
    }

    @Override
    public void info() {
        System.out.print("\nJanitor: " + firstName + " " + lastName);
        System.out.printf("\nSalary: %.2f lv.%n", salary);
        System.out.println("Age: " + age);
        System.out.println("Area Covered: " + areaCovered);

    }

}
