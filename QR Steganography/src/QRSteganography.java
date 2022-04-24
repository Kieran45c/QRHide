import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import java.awt.Window.Type;
import javax.swing.DropMode;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;

public class QRSteganography extends JFrame {

	
	private JPanel contentPane;
	private JTextField keyField;
	JLabel lblNewLabel_2 = new JLabel();
	BufferedImage img2;
	String path;
	
	static {
		Security.addProvider(new BouncyCastleProvider()); 
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					QRSteganography frame = new QRSteganography();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	

	
	
	private byte[] encryptMsg(String msg, String keytext) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		   // String secretText = "This is secret Text that will be embedded";
		    //String keytext = "thisisapassword1";
		    	 
		
			if (keytext.length() < 16)
			{
				while (keytext.length() < 16 )
				{
					keytext = keytext + 'x';
				}
					
			}
		
			
		    byte[] data = msg.getBytes(StandardCharsets.UTF_8);

			Cipher cipher;

		    cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			final Key key = new SecretKeySpec(keytext.getBytes(), "AES");
			
			
			/*SecureRandom random = new SecureRandom();
			byte[] iv = new byte[cipher.getBlockSize()];
			random.nextBytes(iv);*/
				
			final byte[] iv = Hex.decode("9f741fdb5d8845bdb48a94394e84f8a3");
				
			byte[] ciphertext = encrypt(cipher, key, iv, data);
			
			return ciphertext;
		
	}
	
	
	
	private static BufferedImage insert(BufferedImage image, byte[] msg) 
	{
	    
	    int msgLength = msg.length;
	    
	    ByteBuffer b = ByteBuffer.allocate(4);
	    b.putInt(msgLength);
	    byte[] length = b.array();

		byte binImage[] =  ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		
		int maxCap = maxCap(image);
		
		if(msgLength + 32 < maxCap)
		{
			embed(binImage, length, 0); 
			embed(binImage, msg, 32); 
			
			JOptionPane.showMessageDialog(null, "Message embedded!");
		 }
		 else
		 {
			 JOptionPane.showMessageDialog(null, "File not big enough");
		 }
		
		return image;
	}
	
	private static byte[] embed(byte[] binImage, byte[] text, int position)
	{
		
		 for(int i = 0; i < text.length; i++)
		 {	
		         for(int j = 7; j >= 0; j--)
		         {
		        	
		             int bit = (text[i] >>> j) & 1;
		          
		             binImage[position] = (byte) (binImage[position] & 0xFE | bit) ;
		             
		             //System.out.println(Integer.toBinaryString(binImage[position]));
		             
		             position++;
	  
		       
		         } 
		   }
		
       
		return binImage;
	}
	
	
	private static BufferedImage readImage(String path) throws FileNotFoundException, IOException
	{
	        	       
		BufferedImage QR = ImageIO.read(new FileInputStream(path));
          
        BufferedImage image = new BufferedImage(QR.getWidth(), QR.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
  		Graphics2D graphics = (Graphics2D) image.createGraphics();
  		graphics.drawRenderedImage(QR, null);
  		graphics.dispose();
	
		
		return image;
	}
	
	
	public ImageIcon ResizeImage(String ImagePath) {
		
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance( lblNewLabel_2.getWidth(), lblNewLabel_2.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		
		return image;
	}
	
	public static int maxCap(BufferedImage img)
	{
		int g = 3 * img.getWidth() * img.getHeight(); //Amount of bits in image
  		
  		int max = g / 8; //Amount of bytes in image
  		
		return max;
		
	}
	
	public QRSteganography() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(QRSteganography.class.getResource("/img/icons8-binary-42.png")));
		setTitle("QRHide | Embed");
		
		
		 try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 
	    Border border = BorderFactory.createLineBorder( null, 2);
	    lblNewLabel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	   
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 449);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Upload ");
		mntmNewMenuItem.setIcon(new ImageIcon(QRSteganography.class.getResource("/img/icons8-upload-file-20.png")));
		
		keyField = new JTextField();
		keyField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				String getK = keyField.getText();
				
				int klength = getK.length();
				
				if(klength >= 16)
				{
					keyField.setEditable(false);
				}
				
