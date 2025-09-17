import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        StudentsWork maths = new StudentsWork(n);

        maths.readPoints(scan);
        maths.returnValidCount();
        System.out.println(maths.MinDiffPoints());
        maths.Laureates();
    }
}

class StudentsWork {
    double[] points;
    int n;
    double[] validPoints;
    int validCount = 0;

    public StudentsWork(int n) {
        Scanner scan = new Scanner(System.in);
        points = new double[n];
        validPoints = new double[n];
        this.n = n;
    }

    public void readPoints(Scanner scan) {
        for (int i = 0; i < n; i++) {
            points[i] = scan.nextDouble();
            if (points[i] > 0) {
                validPoints[validCount] = points[i];
                validCount++;
            }
        }
    }

    public void returnValidCount() {
        System.out.print("Valid Result Count = " + validCount);
    }

    public double MinDiffPoints() {
        if (validCount < 2) {
            return 0;
        }

        double minDiff = Double.MAX_VALUE;
        double minDiffNum1 = 0;
        double minDiffNum2 = 0;
        for (int i = 0; i < validCount; i++) {
            for (int j = i + 1; j < validCount; j++) {
                double diff = Math.abs(validPoints[i] - validPoints[j]);
                if (diff < minDiff) {
                    minDiff = diff;
                    minDiffNum1 = validPoints[i];
                    minDiffNum2 = validPoints[j];
                }
            }
        }
        System.out.print("\nMin difference is between " + minDiffNum1 + " and " + minDiffNum2 + " = ");
        return minDiff;
    }

    public void Laureates() {
        Arrays.sort(points);
        System.out.println("\nLaureates: \n" + "Top 1 points: " + points[points.length - 1]);
        System.out.println("Top 2 points: " + points[points.length - 2]);
        System.out.println("Top 3 points: " + points[points.length - 3]);

    }
}
