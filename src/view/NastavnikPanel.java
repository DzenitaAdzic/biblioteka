package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import model.Knjiga;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.Korisnik;
import model.Nastavnik;
import model.Posudba;
import service.KnjigaService;
import service.NastavnikService;
import service.PosudbaService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NastavnikPanel extends JFrame {
	private Korisnik korisnik;
	private Nastavnik nastavnik;
	
	private void showForm(JFrame view){    
		view.setSize(610, 530);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        view.setLocation(dim.width/2 - view.getSize().width/2, dim.height/2 - view.getSize().height/2);
        view.getContentPane().setBackground(new Color(245, 245, 245));        
        view.setResizable(true);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setIconImage( (new ImageIcon(NastavnikPanel.class.getResource("/ikone/library.png"))).getImage() );
        dispose();
    }
	
	
	public NastavnikPanel(Korisnik k) {
		korisnik = k;
		NastavnikService nService = new NastavnikService();
		nastavnik = nService.find(korisnik.getSifra());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Nastavnik panel");
		
		FlowLayout flowLayout = new FlowLayout();
		getContentPane().setLayout(flowLayout);
		
		JButton btnPregledPredmeta = new JButton("Pregled predmeta");
		btnPregledPredmeta.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPregledPredmeta.setIcon(new ImageIcon(NastavnikPanel.class.getResource("/ikone/folderOpen.png")));
		btnPregledPredmeta.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPregledPredmeta.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		getContentPane().add(btnPregledPredmeta);
		
		btnPregledPredmeta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 
					NastavnikPredmetiView view = new NastavnikPredmetiView(korisnik, nastavnik);
					showForm(view);
			}
		});
		
		JButton btnPregledZaduzenja = new JButton("Pregled zaduzenja");
		btnPregledZaduzenja.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPregledZaduzenja.setIcon(new ImageIcon(NastavnikPanel.class.getResource("/ikone/contents.png")));
		btnPregledZaduzenja.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPregledZaduzenja.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		getContentPane().add(btnPregledZaduzenja);
		
		btnPregledZaduzenja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PosudbaService posudbaService = new PosudbaService();
				List<Posudba> sadrzaj = posudbaService.findAll();
				if(sadrzaj.size() == 0){
					JOptionPane.showMessageDialog(null, "Niste posudili niti jednu knjigu.", "Gre�ka", JOptionPane.ERROR_MESSAGE);
				}
				else{
					NastavnikPosudbe view = new NastavnikPosudbe(korisnik);
					showForm(view);
				}
			}
		});
		
		JButton btnPregledSadrzaja = new JButton("Pregled sadrzaja");
		btnPregledSadrzaja.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPregledSadrzaja.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPregledSadrzaja.setIcon(new ImageIcon(NastavnikPanel.class.getResource("/ikone/pronadjiKjigu.png")));
		btnPregledSadrzaja.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		getContentPane().add(btnPregledSadrzaja);
		
		btnPregledSadrzaja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KnjigaService knjigaService = new KnjigaService();
				List<Knjiga> sadrzaj = knjigaService.findAll();
				if(sadrzaj.size() == 0){
					JOptionPane.showMessageDialog(null, "U biblioteci se ne nalazi ni jedna knjiga.", "Gre�ka", JOptionPane.ERROR_MESSAGE);
				}
				else{
					BibliotekaView view = new BibliotekaView(korisnik);
					showForm(view);
					}
				}
		}); 
		
		JButton btnPosudjivanje = new JButton("Posudbe");
		btnPosudjivanje.setMinimumSize(new Dimension(131, 23));
		btnPosudjivanje.setMaximumSize(new Dimension(131, 23));
		btnPosudjivanje.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPosudjivanje.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPosudjivanje.setIcon(new ImageIcon(NastavnikPanel.class.getResource("/ikone/knjiga.png")));
		btnPosudjivanje.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		getContentPane().add(btnPosudjivanje);
		
		btnPosudjivanje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					NastavnikRezervacije view = new NastavnikRezervacije(korisnik);
					showForm(view);
			}
		});
		
		JButton btnDodavanjeLiterature = new JButton("Dodavanje literature");
		btnDodavanjeLiterature.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		btnDodavanjeLiterature.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDodavanjeLiterature.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDodavanjeLiterature.setIcon(new ImageIcon(NastavnikPanel.class.getResource("/ikone/bookmarks_list_add.png")));
	    getContentPane().add(btnDodavanjeLiterature);
	    
	    btnDodavanjeLiterature.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
				NastavnikKnjige view = new NastavnikKnjige(korisnik);
				showForm(view);
			}
		});
	    
	    JButton btnPromjenaSifre = new JButton("Promjena password-a");
		btnPromjenaSifre.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPromjenaSifre.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPromjenaSifre.setIcon(new ImageIcon(NastavnikPanel.class.getResource("/ikone/knjiga.png")));
		btnPromjenaSifre.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		getContentPane().add(btnPromjenaSifre);
		
		btnPromjenaSifre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					PromjenaSifreNastavnik view = new PromjenaSifreNastavnik(nastavnik);
					showForm(view);
			}
		});
	    
	    JButton btnOdjava = new JButton("Odjava");
		btnOdjava.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnOdjava.setIcon(new ImageIcon(NastavnikPanel.class.getResource("/ikone/exit.png")));
		btnOdjava.setHorizontalTextPosition(SwingConstants.CENTER);
		btnOdjava.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		getContentPane().add(btnOdjava);
		
		btnOdjava.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Prijava prijavaView=new Prijava();
				prijavaView.setResizable(false);
		        prijavaView.setSize(new Dimension(400, 190));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        prijavaView.setLocation(dim.width/2-prijavaView.getSize().width/2, dim.height/2-prijavaView.getSize().height/2);
		        prijavaView.getContentPane().setBackground(new Color(245, 245, 245));
		        prijavaView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        prijavaView.setVisible(true);
		        prijavaView.setIconImage( (new ImageIcon(NastavnikPanel.class.getResource("/ikone/library.png"))).getImage() );
				dispose();
			}
			
		});
	    
		JLabel lblDobrodoli = new JLabel("Prijavljeni ste kao:");
		getContentPane().add(lblDobrodoli);
		
		JLabel lblUsername = new JLabel(k.getKorisnickoIme() + " (" + k.getIme() + " " + k.getPrezime() + ")");
		lblUsername.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblUsername.setForeground(new Color(30, 144, 255));
		getContentPane().add(lblUsername);
	
	}

}
