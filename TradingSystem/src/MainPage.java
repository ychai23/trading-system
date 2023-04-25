import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainPage extends JFrame{
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private static MainPage mp = null;

    public MainPage() {
        setTitle("Trading System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        add(loginButton);
        add(registerButton);
        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(loginButton); // Get the current frame
        JFrame regFrame = (JFrame) SwingUtilities.getWindowAncestor(registerButton); // Get the current frame

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                // redirect to login page
                UserLoginPage loginPage = new UserLoginPage();
                loginPage.setVisible(true);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to registration page
                try {
                    regFrame.dispose();
                    UserRegister userReg = new UserRegister();
                    userReg.setVisible(true);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static MainPage getMainPage() {
        if (mp == null) {
            mp = new MainPage();
        }
        return mp;
    }
}
