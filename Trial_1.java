
package trial_1;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.*;
import javax.swing.table.*;
class Trial_1 extends Thread 
{
    JFrame jf;
    JLabel c_id;              JLabel invoice_id;  JLabel set_invoice_id;
    JTextField input_c_id;     
    JComboBox product;        JLabel counter_id;  JTextField input_counter_id;
    JComboBox description;
    JLabel quantity;          JLabel cashier;     JComboBox input_cashier;
    JTextField input_quantity;JLabel date1;        JLabel time; 
    JLabel unitprice;
    JLabel set_unitprice;
    JTable contents;         String[] coloumn= {"S.NO","PRODUCT","DESCRIPTION","UNITPRICE","QUANTITY","AMOUNT"}; 
    String[] temp_product={"PRODUCT",""};
    String[] temp_description={"DESCRIPTION ",""};
    String print_payment;
    static int sno=0;
    int total_quantity=0,hour,minute,sec;
    float vat_;
    double total_amount=0.0,amount;
    JPanel panel=new JPanel();
    JScrollPane pane;
    DefaultTableModel model = new DefaultTableModel(); 
    JRadioButton cash;     JRadioButton card;
    JLabel cash_details;  TextField tcash_details;
    JLabel card_details;  TextField tcard_details;
    ButtonGroup bg=new ButtonGroup();
    JButton print;
    JLabel cash_returned;
    JTextField qrcode;
    public void run() 
    {
        int row_index=0,column_index=0;
        while (true) 
        { 
            try
            {    
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","oracle");
                Statement stmt=con.createStatement();
                String query012="SELECT MAX(INVOICE_ID) FROM INVOICE";
                ResultSet rs=stmt.executeQuery(query012);
                rs.next();
                set_invoice_id.setText((rs.getInt(1)+1)+""); 
                rs.close();
                stmt.close();
                con.close();
            }
            catch(Exception e)
            {}
            Calendar now= Calendar.getInstance();
            hour = now.get(Calendar.HOUR_OF_DAY);
            minute=now.get(Calendar.MINUTE);
            sec=now.get(Calendar.SECOND);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Trial_1.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.time.setText("time :"+hour+":"+minute+":"+sec+"");  
            if(sno!=0)
                   {
                       row_index=contents.getSelectedRow();
                       column_index=contents.getSelectedColumn();
                       //System.out.println(row_index);
                      
                       
                       if(row_index!=-1 && column_index!=-1)
                       {
                           contents.getValueAt(row_index,column_index);
                           if(column_index==4)
                           {
                               /*         if(Integer.parseInt((String) contents.getValueAt(contents.getSelectedRow(),4))==0)
                               {
                               model.removeRow(row_index);
                               for(int i=row_index;i<sno;i++)
                               {
                               contents.setValueAt(i,i,0);
                               }
                               System.out.println("hi");
                               }*/
                                double vat_temp=Double.parseDouble((String) contents.getValueAt(row_index,5));
                                double unitprice_temp=Double.parseDouble((String) contents.getValueAt(row_index,3));
                                int quantity_temp=Integer.parseInt((String) contents.getValueAt(row_index,4));
                                double discount_temp=Double.parseDouble((String) contents.getValueAt(row_index,6));
                                double amount_temp=(((vat_temp*unitprice_temp)/100+unitprice_temp)*quantity_temp);
                                double total_amount_temp = 0.0;
                                int total_quantity_temp=0;
                                amount_temp=(amount_temp-(amount_temp*discount_temp)/100);
                                contents.setValueAt(amount_temp+"",row_index,7);
                                for (int i = 0; i <= (contents.getRowCount()-2); i++)
                                {
                                    
                                    if(contents.getValueAt(1,4).equals(""));
                                    else
                                    {
                                        int quantity_temp1 = Integer.parseInt((String) contents.getValueAt(i, 4));
                                        total_quantity_temp += quantity_temp1;
                                    }
                                }
                                 for (int i = 0; i <= (contents.getRowCount()-2); i++)
                                {
                                    if(contents.getValueAt(1,7).equals(""));
                                    else
                                    {
                                        double amount_temp1 = Double.parseDouble((String) contents.getValueAt(i, 7));
                                        total_amount_temp += amount_temp1;
                                    }
                                }
                               contents.setValueAt(total_quantity_temp+"",contents.getRowCount()-1,4);
                               contents.setValueAt((int)total_amount_temp+"",contents.getRowCount()-1,7);                               
                           }
                       }
                   }
        }
        
    }
    Trial_1()  throws IOException, InterruptedException
    {
        jf=new JFrame("INVOICE");
        c_id=new JLabel("customer id");                    input_c_id=new JTextField("0");                 invoice_id=new JLabel("invoice");    set_invoice_id=new JLabel();
        product=new JComboBox(temp_product);               description=new JComboBox(temp_description);       counter_id=new JLabel("counter id"); input_counter_id=new JTextField();
        quantity=new JLabel("quantity");                   input_quantity=new JTextField();                   cashier=new JLabel("cashier");       input_cashier=new JComboBox();
        unitprice=new JLabel("unitprice");                 set_unitprice=new JLabel();                        date1=new JLabel();                  time=new JLabel("time");
        /* DefaultTableModel model = new DefaultTableModel(); */
        model.addColumn("S.NO   ");
        model.addColumn("PRODUCT");
        model.addColumn("DESCRIPTION");
        model.addColumn("UNITPRICE");
        model.addColumn("QUANTITY");
        model.addColumn("VAT");
        model.addColumn("DISCOUNT");
        model.addColumn("AMOUNT");
        contents=new JTable(model);
        pane = new JScrollPane(contents);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        java.util.Date date = new java.util.Date();
        date1.setText("date :"+dateFormat.format(date)+"");
        cash=new JRadioButton("cash");                     card=new JRadioButton("card");
        cash_details=new JLabel("Cash Received"); tcash_details=new TextField();
        card_details=new JLabel("Card Mumber"); tcard_details=new TextField();
        print=new JButton("CONFERM AND PRINT");
        cash_returned=new JLabel();
        qrcode=new JTextField();
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","oracle");
            Statement stmt=con.createStatement();
            String query1="SELECT UNIQUE PRODUCT FROM PRODUCT_MASTER";
            ResultSet rs; 
            rs=stmt.executeQuery(query1);
            product.removeAllItems();
            while(rs.next())
            {
                product.addItem(rs.getString("PRODUCT"));
            }
            String query0="SELECT CASHIER_ID FROM CASHIER"; 
            rs=stmt.executeQuery(query0);
            input_cashier.removeAllItems();
            while(rs.next())
            {
                input_cashier.addItem(rs.getString("CASHIER_ID"));
            }
            rs.close();
            stmt.close();
            con.close();
            product.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent eve)
                {
                    try
                    {
                        if(product.getSelectedItem().toString()!=null)
                        {
                            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","oracle");
                            Statement stmt=con.createStatement();
                            String temp=(String)product.getItemAt(product.getSelectedIndex());
                            String query2="SELECT UNIQUE DESCRIPTION FROM PRODUCT_MASTER WHERE PRODUCT ='"+temp+"' ";
                            ResultSet rs=stmt.executeQuery(query2);
                            description.removeAllItems();
                            while(rs.next())
                            {
                                description.addItem(rs.getString("DESCRIPTION"));
                            }
                            rs.close();
                            stmt.close();
                            con.close();
                            description.addActionListener(new ActionListener()
                            {
                                public void actionPerformed(ActionEvent eve2)
                                {
                                    try
                                    {
                                        if(description.getSelectedItem().toString()!=null)
                                        {
                                            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","oracle");
                                            Statement stmt=con.createStatement();
                                            String temp1=(String)description.getItemAt(description.getSelectedIndex());
                                            String query3="SELECT UNITPRICE FROM PRODUCT_MASTER WHERE DESCRIPTION ='"+temp1+"' ";
                                            ResultSet rs=stmt.executeQuery(query3);
                                            rs.next();
                                            set_unitprice.setText(rs.getInt("UNITPRICE")+"");
                                            rs.close();
                                            stmt.close();
                                            con.close();
                                            
                                        }
                                    }
                                    catch(Exception e3)
                                    {
                                    }
                                }
                            }
                            );
                        }
                    }
                    catch(Exception e2)
                    {
                        System.out.println(e2);
                    }
                }
            }
            );
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        input_quantity.addActionListener(new ActionListener()
        { 
        public void actionPerformed(ActionEvent e123)
        {
            try
            {
                if(input_quantity!=null)
                {
                    try
                    {
                        if(sno!=0)
                        model.removeRow(sno);
                        total_amount=0.0;
                        total_quantity=0;
                        double amount_temp=0.0;
                        int quantity_temp=0;
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","oracle");
                        Statement stmt=con.createStatement();
                        String temp=(String)product.getItemAt(product.getSelectedIndex());
                        String temp1=(String)description.getItemAt(description.getSelectedIndex());
                        String query2="SELECT VAT FROM PRODUCT_MASTER WHERE PRODUCT ='"+temp.trim()+"' ";
                        ResultSet rs=stmt.executeQuery(query2);
                        rs.next();
                        vat_=rs.getFloat("VAT");
                        amount=(((vat_*Integer.parseInt(set_unitprice.getText()))/100+Integer.parseInt(set_unitprice.getText()))*Integer.parseInt(input_quantity.getText().trim()));
                        String query3="SELECT DISCOUNT FROM PRODUCT_MASTER WHERE DESCRIPTION ='"+temp1.trim()+"' ";
                        rs=stmt.executeQuery(query3);
                        rs.next();
                        amount=(amount-(amount*rs.getInt("DISCOUNT"))/100);
                        String row1[]={++sno+"",temp,temp1,set_unitprice.getText(),input_quantity.getText(),vat_+"",rs.getInt("DISCOUNT")+"",amount+""};
                        model.addRow(row1);
                        
                        //PRESS ENTER TO CHANGE VALUE OF TOTAL IF QUANTITY IS CHANGED
                        for (int i = 0; i <= contents.getRowCount()-1; i++)
                        {
                             amount_temp = Double.parseDouble((String) contents.getValueAt(i, 7));
                            total_amount += amount_temp;
                        }
                        for (int i = 0; i <= contents.getRowCount()-1; i++)
                        {
                             quantity_temp = Integer.parseInt((String) contents.getValueAt(i, 4));
                            total_quantity += quantity_temp;
                        }
                        String last_row[]={"","TOTAL","","",total_quantity+"","","",Math.floor(total_amount)+""};
                        model.addRow(last_row);
                        rs.close();
                        stmt.close();
                        con.close();
                    }
                    catch(Exception e12345)
                    {
                        System.out.println(e12345);
                    }
                }
            }
            catch(Exception e1234)
            {
                System.out.println(e1234);
            }
        }
        }
                
        );
         qrcode.addActionListener(new ActionListener()
        { 
        public void actionPerformed(ActionEvent e123)
        {
//if(!qrcode.getText().isEmpty())
                        {
                            try{
                                if(sno!=0)
                        model.removeRow(sno);
                            Connection con1=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","oracle");
                            Statement stmt1=con1.createStatement();   
                            ResultSet rs1;
                            String temp_st=qrcode.getText();String[] words = temp_st.split(",");
                            String temp_2="SELECT UNITPRICE FROM PRODUCT_MASTER WHERE DESCRIPTION ='"+words[1].trim()+"' ";
                            rs1=stmt1.executeQuery(temp_2);
                            rs1.next();
                            int temp_unitprise=rs1.getInt("UNITPRICE");
                            String temp_3="SELECT VAT FROM PRODUCT_MASTER WHERE PRODUCT ='"+words[0]+"' ";
                            rs1=stmt1.executeQuery(temp_3);
                            rs1.next();
                            float vat_1=rs1.getFloat("VAT");
                            String temp_4="SELECT DISCOUNT FROM PRODUCT_MASTER WHERE DESCRIPTION ='"+words[1]+"' ";
                            rs1=stmt1.executeQuery(temp_4);
                            rs1.next();
                            float dis=rs1.getFloat("DISCOUNT");
                            amount=(((vat_1*temp_unitprise)/100+temp_unitprise)*1);
                             amount=(amount-(amount*dis)/100);
                            System.out.println("ihegtg"+amount);
                            String row2[]={++sno+"",words[0],words[1],temp_unitprise+"","1",vat_1+"",dis+"",amount+""};
                            model.addRow(row2);
                          qrcode.setText("");
                          double amount_temp=0;
                          int quantity_temp=0;
                           for (int i = 0; i <= contents.getRowCount()-1; i++)
                        {
                             amount_temp = Double.parseDouble((String) contents.getValueAt(i, 7));
                            total_amount += amount_temp;
                        }
                        for (int i = 0; i <= contents.getRowCount()-1; i++)
                        {
                             quantity_temp = Integer.parseInt((String) contents.getValueAt(i, 4));
                            total_quantity += quantity_temp;
                        }
                        String last_row[]={"","TOTAL","","",total_quantity+"","","",Math.floor(total_amount)+""};
                        model.addRow(last_row);
                           }catch(Exception e){};
                        }
}});
        print.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    sno=0;
                    if(input_counter_id.getText().equals("") || input_cashier.getItemAt(input_cashier.getSelectedIndex())==null)
                        JOptionPane.showMessageDialog(jf,"Entered counter id or cashier name","Alert",JOptionPane.ERROR_MESSAGE);
                    else if(tcash_details.getText().equals("") && tcard_details.getText().equals(""))
                        JOptionPane.showMessageDialog(jf,"Enter cash or card details","Alert",JOptionPane.ERROR_MESSAGE);
                    else
                    {
                        int row = contents.getRowCount();
                        int column = contents.getColumnCount();
                        int invoice_id_;
                        String get;
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","oracle");
                        if(input_c_id.getText().equals(""))
                        {
                            if(cash.isSelected())
                            {
                                print_payment="CASH";
                                get="INSERT INTO INVOICE(C_ID,COUNTER_ID,CASHIER,PAYMENT,CARD_NO_,TOTAL) VALUES('0','"+Integer.parseInt(input_counter_id.getText().trim())+"','"+input_cashier.getItemAt(input_cashier.getSelectedIndex())+"','CASH','"+tcash_details.getText()+"','"+contents.getValueAt(row-1,7)+"')";
                                cash_returned.setText("cash to be returned"+(Integer.parseInt(tcash_details.getText())-Double.parseDouble((String)contents.getValueAt(row-1,7)))+"");
                            }
                            else
                            {
                                print_payment="CARD";
                                get="INSERT INTO INVOICE(C_ID,COUNTER_ID,CASHIER,PAYMENT,CARD_NO_,TOTAL) VALUES('0','"+Integer.parseInt(input_counter_id.getText().trim())+"','"+input_cashier.getItemAt(input_cashier.getSelectedIndex())+"','CARD','"+tcard_details.getText()+"','"+contents.getValueAt(row-1,7)+"')";
                            }
                        }
                        else
                        {
                            if(cash.isSelected())
                            {
                                print_payment="CASH";
                                get="INSERT INTO INVOICE(C_ID,COUNTER_ID,CASHIER,PAYMENT,CARD_NO_,TOTAL) VALUES('"+Integer.parseInt(input_c_id.getText().trim())+"','"+Integer.parseInt(input_counter_id.getText().trim())+"','"+input_cashier.getItemAt(input_cashier.getSelectedIndex())+"','CASH','"+tcash_details.getText()+"','"+contents.getValueAt(row-1,7)+"')";
                                cash_returned.setText("CASH RETURNED "+(Integer.parseInt(tcash_details.getText())-Double.parseDouble((String)contents.getValueAt(row-1,7)))+"");
                            }
                            else
                            {
                                print_payment="CARD";
                                get="INSERT INTO INVOICE(C_ID,COUNTER_ID,CASHIER,PAYMENT,CARD_NO_,TOTAL) VALUES('"+Integer.parseInt(input_c_id.getText().trim())+"','"+Integer.parseInt(input_counter_id.getText().trim())+"','"+input_cashier.getItemAt(input_cashier.getSelectedIndex())+"','CARD','"+tcard_details.getText()+"','"+contents.getValueAt(row-1,7)+"')";
                            }
                        }
                        Statement stmt=con.createStatement();
                        stmt.executeUpdate(get);
                        String query2="SELECT MAX(INVOICE_ID) FROM INVOICE";
                        ResultSet rs=stmt.executeQuery(query2);
                        rs.next();
                        invoice_id_=rs.getInt(1);
                        for(int i=0;i<=row-2;i++)
                        {
                            query2="SELECT P_ID FROM PRODUCT_MASTER WHERE DESCRIPTION= '"+contents.getValueAt(i,2).toString()+"'";
                            ResultSet rs2=stmt.executeQuery(query2);
                            rs2.next();
                            query2="INSERT INTO INVOICE_MASTER VALUES ('"+invoice_id_+"','"+rs2.getInt("P_ID")+"','"+contents.getValueAt(i,4)+"')";
                            stmt.executeUpdate(query2);
                            query2="SELECT QUANTITY FROM PRODUCT_MASTER WHERE DESCRIPTION ='"+contents.getValueAt(i,2).toString()+"'";
                            rs2=stmt.executeQuery(query2);
                            rs2.next();
                            int change_quantity=rs2.getInt("QUANTITY")-Integer.parseInt((String) contents.getValueAt(i,4));
                            System.out.print(change_quantity);
                            query2="UPDATE PRODUCT_MASTER SET QUANTITY ="+change_quantity+" WHERE DESCRIPTION ='"+contents.getValueAt(i,2).toString()+"'";
                            stmt.executeUpdate(query2);
                        }
                        //  PRINTING 
                        File f=new File("D:/hi1.html");
                        String print_date="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\n" +
                        "<title>HTML Invoice Template</title>\n" +
                        "<body>\n" +
                        "  <div id=\"address\">\n" +
                        "<center>\n" +
                        "    <p>COMPANY<br>\n" +
                        "	22 KA 7 JYOTI NAGAR <br>\n" +
                        "	NEAR NEW VIDHAN SABHA<br>\n" +
                        "	JAIPUR <br>\n" +
                        "	TEL: 9551848957<br>\n" +
                        "    ishankvasania09@gmail.com.com\n" +
                        "    <br><br>\n" +
                        "    Transaction # "+invoice_id_+"<br>\n" +
                        "    Date "+dateFormat.format(date)+" time "+hour+":"+minute+":"+sec+"<br>\n" +
                        "    </p>\n" +
                        "	</center>\n" +
                        "  </div>\n" +
                        "  \n" +
                        "  <div id=\"content\">\n" +
                        "    <p>\n" +
                        "      Payment Type: "+print_payment+"</p>\n" +
                        "    <hr>\n" +
                        "    <table>\n" +
                        "      <tbody>\n" +
                        "	  <tr>\n" +
                        "	  <col width=\"100\">\n" +
                        "	  <col width=\"100\">\n" +
                        "	  <col width=\"100\">\n" +
                        "	  <col width=\"100\">\n" +
                        "	  <col width=\"100\">\n" +
                        "	  <col width=\"100\">\n" +
                        "	  <td><strong>Product</strong></td>\n" +
                        "	  <td><strong>Description</strong></td>\n" +
                        "	  <td><strong>Unit Price</strong></td>\n" +
                        "	  <td><strong>Quantity</strong></td>\n" +
                        "	  <td><strong>VAT</strong></td>\n" +
                        "	  <td><strong>Discount</strong></td>\n" +
                        "	  <td><strong>Amount</strong></td>\n" +
                        "	  </tr>\n" ;
                        String print_product="";
                        for(int i=0;i<row-1;i++)
                        {
                            print_product+=("      <tr class=\"odd\"><td>"+(String) contents.getValueAt(i,1)+"</td><td>"+(String) contents.getValueAt(i,2)+"</td><td>"+(String) contents.getValueAt(i,3)+"</td><td>"+(String) contents.getValueAt(i,4)+"</td><td>"+(String) contents.getValueAt(i,5)+"</td><td>"+(String) contents.getValueAt(i,6)+"</td><td>"+(String) contents.getValueAt(i,7)+"</td></tr>\n" );
                        }
                        String print_total=
                        "	  <tr>\n" +
                        "	  <td><strong>Total</strong></td>\n" +
                        "	  <td>&nbsp;</td>\n" +
                        "	  <td>&nbsp;</td>\n" +
                        "	  <td><strong>"+contents.getValueAt(row-1,4)+"</strong></td>\n" +
                        "	  <td>&nbsp;</td>\n" +
                        "	  <td>&nbsp;</td>\n" +
                        "	  <td><strong>"+contents.getValueAt(row-1,7)+"</strong></td>\n" +
                        "	  </tr>\n " +
                        "    </tbody></table>\n" ;
                        String print_received="";
                        if(print_payment.equalsIgnoreCase("CASH"))
                        {
                            print_received="<br><strong>CASH RECEIVED "+tcash_details.getText()+"<br> CASH RETURNED "+cash_returned.getText()+"</strong>";
                        }
                        String print_end=
                        "    <hr>\n" +
                        "    <p>\n" +
                        "      <strong>THANK YOU FOR SHOPPING WITH US!!!! <br> HAVE A NICE DAY!</strong> \n" +
                        "      If you have any questions, please feel free to contact us at 9551848957.\n" +
                        "    </p>\n" +
                        "\n" +
                        "    <hr>\n" +
                        "    <p>\n" +
                        "      </p><center><small>1.For any quarries related to warrenty please bring the bill <br>2. Goods once sold won't we taken back or exchanged<br>\n" +
                        "      <br><br>\n" +
                        "      © Company All Rights Reserved\n" +
                        "      </small></center>\n" +
                        "    <p></p>\n" +
                        "  </div><!--end content-->\n" +
                        "</div><!--end page-->\n" +
                        "\n" +
                        "\n" +
                        "</body></html>";
                        String html=print_date+print_product+print_total+print_received+print_end;
                        BufferedWriter br=new BufferedWriter(new FileWriter(f));
                        br.write(html);
                        br.close();
                        Runtime rTime = Runtime.getRuntime();
                        String url = "D:/hi1.html";
                        String browser = "C:/Program Files/Internet Explorer/iexplore.exe ";
                        Process pc = rTime.exec(browser + url);
                       
                        for(int i = row - 1; i >=0; i--)
                        {
                            model.removeRow(i); 
                        }
                        rs.close();
                        stmt.close();
                        con.close();
                    }
                }
                catch(Exception e1)
                {
                    System.out.println(e1);
                }
            }
        }
        );
        jf.add(c_id);
        jf.add(input_c_id);
        jf.add(invoice_id);
        jf.add(set_invoice_id);
        jf.add(product);
        jf.add(description);
        jf.add(quantity);
        jf.add(input_quantity);
        jf.add(unitprice);
        jf.add(set_unitprice);
        jf.add(counter_id);
        jf.add(input_counter_id);
        jf.add(cashier);
        jf.add(input_cashier);
        jf.add(date1);
        jf.add(time);
        panel.add(pane);
        jf.add(panel);
        jf.add(pane);
        bg.add(cash);
        bg.add(card);
        jf.add(cash);
        jf.add(card);
        jf.add(cash_details);
        jf.add(tcash_details);
        jf.add(card_details);
        jf.add(tcard_details);
        jf.add(print);
        jf.add(cash_returned);
        jf.add(qrcode);
        jf.setSize(600,600);
        c_id.setBounds(20,20,100,20);              input_c_id.setBounds(130,20,100,20);     invoice_id.setBounds(320,20,100,20); set_invoice_id.setBounds(430,20,100,20);
        product.setBounds(20,50,100,20);           description.setBounds(130,50,100,20);    counter_id.setBounds(320,50,100,20); input_counter_id.setBounds(430,50,100,20);
        quantity.setBounds(20,80,100,20);          input_quantity.setBounds(130,80,100,20); cashier.setBounds(320,80,100,20);    input_cashier.setBounds(430,80,100,20);
        unitprice.setBounds(20,110,100,20);        set_unitprice.setBounds(130,110,100,20); date1.setBounds(320,110,100,20);      time.setBounds(430,110,100,20);
        contents.setBounds(20,140,540,150);
        pane.setBounds(20, 140, 540, 150); 
        cash.setBounds(60,300,150,20);             card.setBounds(380,300,150,20);        
        cash_details.setBounds(60,330,200,20);     card_details.setBounds(380,330,200,20);
        tcash_details.setBounds(20,360,200,20);    tcard_details.setBounds(350,360,200,20);
        qrcode.setBounds(100,500,400,20);
        print.setBounds(200,400,200,20);
        cash_returned.setBounds(200,430,200,20);
        jf.setLayout(null);
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
public static void main(String args[]) 
{
    Trial_1 t;
        try {
            t = new Trial_1();
            t.start();
        } catch (Exception ex) {
        } 
    
}
}  