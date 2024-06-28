package oops;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO; 
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSliderUI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Image extends JFrame {
    String loggedInUser = Login.loggedInUser;
    int currUserId;

    private JPanel imagePanel;
    private String url = "jdbc:mysql://localhost:3306/student"; // Update with your database URL
    private String username = "root"; // Update with your database username
    private String password = "root"; // Update with your database password

    // Define a map to store raised amounts for each image ID
    private Map<Integer, Double> raisedAmounts;

    public Image() {
        this.setUndecorated(true);
        setTitle("Projects");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        // Create a panel for the top section with a label, "Refresh," "Logout," and "Close" buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("PROJECTS");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("HP Simplified", Font.BOLD, 32));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton refreshButton = new JButton();
        refreshButton.setFocusable(false);
        refreshButton.setIcon(new ImageIcon("C:\\Users\\Rashmit Mhatre\\Downloads\\OOPS\\refresh-page-option1.png"));
        refreshButton.setPreferredSize(new Dimension(50, 25));
        refreshButton.addActionListener(e -> loadImagesFromDatabase());
        buttonPanel.add(refreshButton);

        JButton logoutButton = new JButton(new ImageIcon("C:\\Users\\Rashmit Mhatre\\Downloads\\OOPS\\switch.png"));
        logoutButton.setFocusable(false);
        logoutButton.setPreferredSize(new Dimension(40, 25));
        logoutButton.addActionListener(e -> handleLogout());
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.WEST);

        JButton closeButton = new JButton(new ImageIcon("C:\\Users\\Rashmit Mhatre\\Downloads\\OOPS\\close.png"));
        closeButton.setPreferredSize(new Dimension(40, 25));
        closeButton.setFocusable(false);
        closeButton.addActionListener(e -> handleWindowClose());
        topPanel.add(closeButton, BorderLayout.EAST);

        JButton maximizeButton = new JButton(new ImageIcon("C:\\Users\\Rashmit Mhatre\\Downloads\\OOPS\\move1.png"));
        maximizeButton.setPreferredSize(new Dimension(40, 25));
        maximizeButton.setFocusable(false);
        maximizeButton.addActionListener(e -> handleMaximize());
        buttonPanel.add(maximizeButton);

        add(topPanel, BorderLayout.NORTH);

        imagePanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        imagePanel.setBackground(new Color(0xDFC5FE));

        // Initialize the raisedAmounts map
        raisedAmounts = new HashMap<>();

        // Load images initially
        loadImagesFromDatabase();

        getContentPane().add(scrollPane);
    }

    private void handleMaximize() {
        if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setExtendedState(JFrame.NORMAL);
        }
    }

    private void handleWindowClose() {
        int a = JOptionPane.showConfirmDialog(null, "Do you want to go back to the Home Page??", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            new Home().setVisible(true);
            dispose();
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(null, "Do you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new Login().setVisible(true);
            dispose();
        }
    }

    private void loadImagesFromDatabase() {
        ImageIcon customThumbIcon = new ImageIcon("C:\\Users\\Rashmit Mhatre\\Downloads\\OOPS\\dollar1.png");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM images";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Clear existing content when reloading
            imagePanel.removeAll();

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between images
            gbc.anchor = GridBagConstraints.WEST; // Align to the left

            int column = 0;
            int row = 0;

            while (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("pic");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int id = resultSet.getInt("project_id");
                Double raisedAmount = resultSet.getDouble("raised_amount");
                int target = resultSet.getInt("target");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");

                // Create a project panel for each project
                JPanel projectPanel = new JPanel();
                projectPanel.setLayout(new GridBagLayout());

                if (imageBytes != null) {

                    ImageIcon icon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes)));
                    JLabel imageLabel = new JLabel(icon);
                    imageLabel.setBorder(BorderFactory.createRaisedBevelBorder());
                    JLabel nameLabel = new JLabel(name);
                    nameLabel.setFont(new Font("HP Simplified", Font.ROMAN_BASELINE, 40));

                    JLabel descriptionLabel = new JLabel("<html>" + description.replaceAll("\\.", ".<br>") + "</html>");
                    descriptionLabel.setFont(new Font("Segoe UI Light", Font.ITALIC, 20));

                    JLabel startDateLabel = new JLabel("Start Date: " + formatDate(startDate));
                    startDateLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 15));

                    JLabel endDateLabel = new JLabel("End Date: " + formatDate(endDate));
                    endDateLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 15));

                    JButton donateButton = new JButton("Donate");
                    donateButton.setFocusable(false);
                    donateButton.setFont(new Font("HP Simplified", Font.BOLD, 15));
                    donateButton.setIcon(new ImageIcon("C:\\Users\\Rashmit Mhatre\\Downloads\\OOPS\\cash1.png"));
                    
                    
                    BoundedRangeModel customModel = new DefaultBoundedRangeModel(0, 0, 0, target);

                    JSlider customSlider = new JSlider(customModel);
                    customSlider.setUI(new BasicSliderUI(customSlider) {
                        @Override
                        public void paintThumb(Graphics g) {
                            Rectangle knobBounds = thumbRect;
                            int thumbX = xPositionForValue(slider.getValue());
                            int thumbY = knobBounds.y + (knobBounds.height - customThumbIcon.getIconHeight()) / 2;

                            if (slider.getOrientation() == JSlider.HORIZONTAL) {
                                customThumbIcon.paintIcon(slider, g, thumbX - customThumbIcon.getIconWidth() / 2, thumbY);
                            } else {
                                customThumbIcon.paintIcon(slider, g, thumbX, thumbY);
                            }
                        }
                    });

                    customSlider.setMaximum(target);
                    customSlider.setValue(raisedAmount.intValue());

                    donateButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        double amount = 0;
        String donationAmount = JOptionPane.showInputDialog(
                null,
                "Enter the amount you want to donate:",
                "Donate",
                JOptionPane.QUESTION_MESSAGE
        );

        if (donationAmount != null && !donationAmount.isEmpty() && loggedInUser != null) {
            amount = Double.parseDouble(donationAmount);

            // Update the "raised_amt" in the database and the slider
            updateRaisedAmountInDatabase(id, amount, customSlider);
            customSlider.setValue((int) (customSlider.getValue() + amount)); // Update the slider value
        }

        if (loggedInUser != null && amount != 0) {
            openImageDialog();
            currUserId = getUserIdForUsername(loggedInUser);
            if (currUserId != -1) {
                insertDonationRecord(currUserId, id, amount);
            } 
        }
    }
});



                    // Add components to the project panel
                    GridBagConstraints projectGBC = new GridBagConstraints();
                    projectGBC.anchor = GridBagConstraints.CENTER;
                    projectGBC.gridx = 0;
                    projectGBC.gridy = 0;
                    projectGBC.insets = new Insets(5, 5, 5, 5); // Add spacing
                    projectPanel.add(imageLabel, projectGBC);

                    projectGBC.gridy = 1;
                    projectPanel.add(nameLabel, projectGBC);

                    projectGBC.gridy = 2;
                    projectGBC.anchor = GridBagConstraints.WEST;
                    projectPanel.add(descriptionLabel, projectGBC);

                    projectGBC.gridy = 3;
                    projectPanel.add(startDateLabel, projectGBC);

                    projectGBC.gridy = 4;
                    projectPanel.add(endDateLabel, projectGBC);

                    projectGBC.gridy = 5;
                    projectPanel.add(customSlider, projectGBC);

                    projectGBC.gridy = 6;
                    projectPanel.add(donateButton, projectGBC);

                    // Add the project panel to the imagePanel
                    gbc.gridx = column;
                    gbc.gridy = row;
                    imagePanel.add(projectPanel, gbc);

                    column++;
                    if (column >= 2) {
                        column = 0;
                        row++;
                    }
                } else {
                    System.err.println("Image data is null for name: " + name);
                }
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // Revalidate and repaint to update the UI
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private int getUserIdForUsername(String loggedInUser) {
        int id;

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            String querygetid = "SELECT userid FROM login WHERE username = ?";

            PreparedStatement pst = con.prepareStatement(querygetid);
            pst.setString(1, loggedInUser);
            ResultSet rsid = pst.executeQuery();

            rsid.next();

            id = rsid.getInt(1);

            return id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void insertDonationRecord(int userId, int projectId, double donationAmount) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            // Get the current date and time
            java.util.Date currentDate = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(currentDate.getTime());
    
            String query = "INSERT INTO donation (user_id, project_id, donation_time, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, projectId);
            statement.setTimestamp(3, timestamp); // Set the current date and time
            statement.setDouble(4, donationAmount);
    
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void openImageDialog() {
        JFrame imageFrame = new JFrame("Donation Successful");
        JLabel imageLabel = new JLabel();
    
        // Load and set the image in the label
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Rashmit Mhatre\\OneDrive\\Documents\\NetBeansProjects\\OOPS\\src\\images\\QR1.jpg"); // Replace with the actual path to your image
        imageLabel.setIcon(imageIcon);
    
        imageFrame.getContentPane().add(imageLabel);
        imageFrame.pack();
        imageFrame.setLocationRelativeTo(null);
        imageFrame.setVisible(true);
    }
    

    private void updateRaisedAmountInDatabase(int id, double amount, JSlider slider) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
    
            // Create an SQL update statement to increase the raised_amt
            String updateQuery = "UPDATE images SET raised_amount = raised_amount + ? WHERE project_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDouble(1, amount);
            updateStatement.setInt(2, id);
    
            // Execute the update statement
            updateStatement.executeUpdate();
            updateStatement.close();
            connection.close();
    
            // Update the raised amount in the map
            if (raisedAmounts.containsKey(id)) {
                raisedAmounts.put(id, raisedAmounts.get(id) + amount);
                // Update the slider's value
                slider.setValue(raisedAmounts.get(id).intValue());
            } else {
                // Handle the case where the key is not found in the map
                System.err.println("Raised amount not found for id: " + id);
            }
    
            System.out.println("Donated amount: " + amount);
            System.out.println("Donation added to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Image imageGallery = new Image();
            imageGallery.setVisible(true);
        });
    }
}