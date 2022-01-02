package catan;

import catan.views.gui.GameWindow;

public class Catan {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }

        switch(args[0]) {
            case "gui":
                GameWindow window = new GameWindow();
                window.setVisible(true);
                break;
            case "tui":
                System.err.println("Not implemented.");
                System.exit(0);
                break;
            default:
                System.err.println("Unknown command.");
                System.exit(0);
                break;
        }
    }
}
