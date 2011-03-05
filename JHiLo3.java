/************************************************
Author: John McGonegal
Date: 5/11/09

Description:
HiLo card game program
************************************************/
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;  // import advanced graphics (extended) package

public class JHiLo3 extends JApplet implements ActionListener
{
	// path must end with slash
	private String imagepath = "";
	private AudioClip winSound, loseSound, shuffleSound, welcomeSound, extraSound;
	// eclipse ftw
	private static final long serialVersionUID = 1L;

	JPanel panel = new JPanel();
	JButton deal = new JButton("Deal");
	JButton shuffle = new JButton("Shuffle");
	JLabel player = new JLabel();
	JLabel house = new JLabel();
	JLabel lblPlayer = new JLabel("Player Card");
	JLabel title = new JLabel("Welcome to HiLo!");
	JLabel lblHouse = new JLabel("House Card");
	JLabel wins = new JLabel("Player Wins: 0");
	JLabel losses = new JLabel("House Wins: 0");
	JLabel message = new JLabel("Welcome to the Hi Lo Table!");
	PlayDeck MyPlayDeck = new PlayDeck();
	Card PlayerCard;
	Card HouseCard;
	int PlayerScore = 0;
	int HouseScore = 0;
	public void init()
	{
		// lets make it ugly, probably wont work online
		if (this.getCodeBase().toString().indexOf("file:/") != -1) {
			imagepath = this.getCodeBase().toString().substring(this.getCodeBase().toString().indexOf("file:/")+6) + "cards/";
		}
		else
		{
			imagepath = this.getCodeBase().toString() + "cards/";
		}
		// get game ready
		MyPlayDeck.shuffle();
		
		Container container = this.getContentPane();
		// add events
		deal.addActionListener(this);
		shuffle.addActionListener(this);
		// setup a 6 row container with 2 columns
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 0;
		gbc.ipady = 10;
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		panel.add(deal, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		panel.add(shuffle, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblPlayer, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		lblHouse.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblHouse, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(player, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(house, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panel.add(wins, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		panel.add(losses, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		panel.add(message, gbc);
		
		// setup fonts
		message.setFont(new Font("Helvetica", Font.BOLD, 13));
		lblHouse.setFont(new Font("Helvetica", Font.BOLD, 14));
		lblPlayer.setFont(new Font("Helvetica", Font.BOLD, 14));
		title.setFont(new Font("Arial", Font.BOLD, 16));
		// add grid to frame
		container.add(panel);
		
		// setup cards
		player.setIcon(new javax.swing.ImageIcon(imagepath + "Cardback.PNG"));
		player.setSize(96,136);
		house.setIcon(new javax.swing.ImageIcon(imagepath + "Cardback.PNG"));
		house.setSize(96,136);
		
		// load our sounds
		winSound = getAudioClip(this.getDocumentBase(), this.getParameter ("PlayerWin"));
		loseSound = getAudioClip(this.getDocumentBase(), this.getParameter ("HouseWin"));
		shuffleSound = getAudioClip(this.getDocumentBase(), this.getParameter ("Shuffle"));
		welcomeSound = getAudioClip(this.getDocumentBase(), this.getParameter ("Welcome"));
		extraSound = getAudioClip(this.getDocumentBase(), this.getParameter ("ExtraSound"));
		
		this.setVisible(true);
	}
	public void start(){
		welcomeSound.play();
	}

	public void stop(){
		extraSound.play();
		// 5 seconds of delay for the sound to play
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
   	}
	public void updateGame()
	{
		// mmm singleton
		NumberFormat nf = NumberFormat.getInstance();
		// we want 2 digits so 1 = 01
		nf.setMinimumIntegerDigits(2);
		// create filenames from cards
		player.setIcon(new javax.swing.ImageIcon(imagepath + Card.suitToString(PlayerCard.getSuit())+"_"+nf.format(PlayerCard.getNumber())+".PNG"));
		house.setIcon(new javax.swing.ImageIcon(imagepath + Card.suitToString(HouseCard.getSuit())+"_"+nf.format(HouseCard.getNumber())+".PNG"));
		// set scores
		wins.setText("Player Wins: " + PlayerScore);
		losses.setText("House Wins: " + HouseScore);
	}
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		// dealer button
		if(source == deal)
		{
			PlayerCard = MyPlayDeck.dealCard();
			HouseCard = MyPlayDeck.dealCard();
			
			if (PlayerCard.getNumber() > HouseCard.getNumber()) 
			{
				message.setText("Player wins, congratulations!");
				PlayerScore++;
				this.updateGame();
				winSound.play();
			}
			else
			{
				message.setText("House wins");
				HouseScore++;
				this.updateGame();
				loseSound.play();
			}
		}
		// shuffle button
		else if(source == shuffle)
		{
			message.setText("Deck Shuffled... Lets Play!");
			PlayerScore = HouseScore = 0;
			this.updateGame();
			MyPlayDeck.shuffle();
			player.setIcon(new javax.swing.ImageIcon(imagepath + "Cardback.PNG"));
			house.setIcon(new javax.swing.ImageIcon(imagepath + "Cardback.PNG"));
			shuffleSound.play();
		}
	}
} //end of class HiLo


