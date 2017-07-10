
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import service.KorisnikService;
import service.ServiceException;
import view.Prijava;
import model.Korisnik;

public class Main {
	
		public static void main(String[] args) {
			Prijava prijavaView=new Prijava();
			prijavaView.setResizable(false);
	        prijavaView.setSize(new Dimension(400, 190));
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
	        prijavaView.setLocation(dim.width/2-prijavaView.getSize().width/2, dim.height/2-prijavaView.getSize().height/2);
	        prijavaView.getContentPane().setBackground(new Color(245, 245, 245));
	        prijavaView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
	        prijavaView.setVisible(true);
	        
		
	}

}
