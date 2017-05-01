import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by olsen on 4/12/17.
 */
public class CubeDB {
    static private JList<String> recordsJList;
    private JTextField nameTextField;
    private JButton addRecordButton;
    private JTextField timeTextField;
    private JComboBox comboBoxDelete;
    private JButton deleteRecordButton;
    private JComboBox comboBoxUpdate;
    private JTextField newTimeTextField;
    private JButton updateRecordButton;

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/Cube";
    static final String USER = "Java";
    static final String PASSWORD = System.getenv("MySQL_Java_password");
    static private Scanner UserInput = new Scanner(System.in);
    static private Scanner SubInput = new Scanner(System.in);
    static private Scanner UserInput2 = new Scanner(System.in);
    static private Scanner SubInput2 = new Scanner(System.in);
    static private Scanner YN1 = new Scanner(System.in);
    static private Scanner YN2 = new Scanner(System.in);
    static private Scanner NewDuration = new Scanner(System.in);
    static private String Solver;
    static private Double Duration;
    static private Character YesOrNo1;
    static private Character YesOrNo2;
    static private double NewValue;
    static private String item;
    static private Integer count = 0;

    public static void main(String[] args) {

        try {
            Class.forName(JDBC_DRIVER); //try catch on it's own
        }
        catch (Exception ex){
            System.out.println("Driver not instantiated");
        }

        try {
            Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            String prepStatCreate = "CREATE TABLE IF NOT EXISTS CUBES (Solvers VARCHAR(50) ,Duration DOUBLE)";
            PreparedStatement psCreate = connection.prepareStatement(prepStatCreate);
            psCreate.execute();
            psCreate.close();

            String prepStatInsert = "INSERT INTO Cubes VALUES(?,?)";
            PreparedStatement psInsert = connection.prepareStatement(prepStatInsert);
            psInsert.setString(1, "Cubestormer II robot");
            psInsert.setDouble(2, 5.27);
            psInsert.executeUpdate();

            psInsert.setString(1, "Fakhri Raihaan (using his feet)");
            psInsert.setDouble(2, 27.93);
            psInsert.executeUpdate();

            psInsert.setString(1, "Ruxin Liu (age 3)");
            psInsert.setDouble(2, 99.33);
            psInsert.executeUpdate();

            psInsert.setString(1, "Mats Valk (human record holder)");
            psInsert.setDouble(2, 6.27);
            psInsert.executeUpdate(); //end try-catch

            String prepStatUpdate = "UPDATE Cubes SET Duration = ? WHERE Solvers = ?";
            PreparedStatement psUpdate = connection.prepareStatement(prepStatUpdate);

            String prepStatResultSet = "SELECT * FROM Cubes";
            PreparedStatement psResult = connection.prepareStatement(prepStatResultSet);
            ResultSet rs = psResult.executeQuery();

            while (rs.next()) {
                System.out.print(rs.getString("Solvers")+" | "+rs.getDouble("Duration")+"\n");
                item = rs.getString("Solvers").toString()+" | "+rs.getDouble("Duration");
                recordsJList.add(item);
                count++;
            }


                System.out.println("Would you like to add a new record? Enter Y for yes | N for no");
                if (YN1.hasNext()) YesOrNo1 = YN1.next().charAt(0);
                if (YesOrNo1.equals('Y')) {


                    System.out.println("Please enter the name of a new rubics cube solver.");
                    if (UserInput.hasNext()) Solver = UserInput.nextLine();

                    System.out.println("Please enter " + Solver.toString() + "'s time");
                    if (SubInput.hasNextDouble()) Duration = SubInput.nextDouble();

                    psInsert.setString(1, Solver.toString());
                    psInsert.setDouble(2, Duration);
                    psInsert.executeUpdate();

                } else {
                    System.out.println("Would you like to update a rubics cube solver's time? Enter Y for yes | N for no");
                    if (YN2.hasNext()) YesOrNo2 = YN2.next().charAt(0);
                    if (YesOrNo2.equals('Y')) {
                        System.out.println("Please enter the name of the rubics cube solver who's time you wish to update");
                        if (UserInput2.hasNext()) Solver = UserInput2.nextLine();
                        System.out.println("Please enter the new time");
                        if (NewDuration.hasNextDouble()) NewValue = NewDuration.nextDouble();
                        psUpdate.setDouble(1, NewValue);
                        psUpdate.setString(2, Solver);
                        psUpdate.executeUpdate();
                    }

                }

                ResultSet rs2 = psResult.executeQuery();
                while (rs2.next()) {
                    System.out.print(rs2.getString("Solvers"));
                    System.out.print(" | ");
                    System.out.print(rs2.getDouble("Duration"));
                    System.out.println("");
                }


            String prepStatDrop = "DROP TABLE Cubes";
            PreparedStatement psDrop = connection.prepareStatement(prepStatDrop);
            psDrop.execute();
            psDrop.close();
            rs.close();
            rs2.close();
            psInsert.close();
            psCreate.close();
            psResult.close();
            psUpdate.close();
            connection.close();
        }
        catch (SQLException ex){
            System.out.println("SQL sgtatements unable to execute");
        }

    }
}
