package catan;

import catan.controllers.GameController;
import catan.models.Game;
import catan.views.gui.GameView;

public class Catan {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }

        switch(args[0]) {
            case "gui" -> {
                GameController controller = GameController.getInstance();
                controller.setView(new GameView());
                controller.setModel(new Game());
                controller.getView().pack();
                controller.getView().setLocationRelativeTo(null);
                controller.getView().setVisible(true);
            }
            case "tui" -> {
                System.err.println("Not implemented.");
                System.exit(0);
            }
            default -> {
                System.err.println("Unknown command.");
                System.exit(0);
            }
        }
    }
}
