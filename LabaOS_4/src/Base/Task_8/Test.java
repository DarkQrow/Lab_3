package Base.Task_8;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.util.ArrayList;

public class Test {

    static void initAndShowGUI( ArrayList<int[]> a) {
        // This method is invoked on the EDT thread
        JFrame frame = new JFrame();
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Platform.runLater(() -> initFX(fxPanel,a));
    }

    private static void initFX(JFXPanel fxPanel,ArrayList<int[]> a) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene(a);
        fxPanel.setScene(scene);
    }

    private static Scene createScene(ArrayList<int[]>a) {
        Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root, Color.BLACK);
        Group g = new Group();

        Rectangle[] r = new Rectangle[a.size()];
        for(int i = 0;i<r.length;i++){
            int[] c = a.get(i);
            r[i] = new Rectangle();
            r[i].setHeight(30);
            r[i].setWidth(30);
            r[i].setY(c[1]*30);
            r[i].setX(c[0]*30);
            r[i].setFill(Color.GREEN);
            g.getChildren().add(r[i]);
        }


        root.getChildren().add(g);

        return (scene);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            //    initAndShowGUI();
            }
        });
    }
}