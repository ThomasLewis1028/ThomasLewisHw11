import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

/**
 * FINAL PROJECT     ThomasFinal.java
 *
 * Author:         Thomas Lewis
 * Class:          Java, Tuesday and Thursday 1:30pm
 * Due Date:       December 9th, 2015
 *
 * Program Description:
 *      This is a JApplet in the style of a restaurant website.
 *      It includes a number of JApplet functions with event handlers
 */

public class ThomasFinal extends JApplet{
    //Base page elements
    private JPanel basePage;
    private JPanel headerPanel;
    private JTabbedPane foodTabPane;
    private JPanel cartPanel;
    private JPanel pricesPanel;

    //Cart panel elements
    private JScrollPane cartScroller;
    private JList cartList;
    private JButton removeCartButton;

    //Price panel elements
    private JPanel deliveryOptionPanel;
    private JTextField addressTextField;
    private JTextField pickUpTimeTextField;
    private JTextArea specialNotesTextArea;
    private JScrollPane specialNotesScroll;
    private JButton checkoutButton;
    private JComboBox deliveryOptionBox;
    private JPanel addressTextFieldPanel;

    //Food Tabs
    private JPanel appetizersPanel;
    private JPanel entreesPanel;
    private JPanel pizzaPanel;
    private JPanel saladPanel;
    private JPanel dessertPanel;
    private JPanel drinksPanel;

    //Variables and labels to be used throughout
    private final int cartMax = 500;
    private String[] cartItems = new String[cartMax];
    private double[] cartPrices = new double[cartMax];
    private double subtotal = 0.00;
    private double taxAdded = 0.00;
    private double total = 0.00;
    private final double TAX = .075;
    private JLabel subtotalLabel = new JLabel("$0.00 - Subtotal (0) Items");
    private JLabel totalLabel = new JLabel("$0.00 - Total");
    private int currentIndex = -1;
    private GridLayout foodPanelGrid = new GridLayout(3, 2, 10, 10);
    private GridLayout foodPageGrid = new GridLayout(5, 2, 10, 10);
    private int numberOfItems = 0;
    private String[] deliveryOptions = new String[]{"In-Store Pickup",
            "Delivery ($4.00)"};

    //Colors and stuff
    Color darkColor = new Color(35, 35, 35);
    Color tabColors = new Color(255, 160, 0);

    //Pizza pricing and tracking variables
    final private double SMALL_PIZZA_PRICE = 10.99;
    final private double MED_PIZZA_PRICE = 12.99;
    final private double LARGE_PIZZA_PRICE = 14.99;
    final private double X_LARGE_PIZZA_PRICE = 16.99;
    final private double XX_LARGE_PIZZA_PRICE = 18.99;
    private double pizzaPrice = 14.99;
    private int numberOfMeats;
    private int numberOfFruits;
    private final double MEATS_PRICE = 1.99;
    private final double FRUITS_VEGGIES_PRICE = .50;
    private String nameOfPizza;
    private String sizeOfPizza;
    private String typeOfCrust;
    private String typeOfSauce;
    private String typeOfCheese;

    private JPanel pizzaButtonPanel;
    private JButton addPizzaToCart;

    //Size options
    private JRadioButton smallPizza = new JRadioButton("$10.99 Small 10\"");
    private JRadioButton medPizza = new JRadioButton("$12.99 Medium 12\"");
    private JRadioButton largePizza = new JRadioButton("$14.99 Large 14\"");
    private JRadioButton xLargePizza = new JRadioButton("$16.99 X-Large 16\"");
    private JRadioButton xxLargePizza = new JRadioButton("$18.99 XX-Large 18\"");

    //Crust options
    private JRadioButton thinCrust = new JRadioButton("Thin Crust");
    private JRadioButton thickCrust = new JRadioButton("Thick Crust");

    //Sauce options
    private JRadioButton tomatoSauce = new JRadioButton("Tomato Sauce");
    private JRadioButton marinaraSauce = new JRadioButton("Marinara Sauce");

    //Cheese options
    private JRadioButton mozzarella = new JRadioButton("Mozzarella");
    private JRadioButton provolone = new JRadioButton("Provolone");

