package catan.views.gui;


import catan.controllers.MenuController;
import catan.models.Menu;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MenuView extends JPanel implements View<Menu> {
    private final Menu model;

    public MenuView(Menu model) {
        this.model = model;
        setPreferredSize(new Dimension(500, 500));

        JLabel label = new JLabel("The Settlers of Catan", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(500, 50));
        label.setFont(new Font("default", Font.BOLD, 20));
        add(label);

        addPlayersSettingsPanel();
        addTraySettingPanel();
        addGameSettingPanel();
        addStartButton();
    }

    private void addGameSettingPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Game"));
        JLabel label = new JLabel("Victory points");
        panel.add(label);

        JSpinner slider = new JSpinner(new SpinnerNumberModel(10, 10, 100, 1));
        slider.addChangeListener(e -> model.setScore((int) slider.getValue()));
        panel.add(slider);
        add(panel);
    }

    private void addStartButton() {
        JButton start = new JButton("Start");
        start.setPreferredSize(new Dimension(100, 50));
        start.addActionListener(e -> MenuController.getInstance().start());
        add(start);
    }

    private void addPlayersSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 180));
        panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Players"));
        for(var p : model.getPlayerSettings()) panel.add(new PlayerSettingView(p));
        add(panel);
    }

    private void addTraySettingPanel() {
        JPanel traySetting = new JPanel();
        traySetting.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Tray size"));

        JComboBox<String> modes = new JComboBox<>();
        modes.addItem("normal 4*5");
        modes.addItem("big 6*6");
        modes.addItem("gigantic 8*8");
        modes.addActionListener(e -> model.setMode((String) modes.getSelectedItem()));
        traySetting.add(modes);
        add(traySetting);
    }

    @Override
    public void setModel(Menu model) {
    }

    private static class PlayerSettingView extends JPanel {
        public PlayerSettingView(Menu.PlayerSetting model) {
            setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Player"));

            JPanel colorPanel = new JPanel();
            colorPanel.setPreferredSize(new Dimension(30, 30));
            colorPanel.setBackground(model.getColor().toAwtColor());
            colorPanel.setBorder(new LineBorder(Color.BLACK, 2, true));
            add(colorPanel);

            JComboBox<String> playerType = new JComboBox<>();
            playerType.addItem("None");
            playerType.addItem("Human");
            playerType.addItem("Bot");
            playerType.addActionListener(e -> model.setType((String) playerType.getSelectedItem()));
            add(playerType);
        }
    }
}