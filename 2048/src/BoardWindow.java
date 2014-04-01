import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.*;


public class BoardWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Tile tiles;
	
	public BoardWindow()
	{
		setTitle("2048");
		setSize(750, 750);
		setLocation(200, 0);
				
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);

		tiles = new Tile();
		tiles.setBorder(BorderFactory.createLineBorder(Color.black, 5));		
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
    	layout.setConstraints(tiles, constraints);
    	add(tiles);
	}
	
	public void setTiles(Board aBoard)
	{
		tiles.setTiles(aBoard);
	}

}

class Tile extends JPanel
{		
	private static final long serialVersionUID = -1123519161954076485L;
		JButton[][] panels;

		public JButton[][] getPanels() { return panels; }

		public Tile(){

			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			setLayout(layout);
			panels = new JButton[4][4];
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			constraints.insets = new Insets(2,2,2,2);

			for (int i = 0 ; i < 4; i++)
			{
				for (int j = 0 ; j < 4 ; j++)
				{
					panels[i][j] = new JButton();
					panels[i][j].setBackground(Color.GRAY);
					panels[i][j].setPreferredSize(new Dimension(100,100));
					panels[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 5));
					constraints.gridx = i;
					constraints.gridy = j;	
					layout.setConstraints(panels[i][j], constraints);
					add(panels[i][j]);
				}			
			}	

		}

		public void setTiles(Board aBoard)
		{
			for (int i = 0 ; i < 4; i ++)
			{
				for (int j = 0 ; j < 4 ; j ++)
				{
					if (aBoard.board[i][j] > 0)
					{
						panels[i][j].setFont(new Font("Arial", 20, 20));
						panels[i][j].setText(String.valueOf(aBoard.board[i][j]));						
						panels[i][j].setBackground(Color.WHITE);
					}
					else
					{
						panels[i][j].setText("");						
						panels[i][j].setBackground(Color.GRAY);
					}
				}
			}
			
		}
		
	}