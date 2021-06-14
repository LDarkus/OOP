package model;

import model.Sport;
import model.SportTitle;
import model.Sportsman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {
    private String DB_URL = "jdbc:h2:/" + System.getProperty("user.dir");
    private String DB_DRIVER = "org.h2.Driver";

    private static Connection connection;

    public DataBase() {
        try {
            Class.forName(this.DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL + "/Base");
            Statement state = connection.createStatement();
            state.execute(
                        "CREATE TABLE IF NOT EXISTS `SportTitle` (" +
                            "  `Id` int AUTO_INCREMENT NOT NULL," +
                            "  `Name` varchar(255) NOT NULL ," +
                            "  PRIMARY KEY (`Id`)" +
                            ");" +

                            "CREATE TABLE IF NOT EXISTS `Sport` (" +
                            "  `Id` int AUTO_INCREMENT NOT NULL," +
                            "  `Name` varchar(255) NOT NULL ," +
                            "  PRIMARY KEY (`Id`)" +
                            ");" +

                            "CREATE TABLE IF NOT EXISTS `Sportsman` (" +
                            "  `Id` int AUTO_INCREMENT NOT NULL," +
                            "  `FullName` varchar(255) NOT NULL ," +
                            "  `SportId` int NOT NULL," +
                            "  `PhoneNumber` varchar(15) NOT NULL," +
                            "  `SportTitleId` int NOT NULL," +
                            "  `CoachName` varchar(255) NOT NULL," +
                            "  PRIMARY KEY (`Id`)," +
                            "  CONSTRAINT `Sportsman_FK_SportId` FOREIGN KEY (`SportId`) REFERENCES `Sport` (`Id`) ON DELETE CASCADE," +
                            "  CONSTRAINT `Sportsman_FK_SportTitleId` FOREIGN KEY (`SportTitleId`) REFERENCES `SportTitle` (`Id`) ON DELETE CASCADE" +
                            ");"
            );


        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("БД Подключена");
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных");
        }
    }
    //---------------------------------------------Sportsman------------------------------------------------------------
    public static ArrayList<Sportsman> getAllSportsman() {
        ArrayList<Sportsman> sportsman = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Sportsman")) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                sportsman.add(new Sportsman(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6)
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return sportsman;
    }

    public static int addSportsman(String fullName, int sportId, String phoneNumber, int sportTitleId, String coachName) {
        int id = -1;
        String[] generatedColumns = {"Id"};
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Sportsman(`FullName`,`SportId`,`PhoneNumber`,`SportTitleId`, `CoachName`) VALUES(?,?,?,?,?)", generatedColumns)) {
            statement.setString(1, fullName);
            statement.setInt(2, sportId);
            statement.setString(3, phoneNumber);
            statement.setInt(4, sportTitleId);
            statement.setString(5, coachName);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public static void deleteSportsman(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Sportsman where id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void updateSportsman(int id, String fullName, int sportId, String phoneNumber, int sportTitleId, String coachName) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE Sportsman " +
                        "SET `FullName`=?, `SportId`=?, `PhoneNumber`=?, `SportTitleId`=?, `CoachName`=? " +
                        "WHERE Id = ?"
        )) {
            statement.setString(1, fullName);
            statement.setInt(2, sportId);
            statement.setString(3, phoneNumber);
            statement.setInt(4, sportTitleId);
            statement.setString(5, coachName);
            statement.setInt(6, id);

            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    //---------------------------------------------Sport----------------------------------------------------------------

    public static ArrayList<Sport> getAllSport() {
        ArrayList<Sport> sports = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Sport")) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                sports.add(new Sport(rs.getInt(1),rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return sports;
    }

    public static int addSport(String name) {
        int id = -1;
        String[] generatedColumns = {"Id"};

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Sport(`Name`) VALUES(?)" , generatedColumns)) {
            statement.setString(1, name);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public static void deleteSport(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Sport where id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void updateSport(int id, String name) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE Sport SET `Name`=? where id = ?")) {
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static String getSportName(int id) {
        String name = "";
        try (PreparedStatement statement = connection.prepareStatement("SELECT `Name` FROM Sport WHERE `Id`=?")) {
            statement.setInt(1, id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            if (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return name;
    }

    //-------------------------------------------SportTitle-------------------------------------------------------------

    public static ArrayList<SportTitle> getAllSportTitle() {
        ArrayList<SportTitle> sportTitles = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM SportTitle")) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                sportTitles.add(new SportTitle(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return sportTitles;
    }

    public static int addSportTitle(String name) {
        int id = -1;
        String[] generatedColumns = {"Id"};

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO SportTitle(`Name`) VALUES(?)" , generatedColumns)) {
            statement.setString(1, name);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public static void deleteSportTitle(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM SportTitle where id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void updateSportTitle(int id, String name) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE SportTitle SET `Name`=? WHERE id = ?")) {
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static String getSportTitleName(int id) {
        String name = "";
        try (PreparedStatement statement = connection.prepareStatement("SELECT `Name` FROM SportTitle WHERE `Id`=?")) {
            statement.setInt(1, id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            if (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return name;
    }
}


