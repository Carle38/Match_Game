
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    import javax.swing.SwingConstants;
    import javax.swing.Timer;
    import java.awt.BorderLayout;
    import java.awt.FlowLayout;
    import java.awt.Font;
    import java.awt.GridLayout;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseEvent;
    import java.awt.event.MouseListener;
    import java.util.Random;
 
    /** This program is a memory matching game.
     * Everything is contained in this class "MatchGame".
     * A 4x4 grid represents 16 cards that the user can click on
     * and reveal the face value of the card. The object of the 
     * game is to find all the matching pairs.
     * 
     * @author carl Estabrook
     * Project#3
     * Class:CS585 Java
     *
     */
 public class MatchGame extends JFrame implements ActionListener 
 {
  private final int WIDTH = 400;
  private final int HEIGHT = 400;
  private Card[][] ca = new Card[4][4];
  private Card[] hold = new Card[1];
  private JPanel bigPanel;
  private static int cardsShowing = 0;
  
  /**Constructor of MatchGame sets up the window
   * layout and creates the main panel for the 4x4 
   * GridLayout. 
   */
  public MatchGame()
  {
   super();
   setSize(WIDTH, HEIGHT);
   setTitle("Matching Game");
   hold[0] = null;
   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   setLayout(new BorderLayout());
   
   bigPanel = new JPanel();
   bigPanel.setLayout(new GridLayout(4, 4));
   add(bigPanel, BorderLayout.CENTER);
   
   JPanel buttonPanel = new JPanel();
   buttonPanel.setLayout(new FlowLayout());
   
   JButton newGame = new JButton("New Game");
   newGame.addActionListener(this);
   buttonPanel.add(newGame);
   
   add(buttonPanel, BorderLayout.SOUTH);
   
   setUpGame();
  }
  
  
//  @Override
  /** actionPerformed for the "New game" button.
   * Calls shuffle(). Sets up new game.
   */
  public void actionPerformed(ActionEvent arg0)
  { 
    shuffle();  
  }
  
  /** method toString() returns string of all
    * cards in grid in order from left to right, top to bottom.
    */ 
  public String toString()
  {
    String st = "Cards in grid = ";
    
     for(int row = 0; row<ca.length; row++)
     {
        for(int column = 0; column<ca[row].length; column++)
        {
          st = st + ca[row][column].getValue()+", ";
        } 
     }
        
    return st;
  }
  /** equals() returns true if MatchGame mg has cards in same order as this
    */
  public boolean equals(MatchGame mg)
  {
    boolean eq = true;
    boolean temp;
     for(int row = 0; row<ca.length; row++)
     {
        for(int column = 0; column<ca[row].length; column++)
        {
         temp = ca[row][column].getValue().equals(mg.ca[row][column].getValue());
         if(temp == false)
           eq = false;    
        } 
     }
    return eq;
  }
  
  /**Inner class Card represents each card and contains
   * each panel that listens for the mouse click.
   * This class also keeps track of the game logic
   * in the mouseClicked method.
   * @author carl
   *
   */
  private class Card implements MouseListener 
  {
   private int face;
   private String value;
   private JPanel panel;
   private JLabel label;
   private boolean match;
   private Timer timer;
   
   /**Constructor for card. Sets up the panel, label and 
    * mouseListener for each card.
    * @param v is face value of the card.
    */
   private Card(int v)
   {
    face = 0;
    value = Integer.toString(v);
    match = false;
    panel = new JPanel();
    panel.setLayout(new BorderLayout());
    
    label = new JLabel(display(), SwingConstants.CENTER);
    label.setFont(new Font("Serif", Font.BOLD, 20));

    panel.setBackground(getBackground());
    panel.add(label, BorderLayout.CENTER);
    panel.addMouseListener(this); 
    
    /**Timer controls the 2 second delay after 2 non-matching
     * cards are displayed then turned over.
     */
    timer = new Timer(2000, new ActionListener() 
    {
        public void actionPerformed(ActionEvent evt) 
        {
         if(hold[0] != null)
         {
          hold[0].setFace(0);
          hold[0].label.setText(hold[0].display());
          face = 0;
          label.setText(display());
          hold[0] = null;
          cardsShowing = 0;
          timer.stop();       
         }          
        }    
    });
   }
   
   public int getFace()
   {
    return face;
   }
   
   public String getValue()
   {
    return value;
   }
   
   public void setValue(String v)
   {
    value = v;
   }
   
   public void setFace(int f)
   {
       face = f;
   }
   /** Will display either side of card
    * based on value of "face".
    * @return
    */
   public String display()
   {
    if(face == 0)
     return "*";
    else
     return value;
   }
   
   public String toString()
   {
       return value;
   }
   
   public JPanel getPanel()
   {
    return panel;
   }
   /** Tests if 2 cards have the same face value.
    * 
    * @param c
    * @return
    */
   public boolean equals(Card c)
   {
    return (this.value.equals(c.value));
   }
   
  // @Override
   /** Listens for mouse click on individual panels representing each card.
    * This method also keeps track of game logic.
    */
   public void mouseClicked(MouseEvent arg0)
   {
    if(hold[0]!=null)
    {
     if((hold[0].panel.equals(panel)) || (match == true))
      return;
    }
    
    if((timer.isRunning() == false) && (cardsShowing < 2))
    {  
     
     if((match == false) && (hold[0] == null)&& (face!=1))
     {
      face = 1;
      hold[0] = this;
      label.setText(display());
      cardsShowing++;
     }
     else if(hold[0] != null)
     {
        if(hold[0].equals(this))
        {
         face = 1;
         label.setText(display());
         cardsShowing++;
         match = true;
         hold[0].match = true;
         hold[0] = null;
         cardsShowing = 0;
        }
        else
        {
         face = 1;
         label.setText(display());// displays value of card
         cardsShowing++;
         timer.start();   
        } 
     } 
    } 
   }

  // @Override
   public void mouseEntered(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
   }

  // @Override
   public void mouseExited(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
   }

  // @Override
   public void mousePressed(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
   }

 //  @Override
   public void mouseReleased(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
   } 
  }
  
  /** Sets up initial starting state of game.
   * 
   */
  private void setUpGame()
  { 
   int row;
   int column;
   int value = 1;
   
   for( row = 0; row<4; row++)
   {  
    for( column = 0; column<4; column++)
    {
     ca[row][column] = new Card(value);
     bigPanel.add(ca[row][column].getPanel());
     ++value;
     if(value == 9)
      value = 1; 
    } 
   } 
   shuffle();
  }
  /**This method will mix up the order of the cards
   * randomly. It also does some new game initialization.
   */
  private void shuffle()
  {
   String one;
   String two;
   int row1;
   int column1;
   int row2;
   int column2;
   Random generator = new Random();
   hold[0] = null;
   cardsShowing = 0;
   
   for(int i = 0; i<100; i++)
   {
    row1 = generator.nextInt(4);
    column1 = generator.nextInt(4);
    row2 = generator.nextInt(4);
    column2 = generator.nextInt(4);
    one = ca[row1][column1].getValue();
    two = ca[row2][column2].getValue();
    ca[row1][column1].setValue(two);
    ca[row2][column2].setValue(one); 
   }
   
   for(int row = 0; row<4; row++)
   {  
    
    for(int column = 0; column<4; column++)
    {
     ca[row][column].setFace(0);
     ca[row][column].label.setText(ca[row][column].display());
     ca[row][column].match = false; 
    } 
   } 
  } 
 }








