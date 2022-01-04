package catan.views.gui;


import catan.controllers.MenuController;
import catan.models.Menu;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MenuView extends JPanel implements View<Menu> {
    private Menu model = new Menu();

    public MenuView(Menu model) {
        this.model = model;
        setPreferredSize(new Dimension(450, 500));

        JLabel label = new JLabel("The Settlers of Catan", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(500, 50));
        label.setFont(new Font("default", Font.BOLD, 20));
        add(label);

        add(getPlayersSettingsPanel());

        JPanel traySetting = new JPanel();
        traySetting.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Tray size"));

        JComboBox<String> modes = new JComboBox<>();
        modes.addItem("normal 4*5");
        modes.addItem("big 6*6");
        modes.addItem("gigantic 8*8");
        traySetting.add(modes);
        add(traySetting);

        JButton start = new JButton("Start");
        start.setPreferredSize(new Dimension(100, 50));
        start.addActionListener(e -> {
            MenuController.getInstance().start((String) modes.getSelectedItem());
            setVisible(false);
        });
        add(start);
    }

    private JPanel getPlayersSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Players"));
        for(var p: model.getPlayerSettings()) panel.add(new PlayerSettingView(p));
        return panel;
    }

    @Override
    public void setModel(Menu model) {
        this.model = model;
    }

    private static class PlayerSettingView extends JPanel {
        private final Menu.PlayerSetting model;

        public PlayerSettingView(Menu.PlayerSetting model) {
            this.model = model;
            setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Player"));

            JPanel colorPanel = new JPanel();
            colorPanel.setPreferredSize(new Dimension(30, 30));
            colorPanel.setBackground(model.getColor().toAwtColor());
            colorPanel.setBorder(new LineBorder(Color.BLACK, 2, true));
            add(colorPanel);

            JComboBox<String> playerType = new JComboBox<>();
            playerType.addItem("Human");
            playerType.addItem("Bot");
            playerType.addActionListener(e -> model.setType((String) playerType.getSelectedItem()));
            add(playerType);
        }
    }
}