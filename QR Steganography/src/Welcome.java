import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JTextPane;

public class Welcome extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome frame = new Welcome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	



	/**
	 * Create the frame.
	 */
	public Welcome() {
		 try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(QRSteganography.class.getResource("/img/icons8-binary-42.png")));
		setTitle("QRHide | Welcome");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		
		JButton btnExtract = new JButton("Extract");
		btnExtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				Extract extraction = new Extract();
				extraction.setVisible(true);
			}
		});
		btnExtract.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JTextPane txtpnExtractAMessage = new JTextPane();
		txtpnExtractAMessage.setForeground(Color.WHITE);
		txtpnExtractAMessage.setContentType("txt");
		txtpnExtractAMessage.setBackground(Color.DARK_GRAY);
		txtpnExtractAMessage.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtpnExtractAMessage.setText("Extract a message from an image and decrypt it.");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtpnExtractAMessage, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(btnExtract, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addComponent(btnExtract)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtpnExtractAMessage, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		
		JButton btnGenerate = new JButton("Generate ");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				GenerateQR generate = new GenerateQR();
				generate.setVisible(true);
			}
		});
		btnGenerate.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JTextPane txtpnGenerateAQr = new JTextPane();
		txtpnGenerateAQr.setBackground(Color.DARK_GRAY);
		txtpnGenerateAQr.setForeground(Color.WHITE);
		txtpnGenerateAQr.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtpnGenerateAQr.setText("Generate a QR code.");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(btnGenerate, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(txtpnGenerateAQr, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(5)
					.addComponent(btnGenerate)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtpnGenerateAQr, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		
		JButton btnScan = new JButton("Scan ");
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				dispose();
				Scan scan = new Scan();
				scan.setVisible(true);
			}
		});
		btnScan.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JTextPane txtpnDecodeAQr = new JTextPane();
		txtpnDecodeAQr.setForeground(Color.WHITE);
		txtpnDecodeAQr.setBackground(Color.DARK_GRAY);
		txtpnDecodeAQr.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtpnDecodeAQr.setText("Decode a QR code.");
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(btnScan, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(txtpnDecodeAQr, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(5)
					.addComponent(btnScan)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtpnDecodeAQr, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_4 = new JPanel();
		
		JLabel lblNewLabel_1 = new JLabel("Welcome to");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 11));
		
		JPanel panel_5 = new JPanel();
		
		
	
		//lblNewLabel_1.setIcon(new ImageIcon(Welcome.class.getResource("/img/icons8-binary-42.png")));
		
		//setImage();
	
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_5, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(184)
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
					.addGap(220))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(227)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(502, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(36))
		);
		
		JLabel lblNewLabel_2 = new JLabel("KieranC 2022");
		lblNewLabel_2.setIcon(new ImageIcon(Welcome.class.getResource("/img/icons8-copyright-15.png")));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 7));
		panel_5.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("QRHide");
		lblNewLabel.setIcon(new ImageIcon(Welcome.class.getResource("/img/icons8-binary-30.png")));
		panel_4.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnNewButton = new JButton("Embed");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		
		JTextPane txtpnEncryptAMessage = new JTextPane();
		txtpnEncryptAMessage.setForeground(Color.WHITE);
		txtpnEncryptAMessage.setContentType("txt");
		txtpnEncryptAMessage.setBackground(Color.DARK_GRAY);
		txtpnEncryptAMessage.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtpnEncryptAMessage.setText("Encrypt a message and embed it into an image.");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtpnEncryptAMessage, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtpnEncryptAMessage, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
			dispose();
			QRSteganography embed = new QRSteganography();
			embed.setVisible(true);
			
			}
		});
		contentPane.setLayout(gl_contentPane);
	}
}
