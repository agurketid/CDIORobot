package imageProcessing;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JSeparator;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class GUI implements Runnable {
	
	JFrame frame;
	private Boolean ready = false;
	private boolean GREEN_OK = false;
	private boolean RED_OK = false;
	private boolean GREEN_PRESSED_BFR = false;
	private boolean RED_PRESSED_BFR = false;
	private boolean isSame = false;
	private JSlider sliderH_Min;
	private JSlider sliderS_min;
	private JSlider sliderV_min;
	private JSlider sliderH_max;
	private JSlider sliderS_max;
	private JSlider sliderV_max;
	private JSlider sliderSmooth;
	private JSlider sliderErode;
	private JSlider sliderDilate;
	private JSlider sliderSize;
	private JSlider sliderSMAX;
	private JLabel labelH_min;
	private JLabel labelS_min;
	private JLabel labelV_min;
	private JLabel labelH_max;
	private JLabel labelS_max;
	private JLabel labelV_max;
	private JLabel lblMin;
	private JLabel lblMax;
	private JLabel lblSmoothness;
	private JLabel lblErode;
	private JLabel lblDilate;
	private JLabel lblSize;
	private JLabel lblSMAX;
	private JButton btnInnerBlock;
	private JButton btnOuterBlock;
	private JButton btnFront;
	private JButton btnBack;
	private ArrayList<Settings> settingList = new ArrayList<Settings>();
	private int counter = 0;
	private int r_min = 0, g_min = 0, b_min = 0;
	private int r_max = 255, g_max = 255, b_max = 255;
	private int smooth = 1, erode, dilate, SMIN = 100, SMAX = 0;

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		//Set the look and feel to users OS LaF.
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (InstantiationException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    } catch (UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
		
		int panelW = 434;
		int panelH = 419;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenW = screenSize.getWidth();
		double screenH = screenSize.getHeight();
		frame = new JFrame("Filter Tool");
		frame.setBounds((int) (screenW-panelW-15), (int) (screenH-panelH-150), 459, 655);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 443, 617);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		sliderH_Min = new JSlider(0, 255);
		sliderH_Min.setBounds(129, 32, 287, 23);
		sliderH_Min.setValue(0);
		panel.add(sliderH_Min);
		sliderH_Min.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				labelH_min.setText("R_MIN : " + sliderH_Min.getValue());
				setR_min(sliderH_Min.getValue());

			}
		});

		labelH_min = new JLabel("R_MIN : " + sliderH_Min.getValue());
		labelH_min.setBounds(30, 32, 89, 14);
		panel.add(labelH_min);

		sliderS_min = new JSlider(0, 255);
		sliderS_min.setBounds(129, 66, 287, 23);
		sliderS_min.setValue(0);
		panel.add(sliderS_min);
		sliderS_min.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				labelS_min.setText("G_MIN : " + sliderS_min.getValue());
				setG_min(sliderS_min.getValue());
			}
		});

		labelS_min = new JLabel("G_MIN : " + sliderS_min.getValue());
		labelS_min.setBounds(30, 66, 89, 14);
		panel.add(labelS_min);

		//lblMin = new JLabel("MIN");
		//lblMin.setBounds(30, 11, 46, 14);
		//panel.add(lblMin);

		sliderV_min = new JSlider(0, 255);
		sliderV_min.setBounds(129, 100, 287, 23);
		sliderV_min.setValue(0);
		panel.add(sliderV_min);
		sliderV_min.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				labelV_min.setText("B_MIN : " + sliderV_min.getValue());
				setB_min(sliderV_min.getValue());
			}
		});

		labelV_min = new JLabel("B_MIN : " + sliderV_min.getValue());
		labelV_min.setBounds(30, 100, 89, 14);
		panel.add(labelV_min);

		//lblMax = new JLabel("MAX");
		//lblMax.setBounds(30, 137, 46, 14);
		//panel.add(lblMax);

		sliderH_max = new JSlider(0, 255);
		sliderH_max.setBounds(129, 162, 287, 23);
		sliderH_max.setValue(255);
		panel.add(sliderH_max);
		sliderH_max.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				labelH_max.setText("R_MAX : " + sliderH_max.getValue());
				setR_max(sliderH_max.getValue());
			}
		});

		labelH_max = new JLabel("R_MAX : " + sliderH_max.getValue());
		labelH_max.setBounds(30, 162, 89, 14);
		panel.add(labelH_max);

		sliderS_max = new JSlider(0, 255);
		sliderS_max.setValue(255);
		sliderS_max.setBounds(129, 196, 287, 23);
		panel.add(sliderS_max);
		sliderS_max.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				labelS_max.setText("G_MAX : " + sliderS_max.getValue());
				setG_max(sliderS_max.getValue());
			}
		});

		labelS_max = new JLabel("G_MAX : " + sliderS_max.getValue());
		labelS_max.setBounds(30, 196, 89, 14);
		panel.add(labelS_max);

		sliderV_max = new JSlider(0, 255);
		sliderV_max.setValue(255);
		sliderV_max.setBounds(129, 230, 287, 23);
		panel.add(sliderV_max);
		sliderV_max.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				labelV_max.setText("B_MAX : " + sliderV_max.getValue());
				setB_max(sliderV_max.getValue());
			}
		});

		labelV_max = new JLabel("B_MAX : " + sliderV_max.getValue());
		labelV_max.setBounds(30, 230, 89, 14);
		panel.add(labelV_max);
		
		sliderSmooth = new JSlider(1,29);
		sliderSmooth.setValue(1);
		sliderSmooth.setBounds(129, 365, 287, 23);
		panel.add(sliderSmooth);
		sliderSmooth.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblSmoothness.setText("Smooth : " + sliderSmooth.getValue());
				if(sliderSmooth.getValue() % 2 == 1)
					setSmooth(sliderSmooth.getValue());
			}
		});

		lblSmoothness = new JLabel("Smooth : " + sliderSmooth.getValue());
		lblSmoothness.setBounds(30, 365, 89, 14);
		panel.add(lblSmoothness);
		
		sliderErode = new JSlider(0,5);
		sliderErode.setValue(0);
		sliderErode.setBounds(129, 399, 287, 23);
		panel.add(sliderErode);
		sliderErode.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblErode.setText("Erode : " + sliderErode.getValue());
				setErode(sliderErode.getValue());
			}
		});

		lblErode = new JLabel("Erode : "+sliderErode.getValue());
		lblErode.setBounds(30, 399, 79, 14);
		panel.add(lblErode);
		
		sliderDilate = new JSlider(0,5);
		sliderDilate.setValue(0);
		sliderDilate.setBounds(129, 433, 287, 23);
		panel.add(sliderDilate);
		sliderDilate.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblDilate.setText("Dilate : "+sliderDilate.getValue());
				setDilate(sliderDilate.getValue());
				
			}
		});
		
		lblDilate = new JLabel("Dilate : "+sliderDilate.getValue());
		lblDilate.setBounds(30, 433, 79, 14);
		panel.add(lblDilate);
		
		sliderSize = new JSlider(0,200);
		sliderSize.setBounds(129, 291, 287, 23);
		panel.add(sliderSize);
		sliderSize.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblSize.setText("S_MIN : "+sliderSize.getValue());
				setSize(sliderSize.getValue());
				
			}
		});
		
		lblSize = new JLabel("S_MIN : "+sliderSize.getValue());
		lblSize.setBounds(30, 291, 200, 14);
		panel.add(lblSize);
		
		sliderSMAX = new JSlider(0,500);
		sliderSMAX.setValue(SMAX);
		sliderSMAX.setBounds(129, 325, 287, 23);
		panel.add(sliderSMAX);
		sliderSMAX.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblSMAX.setText("S_MAX : "+sliderSMAX.getValue());
				setSMAX(sliderSMAX.getValue());
			}
			});
		
		lblSMAX = new JLabel("S_MAX : "+sliderSMAX.getValue());
		lblSMAX.setBounds(30, 325, 200, 14);
		panel.add(lblSMAX);
		
		btnInnerBlock = new JButton("Green");
		btnInnerBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSettings("GREEN", "GREEN_SETTINGS");
				btnInnerBlock.setBackground(Color.GREEN);
				btnOuterBlock.setEnabled(true);
				setSliders(75, 225, 0, 205, 255, 5);
				
			}
		});
		btnInnerBlock.setBounds(30, 505, 89, 23);
		panel.add(btnInnerBlock);
		
		btnOuterBlock = new JButton("Red");
		btnOuterBlock.setEnabled(false);
		btnOuterBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSettings("RED", "RED_SETTINGS");
				btnOuterBlock.setBackground(Color.GREEN);
				btnFront.setEnabled(true);
				setSliders(75, 175, 143, 161, 255, 175);
			}
		});
		btnOuterBlock.setBounds(129, 505, 89, 23);
		panel.add(btnOuterBlock);
		
		btnFront = new JButton("Front");
		btnFront.setEnabled(false);
		btnFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSettings("FRONT", "FRONT_SETTINGS");
				btnFront.setBackground(Color.GREEN);
				btnBack.setEnabled(true);
				setSliders(200, 0, 0, 230, 55, 130);
			}
		});
		btnFront.setBounds(228, 505, 89, 23);
		panel.add(btnFront);
		
		btnBack = new JButton("Back");
		btnBack.setEnabled(false);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSettings("BACK", "BACK_SETTINGS");
				btnBack.setBackground(Color.GREEN);
			}
		});
		btnBack.setBounds(327, 505, 89, 23);
		panel.add(btnBack);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(32, 492, 186, 2);
		panel.add(separator);
		
		JLabel lblPorts = new JLabel("Ports");
		lblPorts.setForeground(Color.DARK_GRAY);
		lblPorts.setBounds(111, 478, 25, 14);
		panel.add(lblPorts);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(228, 492, 186, 2);
		panel.add(separator_1);
		
		JLabel lblRobot = new JLabel("Robot");
		lblRobot.setForeground(Color.DARK_GRAY);
		lblRobot.setBounds(307, 478, 29, 14);
		panel.add(lblRobot);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setReady(true);
			}
		});
		btnStart.setBackground(UIManager.getColor("Button.background"));
		btnStart.setBounds(30, 546, 188, 44);
		panel.add(btnStart);
	}


	@Override
	public void run() {
		initialize();
		frame.setVisible(true);
	}
	public void setSliders(int r_min, int g_min, int b_min, int r_max,int g_max,int b_max){
		this.sliderH_Min.setValue(r_min);
		this.r_min = r_min;
		this.sliderS_min.setValue(g_min);
		this.g_min = g_min;
		this.sliderV_min.setValue(b_min);
		this.b_min = b_min;
		this.sliderH_max.setValue(r_max);
		this.r_max = r_max;
		this.sliderS_max.setValue(g_max);
		this.g_max = g_max;
		this.sliderV_max.setValue(b_max);
		this.b_max = b_max;
	}
	public boolean getReady(){
		return ready;
	}
	public void setReady(boolean status){
		this.ready = status;
	}
	
	public int getR_min() {
		return r_min;
	}

	public void setR_min(int r_min) {
		this.r_min = r_min;
	}

	public int getG_min() {
		return g_min;
	}

	public void setG_min(int g_min) {
		this.g_min = g_min;
	}

	public int getB_min() {
		return b_min;
	}

	public void setB_min(int b_min) {
		this.b_min = b_min;
	}

	public int getR_max() {
		return r_max;
	}

	public void setR_max(int r_max) {
		this.r_max = r_max;
	}

	public int getG_max() {
		return g_max;
	}

	public void setG_max(int g_max) {
		this.g_max = g_max;
	}

	public int getB_max() {
		return b_max;
	}

	public void setB_max(int b_max) {
		this.b_max = b_max;
	}

	public int getErode() {
		return erode;
	}

	public void setErode(int erode) {
		this.erode = erode;
	}

	public int getDilate() {
		return dilate;
	}

	public void setDilate(int dilate) {
		this.dilate = dilate;
	}

	public int getSmooth() {
		return smooth;
	}

	public void setSmooth(int smooth) {
		this.smooth = smooth;
	}

	public int getSize() {
		return SMIN;
	}

	public void setSize(int size) {
		this.SMIN = size;
	}

	public int getSMAX() {
		return SMAX;
	}

	public void setSMAX(int sMAX) {
		SMAX = sMAX;
	}
	
	public boolean getGreen_OK() {
		return GREEN_OK;
	}

	public void setGreen_OK(boolean b) {
		this.GREEN_PRESSED_BFR = true;
		this.GREEN_OK = b;	
	}
	public boolean getGREEN_BFR(){
		return GREEN_PRESSED_BFR;
	}
	
	public boolean getRed_OK() {
		return RED_OK;
	}

	public void setRed_OK(boolean b) {
		this.RED_PRESSED_BFR = true;
		this.RED_OK = b;	
	}
	public boolean getRED_BFR(){
		return RED_PRESSED_BFR;
	}
	
	public void setSettings(String color, String name) {
		for(Settings setting : settingList){
			if(name.equals(setting.getName())) {
				setting.setB_max(b_max);
				setting.setB_min(b_min);
				setting.setR_max(r_max);
				setting.setR_min(r_min);
				setting.setG_max(g_max);
				setting.setG_min(g_min);
				setting.setSmooth_lvl(smooth);
				setting.setDilate_lvl(dilate);
				setting.setErode_lvl(erode);
				setting.setS_max(SMAX);
				setting.setS_min(SMIN);
				isSame = true;
			}
				
			}
		if (isSame == false)
			settingList.add(new Settings(name, color, r_min, g_min, b_min, r_max, g_max, b_max, SMIN, SMAX, smooth, erode, dilate));
		}
	
	
	public ArrayList<Settings> getColorSettings(){
		return settingList;
	}
}