				if(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE)
				{
					keyField.setEditable(true);
				}
				
			}
		});
		keyField.setColumns(10);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save ");
		mntmNewMenuItem_1.setIcon(new ImageIcon(QRSteganography.class.getResource("/img/icons8-save-20.png")));
	
	
		JButton embedButton = new JButton("Embed");
		embedButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblNewLabel_2_1 = new JLabel("Image");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setFont(new Font("DialogInput", Font.BOLD | Font.ITALIC, 13));
		
		
		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "png", "jpg");
			
				file.addChoosableFileFilter(filter);
				int result = file.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					
					if(path.toLowerCase().endsWith(".png") || path.toLowerCase().endsWith(".jpg"))
					{
						
						lblNewLabel_2.setIcon(ResizeImage(path));
							
						  try {
								
								img2 = readImage(path);
								
								int maxCap = maxCap(img2);
								
								int m = maxCap - 32;
								
								String maxCapacity = Integer.toString(m);
								
								lblNewLabel_2_1.setText("Max Capacity: " + maxCapacity + " Characters");
											
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					  
					}
					else
					{
						JOptionPane.showMessageDialog(mntmNewMenuItem, "You must upload a .png or .jpg image");
					}
						
				}
				else if (result == JFileChooser.CANCEL_OPTION) {
					
				
				}
				
		
				
			}
			
			
		});
		
		JTextArea msgField = new JTextArea();
		msgField.setWrapStyleWord(true);
		msgField.setLineWrap(true);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setFont(new Font("DialogInput", Font.BOLD | Font.ITALIC, 10));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		embedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String message = msgField.getText();
				String keytext = keyField.getText();
				
				if(message.isEmpty())
				{
					 JOptionPane.showMessageDialog(embedButton, "Please enter a message first");
				}
				else if(img2 == null)
				{
					 JOptionPane.showMessageDialog(embedButton, "Please upload an image first");
				}
				else
				{
					
					try {
										
							if(keytext.isEmpty())
							{
	
								byte[] msg = message.getBytes();
								int msgLength = msg.length;
								String msgL = Integer.toString(msgLength);
								lblNewLabel_4 .setText("Characters Embedded: " + msgL + " (inc. whitespace)");
								
								img2 = insert(img2, msg);
								
							}
							else
							{
							
								byte[] encryptedMsg = encryptMsg(message,keytext);
								lblNewLabel_4 .setText("");
								img2 = insert(img2, encryptedMsg);
								
							}
							
								
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException
							| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
							| BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
															
					
				}
						
				
			}
		});
			
		
		
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
			    
			    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			        File file = fileChooser.getSelectedFile();
			        
			       if(img2 != null)
			       {
				        try {
				        	
				           ImageIO.write((BufferedImage) img2, "png", new File(file.getAbsolutePath() + ".png"));
				           
				           
				           JOptionPane.showMessageDialog(mntmNewMenuItem_1, "Image saved");
				             
				        } catch (IOException ex) {
				        	
				        	JOptionPane.showMessageDialog(mntmNewMenuItem_1, "Failed to save image");
				           
				        }
				   } 
			       else
			       {
			    	   JOptionPane.showMessageDialog(mntmNewMenuItem_1, "You must upload a file first");
			       }
			
			    }
			}
		});

	
		mnNewMenu.add(mntmNewMenuItem);
		
	
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Help");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Page instructions");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(msgField, "1. Upload a .png or .jpg image" + "\n" + "2. Enter a message"  + "\n" + "3. Enter a key (Optional - Max 16 charachters)"  + "\n" + "4. Embed message"  + "\n" + "5. Save image");
						
				
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(QRSteganography.class.getResource("/img/icons8-help-20.png")));
		mnNewMenu_1.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		

		JLabel lblNewLabel = new JLabel("Message");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblNewLabel_1 = new JLabel("Key");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblNewLabel_3 = new JLabel("Embed a Message");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("DialogInput", Font.BOLD | Font.ITALIC, 18));
		
	
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		
	
	

	
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
							.addGap(98))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
								.addComponent(embedButton, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
								.addComponent(keyField, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
							.addGap(32)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2_1, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
						.addComponent(lblNewLabel_2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(23)
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(lblNewLabel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(keyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1))
							.addGap(18)
							.addComponent(embedButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		JButton btnNewButton_1_3 = new JButton("Welcome");
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				Welcome welcome = new Welcome();
				welcome.setVisible(true);
			}
		});
		btnNewButton_1_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewButton_1_3_1 = new JButton("Extract");
		btnNewButton_1_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				dispose();
				Extract extraction = new Extract();
				extraction.setVisible(true);
			}
		});
		btnNewButton_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewButton_1_3_2 = new JButton("Generate QR");
		btnNewButton_1_3_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				GenerateQR generate = new GenerateQR();
				generate.setVisible(true);
				
			}
		});
		btnNewButton_1_3_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewButton_1_3_2_1 = new JButton("Scan");
		btnNewButton_1_3_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
							
				dispose();
				Scan scan = new Scan();
				scan.setVisible(true);
			}
		});
		
		btnNewButton_1_3_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(55)
					.addComponent(btnNewButton_1_3, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(57)
					.addComponent(btnNewButton_1_3_1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(44)
					.addComponent(btnNewButton_1_3_2, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(41)
					.addComponent(btnNewButton_1_3_2_1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(64))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_1_3)
						.addComponent(btnNewButton_1_3_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1_3_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1_3_2_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(gl_panel);
		
		
		scrollPane.setViewportView(msgField);
		contentPane.setLayout(gl_contentPane);
	}
	
	
	
	private static byte[] encrypt(final Cipher cipher, final Key key, final byte[] inventoryVector, final byte[] data)
			throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(inventoryVector));
		return cipher.doFinal(data);
	}
}
