import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MainGUI mainGUI = new MainGUI();
        mainGUI.setSize(1280, 720);
        mainGUI.setResizable(false);
        mainGUI.setVisible(true);
        mainGUI.setLocationRelativeTo(null); // middle of screen
        mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