    //Meat options
    private JCheckBox pepperoni = new JCheckBox("Pepperoni");
    private JCheckBox sausage = new JCheckBox("Sausage");
    private JCheckBox anchovy = new JCheckBox("Anchovy");
    private JCheckBox canadianBacon = new JCheckBox("Canadian Bacon");
    private JCheckBox ham = new JCheckBox("Ham");
    private JCheckBox bacon = new JCheckBox("Bacon");
    private JCheckBox chicken = new JCheckBox("Chicken");
    private JCheckBox italianSausage = new JCheckBox("Italian Sausage");

    //Fruits and veggie options
    private JCheckBox pineapple = new JCheckBox("Pineapple");
    private JCheckBox olives = new JCheckBox("Olives");
    private JCheckBox mushrooms = new JCheckBox("Mushrooms");
    private JCheckBox onions = new JCheckBox("Onions");
    private JCheckBox peppers = new JCheckBox("Peppers");

    /***MAIN METHOD***/
    public ThomasFinal() {
        //Setting up base page elements
        basePage = new JPanel(new BorderLayout(2, 2));
        headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        foodTabPane = new JTabbedPane();
        cartPanel = new JPanel(new BorderLayout(2, 2));
        pricesPanel = new JPanel(new BorderLayout(2, 2));
        foodTabPane.setMinimumSize(new Dimension(600, 400));

        //Setting up tab panels
        appetizersPanel = new JPanel();
        entreesPanel = new JPanel();
        pizzaPanel = new JPanel();
        saladPanel = new JPanel();
        dessertPanel = new JPanel();
        drinksPanel = new JPanel();

        //Setting up the tab layouts
        appetizersPanel.setLayout(foodPageGrid);
        entreesPanel.setLayout(foodPageGrid);
        saladPanel.setLayout(foodPageGrid);
        dessertPanel.setLayout(foodPageGrid);
        drinksPanel.setLayout(foodPageGrid);
        pizzaPanel.setLayout(new BorderLayout(2, 2));

        //Cart element setup
        cartList = new JList(cartItems);
        cartScroller = new JScrollPane(cartList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        cartScroller.setLayout(new ScrollPaneLayout());
        cartScroller.setPreferredSize(new Dimension(200, 1));
        cartList.setLayoutOrientation(JList.VERTICAL);
        removeCartButton = new JButton("Remove Item");

        //Price panel setup
        specialNotesTextArea = new JTextArea("Special Notes", 5, 10);
        specialNotesTextArea.setLineWrap(true);
        specialNotesTextArea.setSize(new Dimension(200, 400));
        specialNotesTextArea.setMaximumSize(new Dimension(200, 400));
        specialNotesScroll = new JScrollPane(specialNotesTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        deliveryOptionPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        deliveryOptionBox = new JComboBox(deliveryOptions);
        addressTextFieldPanel = new JPanel();
        addressTextField = new JTextField("Delivery Address");
        pickUpTimeTextField = new JTextField("Pick-Up Time");
        pickUpTimeTextField.setPreferredSize(new Dimension(590, 25));
        addressTextField.setPreferredSize(new Dimension(590, 25));
        addressTextField.setVisible(false);
        checkoutButton = new JButton("Checkout");

        //Setting colors and borders
        basePage.setBackground(darkColor);
        basePage.setBorder(new LineBorder(darkColor));
        headerPanel.setBackground(darkColor);
        foodTabPane.setBackground(tabColors);

        //Adding the image viewer
        JLabel imageViewer = new JLabel();
        ImageIcon logoIconBase = new ImageIcon(this.getClass().getResource(
                "resources/logo.png"));
        Image logoImage = logoIconBase.getImage();
        Image logoResized = logoImage.getScaledInstance(800, 69, Image.SCALE_SMOOTH);
        ImageIcon logoIconResized = new ImageIcon(logoResized);
        imageViewer.setIcon(logoIconResized);

        //Adding items into the basePage
        basePage.add(headerPanel, BorderLayout.NORTH);
        basePage.add(foodTabPane, BorderLayout.CENTER);
        basePage.add(cartPanel, BorderLayout.EAST);
        basePage.add(pricesPanel, BorderLayout.SOUTH);

        //Creating food tabs
        foodTabPane.addTab("Appetizers", appetizersPanel);
        foodTabPane.addTab("Entrees", entreesPanel);
        foodTabPane.addTab("Pizza", pizzaPanel);
        foodTabPane.addTab("Salads", saladPanel);
        foodTabPane.addTab("Desserts", dessertPanel);
        foodTabPane.addTab("Drinks", drinksPanel);

        //Adding appetizers
        appetizersPanel.add(createPepperoniBreadPanel());
        appetizersPanel.add(createGarlicBread());
        appetizersPanel.add(createCalamari());
        appetizersPanel.add(createGarlicKnots());
        appetizersPanel.add(createBread());
        appetizersPanel.add(createBruschetta());
        appetizersPanel.add(createMozzarellaSticks());

        //Adding Entrees
        entreesPanel.add(createChickenFettuccine());
        entreesPanel.add(createChickenMilanoPanel());
        entreesPanel.add(createSeafoodMedleyPanel());
        entreesPanel.add(createSkilletiniPanel());
        entreesPanel.add(createTortellini());
        entreesPanel.add(createLasanga());
        entreesPanel.add(createSpaghetti());
        entreesPanel.add(createMeatballSandwich());
        entreesPanel.add(createRavioli());

        //Adding Pizza Panel
        pizzaPanel.add(new JLabel("Build your own pizza", JLabel.CENTER),
                BorderLayout.NORTH);
        pizzaPanel.add(createPizzaPanel(), BorderLayout.CENTER);

        //Salads - No one really eats salad
        saladPanel.add(createSalad1());
        saladPanel.add(createSalad2());
        saladPanel.add(createFruitSalad());
        saladPanel.add(createMeatSalad());
        saladPanel.add(createSalad3());
        saladPanel.add(createLiterallyCroutons());
        saladPanel.add(createThatOneSalad());

        //Desserts panel - the good stuff
        dessertPanel.add(createBiscotti());
        dessertPanel.add(createTiramisu());
        dessertPanel.add(createChocolateMousse());
        dessertPanel.add(createZeppole());
        dessertPanel.add(createCannoli());
        dessertPanel.add(createIceCream());

        //Adding drinks
        drinksPanel.add(createDrPepper());
        drinksPanel.add(createCocaCola());
        drinksPanel.add(createPepsi());
        drinksPanel.add(createSprite());
        drinksPanel.add(createRootBeer());
        drinksPanel.add(createBeer());
        drinksPanel.add(createBelliniPanel());
        drinksPanel.add(createWine());
        drinksPanel.add(createAnotherWine());
        drinksPanel.add(createTheSpecial());

        //Adding items into the basePage header
        headerPanel.add(imageViewer);

        //Adding items into the basePage east
        cartPanel.add(cartScroller, BorderLayout.CENTER);
        cartPanel.add(removeCartButton, BorderLayout.NORTH);

        //Adding price panel stuff
        deliveryOptionPanel.add(deliveryOptionBox);
        deliveryOptionPanel.add(addressTextFieldPanel);
        addressTextFieldPanel.add(addressTextField);
        addressTextFieldPanel.add(pickUpTimeTextField);
        pricesPanel.add(deliveryOptionPanel);
        pricesPanel.add(specialNotesScroll, BorderLayout.WEST);
        pricesPanel.add(subtotalLabel, BorderLayout.NORTH);
        pricesPanel.add(checkoutButton, BorderLayout.EAST);

        //Add the basePage
        add(basePage);

        //Action listeners for the main page elements
        cartList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                currentIndex = cartList.getSelectedIndex();
            }
        });
        removeCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCartItem(currentIndex);
            }
        });
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(deliveryOptionBox.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null,
                            String.format("Number of Items - %d\n" +
                                    "Subtotal - $%.2f\n" +
                                    "Tax (7.5%%) - $%.2f\n" +
                                    "Total - $%.2f\n" +
                                    "In-Store Pickup Time - %s\n" +
                                    "Special Notes - %s",
                            numberOfItems, subtotal, taxAdded, total,
                            pickUpTimeTextField.getText(), specialNotesTextArea.getText()));
                }
                else if(deliveryOptionBox.getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null,
                            String.format("Number of Items - %d\n" +
                                    "Subtotal - $%.2f\n" +
                                    "Tax (7.5%%) - $%.2f\n" +
                                    "Delivery fee - $4.00\n"+
                                    "Total - $%.2f\n" +
                                    "Delivery Address - %s\n" +
                                    "Special Notes - %s",
                            numberOfItems, subtotal, taxAdded, total,
                            addressTextField.getText(), specialNotesTextArea.getText()));
                }
            }
        });
        deliveryOptionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(deliveryOptionBox.getSelectedIndex() == 0){
                    pickUpTimeTextField.setVisible(true);
                    addressTextField.setVisible(false);
                }else if(deliveryOptionBox.getSelectedIndex() == 1){
                    pickUpTimeTextField.setVisible(false);
                    addressTextField.setVisible(true);
                }
                pricesPanel.updateUI();
                calculatePrice();
            }
        });
        pickUpTimeTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!pickUpTimeTextField.getText().matches(
                        "(^([0-9]|[0-1][0-9]|[2][0-3]):([0-5][0-9])$)|(^([0-9]|[1][0-9]|[2][0-3])$)")){
                    JOptionPane.showMessageDialog(null, "Invalid Time");
                }
            }
        });
    }

    /***REMOVE ITEM FROM CART***/
    public void removeCartItem(int index) {
        if (numberOfItems > 0 && index > -1 && cartItems[index] != null &&
                cartPrices[index] != 0) {
            cartItems[index] = null;
            cartPrices[index] = 0;
            numberOfItems--;
            reformatList();
            calculatePrice();
            currentIndex = 0;
            cartList.setSelectedIndex(currentIndex);
        }
    }

    /***REFORMAT LIST***/
    private void reformatList() {
        for (int i = 0; i < cartMax; i++) {
            if (cartItems[i] == null && i + 1 != cartMax) {
                cartItems[i] = cartItems[i + 1];
                cartItems[i + 1] = null;

                cartPrices[i] = cartPrices[i + 1];
                cartPrices[i + 1] = 0;
            }
        }

        cartList.updateUI();
    }

    /***ADDING ITEMS TO CART SECTION***/
    private void addItemToCart(String item, double itemPrice) {
        String itemFormatted = String.format("$%.2f - %s", itemPrice, item);
        for (int i = 0; i <= cartMax; i++) {
            if (i == cartMax) {
                JOptionPane.showMessageDialog(null, String.format(
                        "Error Adding Items to Cart\n" +
                        "No More Than %d Items", cartMax));
                break;
            } else if (cartItems[i] == null && cartPrices[i] == 0) {
                cartItems[i] = itemFormatted;
                cartPrices[i] = itemPrice;
                numberOfItems++;
                break;
            }
        }

        calculatePrice();
    }

    /***RECALCULATE TOTAL PRICE***/
    private void calculatePrice() {
        subtotal = 0;
        total = 0;

        for (int i = 0; i < cartMax; i++) {
            subtotal += cartPrices[i];
        }

        taxAdded = subtotal * TAX;
        total = subtotal +taxAdded;

        if(deliveryOptionBox.getSelectedIndex() == 1){
            total += 4;
        }

        subtotalLabel.setText(String.format("$%.2f - Subtotal (%d) Items", subtotal, numberOfItems));
        totalLabel.setText(String.format("$%.2f - Total",
                total));
        cartList.updateUI();
    }

    /***CREATE FOOD ITEM***/
    private JPanel createFoodItem(final String name, String description, final double price) {
        //Create food item allows me to use one action listener for every food item
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(2, 2));
        JButton button = new JButton(String.format("$%.2f", price));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setFont(new Font("Default", Font.BOLD, 15));

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Default", Font.PLAIN, 10));

        panel.setBorder(new LineBorder(darkColor));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToCart(name, price);
            }
        });

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(descLabel, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }
    /***END CREATE FOOD ITEM***/

    /***FOOD PANELS***/
    //<editor-fold desc="Food Panels">
    //In here we have all of our food panels and their related variables.
    private JPanel createChickenFettuccine() {
        String name = "Chicken Fettuccine";
        String description = "Noodles, chicken, sauce.  That's it.";
        double price = 12.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createPepperoniBreadPanel() {
        String name = "Pepperoni Bread";
        String description = "It's bread.  With pepperonis.";
        double price = 8.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createChickenMilanoPanel() {
        String name = "Chicken Milano";
        String description = "Chicken with some sauces and cheese.";
        double price = 13.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createSeafoodMedleyPanel() {
        String name = "Seafood Medley";
        String description = "Shrimp, scallops, calamari, cheese, and whatever else.";
        double price = 14.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createBelliniPanel() {
        String name = "Bellini";
        String description = "Wine and rum.";
        double price = 7.50;

        return createFoodItem(name, description, price);
    }

    private JPanel createSkilletiniPanel() {
        String name = "Skilletini";
        String description = "Peppers and onions with a hint of meat sprinkled in.";
        double price = 13.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createDrPepper() {
        String name = "Dr. Pepper";
        String description = "Heaven's nectar";
        double price = 3.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createCocaCola() {
        String name = "Coca Cola";
        String description = "Not as good as Dr. Pepper";
        double price = 3.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createPepsi() {
        String name = "Pepsi";
        String description = "No Pepsi is not okay.";
        double price = 3.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createSprite() {
        String name = "Sprite";
        String description = "It helps when you're sick.";
        double price = 3.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createRootBeer() {
        String name = "Root Beer";
        String description = "\"Mom look I'm drinking beer!\"";
        double price = 3.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createWine() {
        String name = "Wine";
        String description = "You couldn't tell how cheap it was.";
        double price = 12.45;

        return createFoodItem(name, description, price);
    }

    private JPanel createBeer() {
        String name = "Beer";
        String description = "\"Mom look I'm drinking beer!\"";
        double price = 5.20;

        return createFoodItem(name, description, price);
    }

    private JPanel createAnotherWine() {
        String name = "Another Wine";
        String description = "It tastes the same.";
        double price = 16.43;

        return createFoodItem(name, description, price);
    }

    private JPanel createTheSpecial() {
        String name = "The Special";
        String description = "It's the same thing every time";
        double price = 21.37;

        return createFoodItem(name, description, price);
    }

    private JPanel createSalad1() {
        String name = "Salad 1";
        String description = "It's got some green stuff, it's healthy.";
        double price = 5.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createSalad2() {
        String name = "Salad 2";
        String description = "Leaves, croutons, ranch, olives.";
        double price = 7.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createFruitSalad() {
        String name = "Fruit Salad";
        String description = "Literally a bunch of fruit thrown into a bowl.";
        double price = 2.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createMeatSalad() {
        String name = "Meat Salad";
        String description = "Yes we can call this a salad.";
        double price = 12.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createSalad3() {
        String name = "Salad 3";
        String description = "You don't actually want a salad.";
        double price = 5.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createLiterallyCroutons() {
        String name = "Literally Just Croutons";
        String description = "We know that's all you eat anyways.";
        double price = 1.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createThatOneSalad() {
        String name = "That One Salad";
        String description = "You know, that one.";
        double price = 8.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createTiramisu() {
        String name = "Tiramisu";
        String description = "The only italian word you know.";
        double price = 8.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createZeppole() {
        String name = "Zeppole";
        String description = "Doughnut-holes.";
        double price = 6.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createCannoli() {
        String name = "Cannoli";
        String description = "It's got chocolate and cheese.";
        double price = 5.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createBiscotti() {
        String name = "Biscotti";
        String description = "Rectangular cookies.";
        double price = 12.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createChocolateMousse() {
        String name = "Chocolate Mousse";
        String description = "Chocolate, chocoloate, and more chocolate.";
        double price = 16.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createIceCream() {
        String name = "Ice Cream";
        String description = "Frozen liquid from a cow's udders.";
        double price = 1.20;

        return createFoodItem(name, description, price);
    }

    private JPanel createCalamari() {
        String name = "Calamari";
        String description = "Fried tentacles and sauce";
        double price = 9.34;

        return createFoodItem(name, description, price);
    }

    private JPanel createGarlicBread() {
        String name = "Garlic Bread";
        String description = "Oman pls to halp am not good with compooter";
        double price = 5.50;

        return createFoodItem(name, description, price);
    }

    private JPanel createGarlicKnots() {
        String name = "Garlic Knots";
        String description = "Special Garlic Bread";
        double price = 7.47;

        return createFoodItem(name, description, price);
    }

    private JPanel createBread() {
        String name = "Bread";
        String description = "Special Italian bread";
        double price = 3.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createBruschetta() {
        String name = "Bruschetta";
        String description = "Even specialer garlic bread.";
        double price = 5.99;

        return createFoodItem(name, description, price);
    }

    private JPanel createMozzarellaSticks() {
        String name = "Mozzarella Sticks";
        String description = "Fried cheese and sauce.";
        double price = 7.98;

        return createFoodItem(name, description, price);
    }

    private JPanel createLasanga() {
        String name = "Lasanga";
        String description = "Garfield loved it, and so do you.";
        double price = 13.45;

        return createFoodItem(name, description, price);
    }

    private JPanel createSpaghetti() {
        String name = "Spaghetti";
        String description = "It won't play out like it did in the dog movie.";
        double price = 9.73;

        return createFoodItem(name, description, price);
    }

    private JPanel createTortellini() {
        String name = "Tortellini";
        String description = "A fancier pizza roll.";
        double price = 6.98;

        return createFoodItem(name, description, price);
    }

    private JPanel createMeatballSandwich() {
        String name = "Meatball Sandwich";
        String description = "Meatballs in bread";
        double price = 5.49;

        return createFoodItem(name, description, price);
    }

    private JPanel createRavioli() {
        String name = "Ravioli";
        String description = "Chef Boyardee's specialty..";
        double price = 10.28;

        return createFoodItem(name, description, price);
    }
    //</editor-fold desc = "Food Panels">
    /***END FOOD PANELS***/

    /***BUILD YOUR OWN PIZZA***/
    private JPanel createPizzaPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(2, 2));
        JPanel panel = new JPanel();
        panel.setLayout(foodPanelGrid);
        pizzaButtonPanel = new JPanel();
        addPizzaToCart = new JButton(String.format("$%.2f Add to cart", pizzaPrice));

        //Button groups for single selection items
        ButtonGroup sizes = new ButtonGroup();
        ButtonGroup crusts = new ButtonGroup();
        ButtonGroup sauces = new ButtonGroup();
        ButtonGroup cheeses = new ButtonGroup();

        //Pizza size
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(new LineBorder(darkColor));
        sizePanel.setLayout(new BorderLayout(2, 2));

        JPanel sizeContent = new JPanel();
        sizeContent.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

        JLabel sizeTitle = new JLabel("Sizes");
        sizeTitle.setHorizontalAlignment(JLabel.CENTER);
        sizeTitle.setFont(new Font("Default", Font.BOLD, 12));

        sizes.add(smallPizza);
        sizes.add(medPizza);
        sizes.add(largePizza);
        sizes.add(xLargePizza);
        sizes.add(xxLargePizza);

        sizeContent.add(smallPizza);
        sizeContent.add(medPizza);
        sizeContent.add(largePizza);
        sizeContent.add(xLargePizza);
        sizeContent.add(xxLargePizza);

        sizePanel.add(sizeTitle, BorderLayout.NORTH);
        sizePanel.add(sizeContent, BorderLayout.CENTER);

        //Crust
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(new LineBorder(darkColor));
        crustPanel.setLayout(new BorderLayout(2, 2));

        JPanel crustContent = new JPanel();
        crustContent.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

        JLabel crustTitle = new JLabel("Crusts");
        crustTitle.setHorizontalAlignment(JLabel.CENTER);
        crustTitle.setFont(new Font("Default", Font.BOLD, 12));

        crusts.add(thinCrust);
        crusts.add(thickCrust);

        crustContent.add(thinCrust);
        crustContent.add(thickCrust);

        crustPanel.add(crustTitle, BorderLayout.NORTH);
        crustPanel.add(crustContent, BorderLayout.CENTER);

        //Sauces
        JPanel saucePanel = new JPanel();
        saucePanel.setBorder(new LineBorder(darkColor));
        saucePanel.setLayout(new BorderLayout(2, 2));

        JPanel sauceContent = new JPanel();
        sauceContent.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

        JLabel sauceTitle = new JLabel("Sauces");
        sauceTitle.setHorizontalAlignment(JLabel.CENTER);
        sauceTitle.setFont(new Font("Default", Font.BOLD, 12));

        sauces.add(tomatoSauce);
        sauces.add(marinaraSauce);

        sauceContent.add(tomatoSauce);
        sauceContent.add(marinaraSauce);

        saucePanel.add(sauceTitle, BorderLayout.NORTH);
        saucePanel.add(sauceContent, BorderLayout.CENTER);

        //Cheeses
        JPanel cheesePanel = new JPanel();
        cheesePanel.setBorder(new LineBorder(darkColor));
        cheesePanel.setLayout(new BorderLayout(2, 2));

        JPanel cheeseContent = new JPanel();
        cheeseContent.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

        JLabel cheeseTitle = new JLabel("Cheese");
        cheeseTitle.setHorizontalAlignment(JLabel.CENTER);
        cheeseTitle.setFont(new Font("Default", Font.BOLD, 12));

        cheeses.add(mozzarella);
        cheeses.add(provolone);

        cheeseContent.add(mozzarella);
        cheeseContent.add(provolone);

        cheesePanel.add(cheeseTitle, BorderLayout.NORTH);
        cheesePanel.add(cheeseContent, BorderLayout.CENTER);

        //Meats
        JPanel meatPanel = new JPanel();
        meatPanel.setBorder(new LineBorder(darkColor));
        meatPanel.setLayout(new BorderLayout(2, 2));

        JPanel meatContent = new JPanel();
        meatContent.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

        JLabel meatTitle = new JLabel("Meats - $1.99 Each");
        meatTitle.setHorizontalAlignment(JLabel.CENTER);
        meatTitle.setFont(new Font("Default", Font.BOLD, 12));

        meatContent.add(pepperoni);
        meatContent.add(sausage);
        meatContent.add(anchovy);
        meatContent.add(canadianBacon);
        meatContent.add(ham);
        meatContent.add(bacon);
        meatContent.add(chicken);
        meatContent.add(italianSausage);

        meatPanel.add(meatTitle, BorderLayout.NORTH);
        meatPanel.add(meatContent, BorderLayout.CENTER);

        //Fruits and Veggies
        JPanel fruitsVeggiesPanel = new JPanel();
        fruitsVeggiesPanel.setBorder(new LineBorder(darkColor));
        fruitsVeggiesPanel.setLayout(new BorderLayout(10, 10));

        JPanel fruitsVeggiesContent = new JPanel();
        fruitsVeggiesContent.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

        JLabel fruitsVeggiesTitle = new JLabel("Fruits and Veggies - $0.50 Each");
        fruitsVeggiesTitle.setHorizontalAlignment((JLabel.CENTER));
        fruitsVeggiesTitle.setFont(new Font("Default", Font.BOLD, 12));

        fruitsVeggiesContent.add(pineapple);
        fruitsVeggiesContent.add(olives);
        fruitsVeggiesContent.add(mushrooms);
        fruitsVeggiesContent.add(onions);
        fruitsVeggiesContent.add(peppers);

        fruitsVeggiesPanel.add(fruitsVeggiesTitle, BorderLayout.NORTH);
        fruitsVeggiesPanel.add(fruitsVeggiesContent, BorderLayout.CENTER);

        panel.add(sizePanel);
        panel.add(crustPanel);
        panel.add(saucePanel);
        panel.add(cheesePanel);
        panel.add(meatPanel);
        panel.add(fruitsVeggiesPanel);

        //Adding items to cart
        pizzaButtonPanel.add(addPizzaToCart);

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(pizzaButtonPanel, BorderLayout.SOUTH);

        //<editor-fold desc = "Pizza Action Listeners">
        //Action Listeners.  There are lots of these.
        smallPizza.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        medPizza.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        largePizza.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        xLargePizza.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        xxLargePizza.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        thickCrust.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        thinCrust.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        mozzarella.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        provolone.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        tomatoSauce.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        marinaraSauce.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                recalculatePizza();
            }
        });
        pepperoni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        sausage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        ham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        canadianBacon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        anchovy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        bacon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        chicken.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        italianSausage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        pineapple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        olives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        mushrooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        onions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        peppers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculatePizza();
            }
        });
        //</editor-fold desc = "End Pizza Action Listeners>"

        thinCrust.setSelected(true);
        largePizza.setSelected(true);
        mozzarella.setSelected(true);
        tomatoSauce.setSelected(true);

        addPizzaToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToCart(nameOfPizza, pizzaPrice);
            }
        });

        return mainPanel;
    }

    /***RECALCULATE PIZZA***/
    private void recalculatePizza() {
        numberOfMeats = 0;
        numberOfFruits = 0;
        pizzaPrice = 0;
        typeOfCrust = "";

        //Sizes
        //<editor-fold desc="If statements for buttons">
        if (smallPizza.isSelected()) {
            pizzaPrice = SMALL_PIZZA_PRICE;
            sizeOfPizza = "Small";
        } else if (medPizza.isSelected()) {
            pizzaPrice = MED_PIZZA_PRICE;
            sizeOfPizza = "Medium";
        } else if (largePizza.isSelected()) {
            pizzaPrice = LARGE_PIZZA_PRICE;
            sizeOfPizza = "Large";
        } else if (xLargePizza.isSelected()) {
            pizzaPrice = X_LARGE_PIZZA_PRICE;
            sizeOfPizza = "X-Large";
        } else if (xxLargePizza.isSelected()) {
            pizzaPrice = XX_LARGE_PIZZA_PRICE;
            sizeOfPizza = "XX-Large";
        }

        //Types of crust
        if (thinCrust.isSelected()) {
            typeOfCrust = "Thin Crust";
        } else if (thickCrust.isSelected()) {
            typeOfCrust = "Thick Crust";
        }

        //Types of Sauce
        if (tomatoSauce.isSelected()) {
            typeOfSauce = "Tomato Sauce";
        } else if (marinaraSauce.isSelected()) {
            typeOfSauce = "Marinara Sauce";
        }

        //Types of cheese
        if (mozzarella.isSelected()) {
            typeOfCheese = "Mozzarella";
        } else if (provolone.isSelected()) {
            typeOfCheese = "Provolone";
        }

        //Adding to numberOfMeats
        if (pepperoni.isSelected()) {
            numberOfMeats++;
        }
        if (sausage.isSelected()) {
            numberOfMeats++;
        }
        if (anchovy.isSelected()) {
            numberOfMeats++;
        }
        if (canadianBacon.isSelected()) {
            numberOfMeats++;
        }
        if (ham.isSelected()) {
            numberOfMeats++;
        }
        if (bacon.isSelected()) {
            numberOfMeats++;
        }
        if (chicken.isSelected()) {
            numberOfMeats++;
        }
        if (italianSausage.isSelected()) {
            numberOfMeats++;
        }

        //Adding to numberOfFruits
        if (pineapple.isSelected()) {
            numberOfFruits++;
        }
        if (olives.isSelected()) {
            numberOfFruits++;
        }
        if (mushrooms.isSelected()) {
            numberOfFruits++;
        }
        if (onions.isSelected()) {
            numberOfFruits++;
        }
        if (peppers.isSelected()){
            numberOfFruits++;
        }
        //</editor-fold>

        pizzaPrice += (MEATS_PRICE * numberOfMeats) + (FRUITS_VEGGIES_PRICE * numberOfFruits);

        if (numberOfMeats > 0) {
            nameOfPizza = String.format("%s, %s, %s %d Meat Pizza with %s",
                    sizeOfPizza, typeOfCrust, typeOfCheese, numberOfMeats, typeOfSauce);
        } else if (numberOfMeats == 0) {
            nameOfPizza = String.format("%s, %s, %s Pizza with %s",
                    sizeOfPizza, typeOfCrust, typeOfCheese, typeOfSauce);
        }

        addPizzaToCart.setText(String.format("$%.2f Add to cart", pizzaPrice));
        addPizzaToCart.updateUI();

    }
    /***END BUILD YOUR OWN PIZZA***/
}