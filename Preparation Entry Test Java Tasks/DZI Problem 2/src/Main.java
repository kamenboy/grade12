import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<TheClubMember> members = new ArrayList<>();
        TheFootballPlayer topScorer = null;
        TheClubMember highestPaid = null;

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].trim();
                    }
                    String type = parts[0];

                    switch (type) {
                        case "Director":
                            members.add(new Director(parts[1], parts[2],
                                    Integer.parseInt(parts[3]),
                                    Double.parseDouble(parts[4]),
                                    parts[5]));
                            break;
                        case "Coach":
                            members.add(new Coach(parts[1], parts[2],
                                    Integer.parseInt(parts[3]),
                                    Double.parseDouble(parts[4]),
                                    parts[5],
                                    Integer.parseInt(parts[6])));
                            break;
                        case "FootballPlayer":
                            TheFootballPlayer player = new TheFootballPlayer(parts[1], parts[2],
                                    Integer.parseInt(parts[3]),
                                    Double.parseDouble(parts[4]),
                                    parts[5],
                                    Integer.parseInt(parts[6]),
                                    Integer.parseInt(parts[7]),
                                    Integer.parseInt(parts[8]),
                                    Integer.parseInt(parts[9]));
                            members.add(player);
                            if (topScorer == null || player.getGoals() > topScorer.getGoals()) {
                                topScorer = player;
                            }
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing line: " + line);
                    System.out.println(e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        members.sort(Comparator.comparingInt(TheClubMember::getAge));

        for (TheClubMember m : members) {
            if (highestPaid == null || m.getSalary() > highestPaid.getSalary()) {
                highestPaid = m;
            }
        }

        for (int i = 0; i < members.size(); i++) {
            members.get(i).getInfo();
            if (i != members.size() - 1) {
                System.out.println("*".repeat(20));
            }
        }

        if (highestPaid != null) {
            System.out.printf("The person with the highest salary in the club is %s with %.2f lv salary.%n",
                    highestPaid.getFullName(), highestPaid.getSalary());
        }
        if (topScorer != null) {
            System.out.printf("The team's top scorer is %s with %d goals.%n",
                    topScorer.getFullName(), topScorer.getGoals());
        }
    }
}

abstract class TheClubMember {
    private String firstName;
    private String lastName;
    private int age;
    private double salary;

    public TheClubMember(String firstName, String lastName, int age, double salary) {
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("The name can’t be an empty string!");
        }
        if (age < 17) {
            throw new IllegalArgumentException("The age can't be less than 17!");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be a positive number");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
    }


    public void getInfo() {
        System.out.printf("-> %s %s%n-> %d years old%n-> %.2f EUR%n",
                firstName, lastName, age, salary);
    }


    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

class Director extends TheClubMember {
    private String directorType;


    public Director(String firstName, String lastName, int age, double salary, String directorType) {
        super(firstName, lastName, age, salary);
        if (!(directorType.equals("executive") || directorType.equals("technical") || directorType.equals("sports"))) {
            throw new IllegalArgumentException("Invalid director type!");
        }
        this.directorType = directorType;
    }

    public void getInfo() {
        System.out.println(directorType + " director: " + super.getFullName());
        System.out.printf("Salary: %.2f EUR%n", super.getSalary());
        System.out.println("Age: " + super.getAge() + " years old");
    }

}

class Coach extends TheClubMember {
    private String coachType;
    private int contractLength;

    public Coach(String firstName, String lastName, int age, double salary, String coachType, int contractLength) {
        super(firstName, lastName, age, salary);
        if (!(coachType.equals("head") || coachType.equals("assistant") || coachType.equals("goalkeeper"))) {
            throw new IllegalArgumentException("Invalid coach type!");
        }
        this.coachType = coachType;
        this.contractLength = contractLength;
    }

    public void getInfo() {
        System.out.println(coachType + " coach: " + super.getFullName());
        System.out.printf("Salary: %.2f lv%n", super.getSalary());
        System.out.println("Age: " + super.getAge() + " years old");
    }
}

class TheFootballPlayer extends TheClubMember {
    private String position;
    private int contractLength;
    private int matches;
    private int goals;
    private int assist;

    public TheFootballPlayer(String firstName, String lastName, int age, double salary, String position, int contractLength, int matches, int goals, int assist) {
        super(firstName, lastName, age, salary);
        this.position = position;
        this.contractLength = contractLength;
        this.matches = matches;
        this.goals = goals;
        this.assist = assist;
    }

    public int getGoals() {
        return goals;
    }

    @Override
    public void getInfo() {
        System.out.println(getFullName() + " - " + position);
        System.out.printf("salary: %.2f lv%n", getSalary());
        System.out.println("age: " + getAge() + " years");
        System.out.println(goals + " goals and " + assist + " assists in " + matches + " matches");
    }
}
