import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class TreeFrame extends JFrame implements ActionListener
{
   int WIDTH=1000;
   int HEIGHT=1000;
   
   protected static String startText="START";
   protected JButton start = new JButton(startText);
   
   protected JLabel numStarsLabel= new JLabel("Number of Stars");
   protected JTextField numStarsField = new JTextField("2000",4);
   
   protected JButton rotate45 = new JButton("Rotate 22.5 deg");
   protected JButton restart = new JButton("Restart");
   protected JLabel timeStepLabel = new JLabel("Time Step (sec)");
   protected JTextField timeStepField = new JTextField("5e10",4);
   protected JButton blueC = new JButton("Blue X");
   protected JButton greenC = new JButton("Green O");
   protected JButton redC = new JButton("Red X");
   
   public TreeFrame()
   {
      super("N-Body Threaded Barnes-Hut Tree Method");
      setSize(WIDTH,(HEIGHT+50));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      TreeJPanel panel1= new TreeJPanel();
      JPanel panel2= new JPanel();
      Container con = getContentPane();
      con.setLayout(new FlowLayout());
      con.add(panel2);
      con.add(panel1);
      panel2.add(start);
         start.addActionListener(this);
      panel2.add(restart);
         restart.addActionListener(this);
      panel2.add(numStarsLabel);
      panel2.add(numStarsField);
         numStarsField.addActionListener(this);
      panel2.add(rotate45);
         rotate45.addActionListener(this);
      panel2.add(timeStepLabel);
         panel2.add(timeStepField);
         timeStepField.addActionListener(this);
      panel2.add(redC);
         redC.addActionListener(this);   
         panel2.add(blueC);
            blueC.addActionListener(this);
         panel2.add(greenC);
            greenC.addActionListener(this);
         
      panel1.setPreferredSize(new Dimension(1000, 1000));
      setLocationRelativeTo(null);
      setVisible(true);
   }//end constructor
   
   public void actionPerformed(ActionEvent e)
   {
      Object source =e.getSource();
      if(source==start)
      {
         if(startText.equals("START"))
         {
            startText="STOP";
            TreeJPanel.setRunning(true);
            start.setText(startText);
         }
         else
         {
            startText="START";
            TreeJPanel.setRunning(false);
            start.setText(startText);
         }
      }//end start button
      
      if(source==numStarsField)
      {
         startText="STOP";
         start.setText(startText);
         TreeJPanel.setRunning(false);
         try{
            TreeJPanel.n= Integer.parseInt(numStarsField.getText());
         }
         catch(Exception err)
         {
         }
         if (TreeJPanel.n>15000)
         {
            TreeJPanel.n=15000;
            numStarsField.setText("15000");  
         }
         TreeJPanel.particle= Arrays.copyOf(TreeJPanel.particle,TreeJPanel.n);
         TreeJPanel.initParticles(TreeJPanel.n);
         TreeJPanel.setRunning(true);
           
      }//end number of stars field
      
      if(source== timeStepField)
      {
         try
         {
            TreeJPanel.timeStep=Double.parseDouble(timeStepField.getText());
         }
         catch (Exception err)
         {
            TreeJPanel.timeStep=5e10;
         }
      }
      if(source==rotate45)
      {  
         if (startText.equals("STOP"))
         {
            TreeJPanel.setRunning(false);
         }
         TreeJPanel.translate45();
         if (startText.equals("STOP"))
         {
            TreeJPanel.setRunning(true);
         }
      }//end rotate45
      
      
      // restart button
      if(source==restart)
      {
         if(TreeJPanel.getIsRunning())
         {
            TreeJPanel.setRunning(false);
         }
         TreeJPanel.initParticles(TreeJPanel.n);
         startText="STOP";
         start.setText(startText);
         TreeJPanel.setRunning(true);      
      }
      
      //color buttons
      if(source==blueC)
      {
         if (TreeJPanel.getColorBlue()==true)
         {
            TreeJPanel.setColorBlue(false);
            blueC.setText("Blue O");
         }
         else
         {
            TreeJPanel.setColorBlue(true);
            blueC.setText("Blue X");
         }
      }
      if(source==greenC)
      {
         if (TreeJPanel.getColorGreen()==true)
         {
            TreeJPanel.setColorGreen(false);
            greenC.setText("Green O");
         }
         else
         {
            TreeJPanel.setColorGreen(true);
            greenC.setText("Green X");
         }
      }
      if(source==redC)
      {
         if(TreeJPanel.getColorRed()==true)
         {
            TreeJPanel.setColorRed(false);
            redC.setText("Red O");
         }
         else
         {
            TreeJPanel.setColorRed(true);
            redC.setText("Red X");
         }
      }
   }//end actionPerformed
}