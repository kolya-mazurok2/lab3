package lab3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class lab3JFrame extends JFrame {
    
    private int nArr = 5;
    private int mArr = 10;
    private JFileChooser fc = new JFileChooser();
    private JTextField hiddenText = new JTextField();
    private JFrame tableFrame = new JFrame();

    lab3JFrame() {
    	//Create and add to main panel components
        JPanel pnl = new JPanel();
        pnl.setPreferredSize(new Dimension(500,250));
        add(pnl);
        pnl.setLayout(null);
        
        hiddenText.setVisible(false);
        hiddenText.setText("");
        
        SpinnerModel model = new SpinnerNumberModel(5, 1, 20, 1);
        SpinnerModel model2 = new SpinnerNumberModel(10, 1, 20, 1);
        JSpinner nArr = new JSpinner(model);
        JSpinner mArr = new JSpinner(model2);
        JFormattedTextField tf = ((JSpinner.DefaultEditor) nArr.getEditor()).getTextField();
        tf.setEditable(false);
        tf.setBackground(Color.white);
        JFormattedTextField tf2 = ((JSpinner.DefaultEditor) mArr.getEditor()).getTextField();
        tf2.setEditable(false);
        tf2.setBackground(Color.white);
        
        JButton createArrBtn = new JButton();
        createArrBtn.setText("Створити масив");
        
        JButton randArray = new JButton();
        randArray.setText("Випадкові числа");
        
        JButton fillArray = new JButton();
        fillArray.setText("Ввести з клавіатури");
        
        JButton fillArrayFromFile = new JButton();
        fillArrayFromFile.setText("Заповнити з файлу");
        
        JButton showTable = new JButton();
        showTable.setText("Вивести у таблицю");
        
        JButton btnToFile = new JButton();
        btnToFile.setText("Вивести у файл");
        
        JButton btnSortArray = new JButton();
        btnSortArray.setText("Відсортувати масив");
        
        //Setup tableFrame
        tableFrame.setTitle("Таблиця");
        tableFrame.setPreferredSize(new Dimension(600,400));
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setResizable(false);
        tableFrame.pack();
        tableFrame.setVisible(false);
        
        //Add elements to panel
        pnl.add(nArr);
        nArr.setBounds(10,10,40,30);
        
        pnl.add(mArr);
        mArr.setBounds(150,10,40,30);
        
        //pnl.add(createArrBtn);
        //createArrBtn.setBounds(10,50,180,30);
        
        pnl.add(randArray);
        randArray.setBounds(10,50,180,30);
        
        pnl.add(fillArray);
        fillArray.setBounds(10,90,180,30);
        
        pnl.add(fillArrayFromFile);
        fillArrayFromFile.setBounds(10,130,180,30);
        
        pnl.add(showTable);
        showTable.setBounds(220,50,180,30);
        
        pnl.add(btnToFile);
        btnToFile.setBounds(220,90,180,30);
        
        pnl.add(btnSortArray);
        btnSortArray.setBounds(220,130,180,30);
        
        //Button actions
        createArrBtn.addActionListener(createArr);
        randArray.addActionListener(randArr);
        fillArray.addActionListener(fillArr);
        fillArrayFromFile.addActionListener(fillFromFile);
        showTable.addActionListener(showTableAction);
        btnToFile.addActionListener(toFile);
        btnSortArray.addActionListener(sortArray);
        
        //Array size change listeners
        nArr.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setnArr((int) nArr.getValue());
            }
        });
        mArr.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setmArr((int) mArr.getValue());
            }
        });
        
        setTitle("Панель керування");
        setPreferredSize(new Dimension(500,250));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
    }

    public int getnArr() {
        return nArr;
    }

    public void setnArr(int nArr) {
        this.nArr = nArr;
    }

    public int getmArr() {
        return mArr;
    }

    public void setmArr(int mArr) {
        this.mArr = mArr;
    }

    
    Action createArr = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {    
                MyArray obj = new MyArray(getnArr(), getmArr());
            }
            catch(Exception exc) {
            	System.out.println("Error: "+exc);
            }
        }
    };
    
    Action randArr = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                MyArray obj = new MyArray(getnArr(), getmArr());
                obj.fillArr(getnArr(), getmArr());
                hiddenText.setText(obj.convertToString(obj.getArr()));
            }
            catch(Exception exc) {
                System.out.println("Error: "+exc);
            }
        }
    };
    
    Action fillArr = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                MyArray obj = new MyArray(getnArr(), getmArr());
                Scanner sc = new Scanner(System.in);
                obj.fillArr(getnArr(), getmArr(), sc);
                hiddenText.setText(obj.convertToString(obj.getArr()));
                
            }
            catch(Exception exc) {
                System.out.println("Error: "+exc);
            }
        }
    };
    
    Action fillFromFile = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                MyArray obj = new MyArray(getnArr(), getmArr());
                
                if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    obj.fillArr(getnArr(),getmArr(),fc.getSelectedFile().getAbsolutePath());
                    hiddenText.setText(obj.convertToString(obj.getArr()));
                }
            }
            catch(Exception exc) {
                System.out.println("Error: "+exc);
            }
        }
    };
    
    Action showTableAction = new AbstractAction() {
    	@Override
        public void actionPerformed(ActionEvent e) {
            try {
            	tableFrame.setVisible(true);
            	
            	String str = hiddenText.getText();
            	String[] sArr = str.split(",");
            	
            	int k = 0;
            	int n = getnArr();
            	int m = getmArr();
            	String[][] data = new String[n][m];
            	String[] col = new String[m];
            	
            	for(int i=0; i<getnArr(); i++) {
                    for (int j=0; j<getmArr(); j++) {
                    	if(i == 0) {
                    		if(sArr.length-1 < k) {
                            	data[i][j] = "0";
                    			col[j] = "0";
                    		}
                            else {
                            	data[i][j] = sArr[k];
                            	col[j] = sArr[k];
                            }
                            k++;
                    	}
                    	else {
	                        if(sArr.length-1 < k)
	                        	data[i][j] = "0";
	                        else
	                        	data[i][j] = sArr[k];
	                        k++;
                    	}
                    }
                }
            	
            	DefaultTableModel model = new DefaultTableModel(data,col);
            	JTable table = new JTable(model);
            	tableFrame.add(table);
            }
            catch(Exception exc) {
            	System.out.println("Error: "+exc);
            }
        }
    };

    Action toFile = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
            	if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            		BufferedWriter writer = new BufferedWriter(new FileWriter(fc.getSelectedFile().getAbsolutePath()));
            		System.out.println(hiddenText.getText());
            		writer.write(hiddenText.getText());
            		
            		writer.close();
            	}
            }
            catch(Exception exc) {
                System.out.println("Error: "+exc);
            }
        }
    };
    

    Action sortArray = new AbstractAction() {
    	@Override
        public void actionPerformed(ActionEvent e) {
            try {
            	String [] strArr =  hiddenText.getText().split(",");
            	Arrays.sort(strArr);
            	String str = "";
            	for(int i=0; i<strArr.length-1; i++) {
            		str = str + "," + strArr[i];	
            	}
            	str = str.substring(1);
            	System.out.println(str);
            	hiddenText.setText(str);
            }
            catch(Exception exc) {
                System.out.println("Error: "+exc);
            }
        }
    };
}
