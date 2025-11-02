package executable;

import javax.swing.SwingUtilities;
import view.View;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new View().setVisible(true));
    }
}
