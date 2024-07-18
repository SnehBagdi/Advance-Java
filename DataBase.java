package pro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataBase implements ActionListener{
    
	JFrame f1;
	JButton b1,b2,b3,b4;
	JScrollPane s1;
	JTable t1;
	DefaultTableModel m1;
	PreparedStatement ps;
    Statement st;
	ResultSet rt;
	Connection con;
	
	void cal()
	{
		f1 = new JFrame("Database entry");
		f1.setSize(500,500);
		f1.setLayout(null);
		
		b1 = new JButton("view");
		b1.setBounds(30,60,100,50);
		f1.add(b1);
		b1.addActionListener(this);
		
		b2 = new JButton("insert");
		b2.setBounds(210,60,100,50);
		f1.add(b2);
		b2.addActionListener(this);
		
		b3 = new JButton("delete");
		b3.setBounds(390,60,100,50);
		f1.add(b3);
		b3.addActionListener(this);
		
		b4 = new JButton("Update");
		b4.setBounds(570, 60,100,50);
		f1.add(b4);
		b4.addActionListener(this);
		
		m1 = new DefaultTableModel();
		t1 = new JTable(m1);
		s1 = new JScrollPane(t1);
		s1.setBounds(10,180,1000,250);
		f1.add(s1);
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/class", "****", "****");
		    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		f1.setVisible(true);
	}
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if(e.getSource() == b1)
		{
			view();
		}
		
		else if(e.getSource() == b2)
		{
			insertion();
			
		}
		
	    else if(e.getSource() == b3)
		{
			deletion();
		}
		
	    else
	    {
	    	updation();
	    }
	}
	
	private void view()
	{
		try {
			rt = st.executeQuery("Select * from employee");
			
			ResultSetMetaData meta = rt.getMetaData();
			int col = meta.getColumnCount();
			
			m1.setColumnCount(0);
			m1.setRowCount(0);
			
			for(int i = 1; i<=col; i++)
			{
				m1.addColumn(meta.getColumnName(i));
			}
			
			while(rt.next())
			{
				Object[] row = new Object[col];
				for(int i = 1; i<=col; i++)
				{
					row[i-1] = rt.getObject(i);
				}
				
				m1.addRow(row);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
		
	private void insertion() {
		String id = JOptionPane.showInputDialog(f1,"enter the id:");
		String name = JOptionPane.showInputDialog(f1,"enter the name:");
		String salary = JOptionPane.showInputDialog(f1,"enter the salary:");
		String position = JOptionPane.showInputDialog(f1,"enter the position:");
        
		int i = Integer.parseInt(id);
		float sal = Float.parseFloat(salary);

        try {
        	
        	ps = con.prepareStatement("insert into employee values ( ?,?,?,? )");

    		ps.setInt(1, i);
    		ps.setString(2, name);
    		ps.setFloat(3, sal);
    		ps.setString(4,position);
    		
    		ps.executeUpdate();
    		
            JOptionPane.showMessageDialog(f1, "Record inserted successfully!");
            view(); 
         
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(f1, "Error inserting record.");
        }
    }
	
	private void deletion()
	{
		int row = t1.getSelectedRow();
		if(row == -1)
		{
			JOptionPane.showMessageDialog(f1, "Select a row to delete.");
	        return;
		}
		
		int id1 = (int) t1.getValueAt(row, 0);
		
		try
		{
			st.executeUpdate("Delete from employee where e_id = " + id1);
			JOptionPane.showMessageDialog(f1, "deleted");
			view();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(f1, "Error");
		}
	}
	
	private void updation()
	{
		String i = JOptionPane.showInputDialog(f1,"Enter the id you want to update");
		int i1 = Integer.parseInt(i);
		
		try
		{
		rt = st.executeQuery("Select * from employee");
		while(rt.next())
		{
			if(rt.getInt(1) == i1)
			{
				String ch = JOptionPane.showInputDialog(f1,"Do you want to update id? Y/N");
				if(ch.equals("y") || ch.equals("Y"))
				{
					String u = JOptionPane.showInputDialog(f1,"Enter the id you want");
					int u1 = Integer.parseInt(u);
					rt.updateInt(1, u1);
					rt.updateRow();
					JOptionPane.showMessageDialog(f1,"Id has been updated");
				}
				
				String ch1 = JOptionPane.showInputDialog(f1,"Do you want to update name? Y/N");
				if(ch1.equals("y") || ch1.equals("Y"))
				{
					String u = JOptionPane.showInputDialog(f1,"Enter the name you want");
					rt.updateString(2, u);
					rt.updateRow();
					JOptionPane.showMessageDialog(f1,"Name has been updated");
				}
				
				String ch2 = JOptionPane.showInputDialog(f1,"Do you want to update salary? Y/N");
				if(ch2.equals("y") || ch.equals("Y"))
				{
					String u = JOptionPane.showInputDialog(f1,"Enter the salary you want");
					float u1 = Float.parseFloat(u);
					rt.updateFloat(3, u1);
					rt.updateRow();
					JOptionPane.showMessageDialog(f1,"Salary has been updated");
				}
				
				String ch3 = JOptionPane.showInputDialog(f1,"Do you want to update position? Y/N");
				if(ch3.equals("y") || ch3.equals("Y"))
				{
					String u = JOptionPane.showInputDialog(f1,"Enter the position you want");
					rt.updateString(4, u);
					rt.updateRow();
					JOptionPane.showMessageDialog(f1,"position has been updated");
				}
				
				view();
			}
			
		}	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			}
		
	}
	public static void main(String[] args) {
		Sample ob = new Sample();
		ob.cal();
	}
	

}
