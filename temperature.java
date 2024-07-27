package temperature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class temperature {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Temperature Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JLabel titleLabel = new JLabel("Temperature Converter", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel inputLabel = new JLabel("Select Temperature: ");

        
        JSlider temperatureSlider = new JSlider(JSlider.HORIZONTAL, -273, 1000, 0);
        temperatureSlider.setMajorTickSpacing(100);
        temperatureSlider.setMinorTickSpacing(10);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);

        
        JLabel sliderValueLabel = new JLabel("Temperature: 0", JLabel.CENTER);

        
        temperatureSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int temp = temperatureSlider.getValue();
                sliderValueLabel.setText("Temperature: " + temp);
                updateBackgroundColor(mainPanel, temp);
            }
        });

        JLabel unitLabel = new JLabel("Select Unit: ");
        String[] units = {"Celsius", "Fahrenheit", "Kelvin"};
        JComboBox<String> unitComboBox = new JComboBox<>(units);

        inputPanel.add(inputLabel);
        inputPanel.add(sliderValueLabel);
        inputPanel.add(unitLabel);
        inputPanel.add(unitComboBox);

        
        JButton convertButton = new JButton("Convert");
        convertButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

       
        mainPanel.add(titleLabel);
        mainPanel.add(inputPanel);
        mainPanel.add(temperatureSlider);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(convertButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(scrollPane);

        
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(imageLabel, BorderLayout.SOUTH);

        
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double temperature = temperatureSlider.getValue();
                    String unit = (String) unitComboBox.getSelectedItem();
                    String result = convertTemperature(temperature, unit);
                    resultArea.setText(result);
                } catch (NumberFormatException ex) {
                    resultArea.setText("Please enter a valid temperature.");
                }
            }
        });

        
        frame.setVisible(true);
    }

    
    public static String convertTemperature(double temperature, String unit) {
        StringBuilder result = new StringBuilder();

        switch (unit) {
            case "Celsius":
                double fahrenheitFromCelsius = temperature * 9 / 5 + 32;
                double kelvinFromCelsius = temperature + 273.15;
                result.append(String.format("%.2f Celsius is equal to %.2f Fahrenheit and %.2f Kelvin.%n",
                        temperature, fahrenheitFromCelsius, kelvinFromCelsius));
                break;
            case "Fahrenheit":
                double celsiusFromFahrenheit = (temperature - 32) * 5 / 9;
                double kelvinFromFahrenheit = celsiusFromFahrenheit + 273.15;
                result.append(String.format("%.2f Fahrenheit is equal to %.2f Celsius and %.2f Kelvin.%n",
                        temperature, celsiusFromFahrenheit, kelvinFromFahrenheit));
                break;
            case "Kelvin":
                double celsiusFromKelvin = temperature - 273.15;
                double fahrenheitFromKelvin = celsiusFromKelvin * 9 / 5 + 32;
                result.append(String.format("%.2f Kelvin is equal to %.2f Celsius and %.2f Fahrenheit.%n",
                        temperature, celsiusFromKelvin, fahrenheitFromKelvin));
                break;
        }

        return result.toString();
    }

    
    // Update the background color based on the temperature
    public static void updateBackgroundColor(JPanel panel, int temperature) {
        double normalizedTemp = (temperature + 273) / 1273.0;
        Color color = new Color((float) Math.min(1, normalizedTemp * 2), 0, (float) Math.min(1, (1 - normalizedTemp) * 2));
        panel.setBackground(color);
    }
}
