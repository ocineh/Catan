package catan;

import catan.views.gui.Game;

public class Catan {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }

        switch(args[0]){
            case "gui"->{
                Game game = new Game();
                game.setVisible(true);
            }
            case "tui"->{
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
