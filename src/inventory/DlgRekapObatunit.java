package inventory;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.batasInput;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariunit;
import simrskhanza.DlgCariCaraBayar;

public class DlgRekapObatunit extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psobat,psreg,pskamar;
    private ResultSet rsobat,rsreg,rskamar; 
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    private DlgCariunit asalstok=new DlgCariunit(null,false);
    private int i=0,a=0;
    private double jmlbiaya=0,ttlbiaya=0,jmlmodal=0,ttlmodal=0,jmlembalase=0,ttlembalase=0,jmltuslah=0,ttltuslah=0,jmltotal=0,ttltotal=0,subtotalHargaBeli = 0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRekapObatunit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{"No.","Tanggal","No.RM","Nama Pasien","Jml","Nama Obat","Biaya Obat","Harga Beli","Embalase","Tuslah","Total","Asal Obat","Kategori","Tanggal Beri","DPJP"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 15; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(230);
            }else if(i==14){
                column.setPreferredWidth(230);
            }else{
                column.setPreferredWidth(80);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());  
        
        tabMode2=new DefaultTableModel(null,new Object[]{"No.","Tanggal","No.RM","Nama Pasien","Jml","Nama Obat","Biaya Obat","Harga Beli","Embalase","Tuslah","Total","Asal Obat","Kategori","Tanggal Beri","DPJP"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter1.setModel(tabMode2);

        tbDokter1.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 15; i++) {
            TableColumn column = tbDokter1.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(230);
            }else if(i==14){
                column.setPreferredWidth(230);
            }else{
                column.setPreferredWidth(80);
            }
        }
        tbDokter1.setDefaultRenderer(Object.class, new WarnaTable());        
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        asalstok.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(asalstok.getTable().getSelectedRow()!= -1){                   
                    kdasal.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),0).toString());                    
                    nmasal.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),1).toString());
                }  
                kdasal.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        asalstok.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    asalstok.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
         
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    kdgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 0).toString());
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    kdkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 0).toString());
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
            });
        } 
        
        ChkInput.setSelected(false);
        isForm();
     
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        kdasal = new widget.TextBox();
        kdpenjab = new widget.TextBox();
        kdjenis = new widget.TextBox();
        kdkategori = new widget.TextBox();
        kdgolongan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        label19 = new widget.Label();
        nmpenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        label20 = new widget.Label();
        nmasal = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label21 = new widget.Label();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        status = new widget.ComboBox();
        TabRawat1 = new javax.swing.JTabbedPane();
        scrollPane4 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        scrollPane5 = new widget.ScrollPane();
        tbDokter1 = new widget.Table();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        kdasal.setEditable(false);
        kdasal.setName("kdasal"); // NOI18N
        kdasal.setPreferredSize(new java.awt.Dimension(75, 23));

        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(60, 23));

        kdjenis.setEditable(false);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(75, 23));

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(75, 23));

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(75, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Penggunaan Obat Per Unit ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi1.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi1.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi1.add(Tgl2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi1.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(135, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi1.add(BtnAll);

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(15, 23));
        panelisi1.add(jLabel7);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(null);

        label17.setText("Status :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(37, 23));
        FormInput.add(label17);
        label17.setBounds(10, 10, 43, 23);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label19);
        label19.setBounds(265, 10, 65, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(168, 23));
        FormInput.add(nmpenjab);
        nmpenjab.setBounds(333, 10, 150, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(486, 10, 28, 23);

        label20.setText("Unit :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(538, 10, 60, 23);

        nmasal.setEditable(false);
        nmasal.setName("nmasal"); // NOI18N
        nmasal.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmasal);
        nmasal.setBounds(601, 10, 150, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(754, 10, 28, 23);

        label21.setText("Jenis :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(40, 23));
        FormInput.add(label21);
        label21.setBounds(10, 40, 43, 23);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmjns);
        nmjns.setBounds(56, 40, 150, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        FormInput.add(BtnJenis);
        BtnJenis.setBounds(209, 40, 28, 23);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label22);
        label22.setBounds(265, 40, 65, 23);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmkategori);
        nmkategori.setBounds(333, 40, 150, 23);

        BtnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori.setMnemonic('2');
        BtnKategori.setToolTipText("Alt+2");
        BtnKategori.setName("BtnKategori"); // NOI18N
        BtnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategoriActionPerformed(evt);
            }
        });
        FormInput.add(BtnKategori);
        BtnKategori.setBounds(486, 40, 28, 23);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label23);
        label23.setBounds(538, 40, 60, 23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmgolongan);
        nmgolongan.setBounds(601, 40, 150, 23);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        FormInput.add(BtnGolongan);
        BtnGolongan.setBounds(754, 40, 28, 23);

        status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua Status", "Obat Rawat Jalan", "Obat Rawat Inap" }));
        status.setName("status"); // NOI18N
        FormInput.add(status);
        status.setBounds(56, 10, 181, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat1.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat1.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat1.setName("TabRawat1"); // NOI18N
        TabRawat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawat1MouseClicked(evt);
            }
        });

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane4.setViewportView(tbDokter);

        TabRawat1.addTab("Tanggal Masuk", scrollPane4);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);

        tbDokter1.setAutoCreateRowSorter(true);
        tbDokter1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter1.setName("tbDokter1"); // NOI18N
        scrollPane5.setViewportView(tbDokter1);

        TabRawat1.addTab("Tanggal Keluar", scrollPane5);

        internalFrame1.add(TabRawat1, java.awt.BorderLayout.CENTER);
        TabRawat1.getAccessibleContext().setAccessibleName("Tanggal Keluar");

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabRawat1.getSelectedIndex()==0){
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                int row=tabMode.getRowCount();
                for(int r=0;r<row;r++){  
                    Sequel.menyimpan("temporary","'"+r+"','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Obat Perdokter Poli"); 
                }

                Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptRekapObatPasien.jasper","report","[ Rekap Penggunaan Obat Per Pasien ]","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
            
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdpenjab.setText("");
        nmpenjab.setText("");
        kdasal.setText("");
        nmasal.setText("");
        kdjenis.setText("");
        nmjns.setText("");
        kdkategori.setText("");
        nmkategori.setText("");
        kdgolongan.setText("");
        nmgolongan.setText("");
        TCari.setText("");
        status.setSelectedIndex(0);
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }else if(TabRawat1.getSelectedIndex()==1){
           prosesCari2();
        }
            
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, status, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }else if(TabRawat1.getSelectedIndex()==1){
           prosesCari2();
        }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, status, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,status);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        asalstok.isCek();
        asalstok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        asalstok.setLocationRelativeTo(internalFrame1);
        asalstok.setAlwaysOnTop(false);
        asalstok.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void TabRawat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawat1MouseClicked
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }
    }//GEN-LAST:event_TabRawat1MouseClicked

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRekapObatunit dialog = new DlgRekapObatunit(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnGolongan;
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat1;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel7;
    private widget.TextBox kdasal;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdjenis;
    private widget.TextBox kdkategori;
    private widget.TextBox kdpenjab;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label9;
    private widget.TextBox nmasal;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.TextBox nmpenjab;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ComboBox status;
    private widget.Table tbDokter;
    private widget.Table tbDokter1;
    // End of variables declaration//GEN-END:variables

//    private void prosesCari() {             
//        try{   
//            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
//            Valid.tabelKosong(tabMode);
//            if((status.getSelectedIndex()==0)&&nmpenjab.getText().equals("")&&TCari.getText().equals("")){
//                psreg=koneksi.prepareStatement(
//                   "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien "+
//                   "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                   "where reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
//            }else{
//                psreg=koneksi.prepareStatement(
//                   "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien "+
//                   "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? "+
//                   "and reg_periksa.status_lanjut like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
//                   "and (reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ?) order by reg_periksa.tgl_registrasi");
//            }
//           
//            try {
//                if((status.getSelectedIndex()==0)&&nmpenjab.getText().equals("")&&TCari.getText().equals("")){
//                    psreg.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
//                    psreg.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
//                }else{
//                    psreg.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
//                    psreg.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
//                    psreg.setString(3,"%"+status.getSelectedItem().toString().replaceAll("Obat Rawat Jalan","Ralan").replaceAll("Obat Rawat Inap","Ranap").replaceAll("Semua Status","")+"%");
//                    psreg.setString(4,"%"+kdpenjab.getText()+nmpenjab.getText()+"%");
//                    psreg.setString(5,"%"+TCari.getText().trim()+"%");
//                    psreg.setString(6,"%"+TCari.getText().trim()+"%");
//                }
//                rsreg=psreg.executeQuery();
//                i=1;
//                ttlbiaya=0;ttlmodal=0;ttlembalase=0;ttltuslah=0;ttltotal=0;
//                while(rsreg.next()){
//                    // Query obat berdasarkan kondisi
//               if ((status.getSelectedIndex() == 0) && nmjns.getText().equals("") && nmkategori.getText().equals("") && nmgolongan.getText().equals("") && nmasal.getText().equals("")) {
//                    psobat = koneksi.prepareStatement(
//                        "SELECT detail_pemberian_obat.kode_brng, databarang.nama_brng, SUM(detail_pemberian_obat.jml) AS jml, " +
//                        "(SUM(detail_pemberian_obat.total) - SUM(detail_pemberian_obat.embalase + detail_pemberian_obat.tuslah)) AS biaya, " +
//                        "SUM(detail_pemberian_obat.h_beli * detail_pemberian_obat.jml) AS modal, " +
//                        "SUM(detail_pemberian_obat.embalase) AS embalase, SUM(detail_pemberian_obat.tuslah) AS tuslah, " +
//                        "SUM(detail_pemberian_obat.total) AS total, " +
//                        "MAX(detail_pemberian_obat.asal_resep) AS asal_resep, " + // Menambahkan asal_resep dengan MAX agar ditampilkan dengan benar   
//                        "CASE WHEN detail_pemberian_obat.no_rawat IS NOT NULL THEN 'Obat Rawatan' ELSE 'Obat Lain' END AS kategori " + // Kolom kategori yang telah ditambahkan
//                        "FROM detail_pemberian_obat " +
//                        "INNER JOIN reg_periksa ON detail_pemberian_obat.no_rawat = reg_periksa.no_rawat " +
//                        "INNER JOIN databarang ON detail_pemberian_obat.kode_brng = databarang.kode_brng " +
//                        "WHERE detail_pemberian_obat.no_rawat = ? " +
//                        "GROUP BY detail_pemberian_obat.kode_brng " + // Dikelompokkan berdasarkan kode_brng
//                        "ORDER BY databarang.nama_brng"
//                    );
//                } else {
//                    psobat = koneksi.prepareStatement(
//                        "SELECT detail_pemberian_obat.kode_brng, databarang.nama_brng, SUM(detail_pemberian_obat.jml) AS jml, " +
//                        "(SUM(detail_pemberian_obat.total) - SUM(detail_pemberian_obat.embalase + detail_pemberian_obat.tuslah)) AS biaya, " +
//                        "SUM(detail_pemberian_obat.h_beli * detail_pemberian_obat.jml) AS modal, " +
//                        "SUM(detail_pemberian_obat.embalase) AS embalase, SUM(detail_pemberian_obat.tuslah) AS tuslah, " +
//                        "SUM(detail_pemberian_obat.total) AS total, " +
//                        "MAX(detail_pemberian_obat.asal_resep) AS asal_resep, " + // Menambahkan asal_resep dengan MAX agar ditampilkan dengan benar
//                        "CASE WHEN detail_pemberian_obat.no_rawat IS NOT NULL THEN 'Obat Rawatan' ELSE 'Obat Lain' END AS kategori " + // Kolom kategori yang telah ditambahkan
//                        "FROM detail_pemberian_obat " +
//                        "INNER JOIN reg_periksa ON detail_pemberian_obat.no_rawat = reg_periksa.no_rawat " +
//                        "INNER JOIN databarang ON detail_pemberian_obat.kode_brng = databarang.kode_brng " +
//                        "WHERE detail_pemberian_obat.no_rawat = ? " +
//                        "GROUP BY detail_pemberian_obat.kode_brng " + // Dikelompokkan berdasarkan kode_brng
//                        "ORDER BY databarang.nama_brng"
//                    );
//                }
//    try {
//        if((status.getSelectedIndex()==0) && nmjns.getText().equals("") && nmkategori.getText().equals("") && nmgolongan.getText().equals("") && nmasal.getText().equals("")){
//            psobat.setString(1, rsreg.getString("no_rawat"));
//            } else {
//                psobat.setString(1, rsreg.getString("no_rawat"));
//                psobat.setString(2, "%" + status.getSelectedItem().toString().replaceAll("Obat Rawat Jalan", "Ralan").replaceAll("Obat Rawat Inap", "Ranap").replaceAll("Semua Status", "") + "%");
//                psobat.setString(3, "%" + kdjenis.getText() + nmjns.getText() + "%");
//                psobat.setString(4, "%" + kdkategori.getText() + nmkategori.getText() + "%");
//                psobat.setString(5, "%" + kdgolongan.getText() + nmgolongan.getText() + "%");
//                psobat.setString(6, "%" + kdasal.getText() + nmasal.getText() + "%");
//            }
//            rsobat = psobat.executeQuery();
//            if (rsobat.next()) {
//                rsobat.beforeFirst();
//                a = 1;
//                jmlbiaya = 0; jmlembalase = 0; jmltotal = 0; jmltuslah = 0;  jmlmodal = 0;
//                while (rsobat.next()) {
//                    if (a == 1) {
//                        tabMode.addRow(new String[]{
//                        i + "",
//                                    rsreg.getString("tgl_registrasi"),
//                                    rsreg.getString("no_rkm_medis"),
//                                    rsreg.getString("nm_pasien"),
//                                    rsobat.getString(3),
//                                    rsobat.getString(1) + " " + rsobat.getString(2),
//                                    Valid.SetAngka(rsobat.getDouble(4)),
//                                    Valid.SetAngka(rsobat.getDouble(5)),
//                                    Valid.SetAngka(rsobat.getDouble(6)),
//                                    Valid.SetAngka(rsobat.getDouble(7)),
//                                    Valid.SetAngka(rsobat.getDouble(8)),
//                                    rsobat.getString(9),   
//                                    rsobat.getString(10)  
//                    });
//                        i++;
//                    } else {
//                         tabMode.addRow(new String[]{
//                            "", "", "", "", rsobat.getString(3), rsobat.getString(1) + " " + rsobat.getString(2),
//                            Valid.SetAngka(rsobat.getDouble(4)), // Biaya
//                            Valid.SetAngka(rsobat.getDouble(5)), // Harga Beli
//                            Valid.SetAngka(rsobat.getDouble(6)), // Embalase
//                            Valid.SetAngka(rsobat.getDouble(7)), // Tuslah
//                            Valid.SetAngka(rsobat.getDouble(8)),  // Total
//                            rsobat.getString(9), // 
//                            rsobat.getString(10) // 
//                        });
//                    }
//                    // Penjumlahan
//                    jmlbiaya = jmlbiaya + rsobat.getDouble(4);
//                    ttlbiaya = ttlbiaya + rsobat.getDouble(4);
//                    jmlmodal = jmlmodal + rsobat.getDouble(5);
//                    ttlmodal = ttlmodal + rsobat.getDouble(5);
//                    jmlembalase = jmlembalase + rsobat.getDouble(6);
//                    ttlembalase = ttlembalase + rsobat.getDouble(6);
//                    jmltuslah = jmltuslah + rsobat.getDouble(7);
//                    ttltuslah = ttltuslah + rsobat.getDouble(7);
//                    jmltotal = jmltotal + rsobat.getDouble(8);
//                    ttltotal = ttltotal + rsobat.getDouble(8);
//                    a++;
//                }
//                if (jmltotal > 0) {
//                    tabMode.addRow(new String[]{
//                        "", "", "", "", "", "Subtotal :", Valid.SetAngka(jmlbiaya), Valid.SetAngka(jmlmodal), Valid.SetAngka(jmlembalase), Valid.SetAngka(jmltuslah), Valid.SetAngka(jmltotal)
//                    });
//                }
//            }
//                    } catch (Exception e) {
//                        System.out.println("Notif : "+e);
//                    } finally{
//                        if(rsobat!=null){
//                            rsobat.close();
//                        }
//                        if(psobat!=null){
//                            psobat.close();
//                        }
//                    }
//                }
//                if(ttltotal>0){
//                    tabMode.addRow(new Object[]{">>","Total ",":","","","",Valid.SetAngka(ttlbiaya),Valid.SetAngka(ttlmodal),Valid.SetAngka(ttlembalase),Valid.SetAngka(ttltuslah),Valid.SetAngka(ttltotal)});
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            } finally{
//                if(rsreg!=null){
//                    rsreg.close();
//                }
//                if(psreg!=null){
//                    psreg.close();
//                }
//            }
//                
//           this.setCursor(Cursor.getDefaultCursor());             
//        }catch(Exception e){
//            System.out.println("Catatan  "+e);
//        }        
//    }

//    private void prosesCari() {
//    try {
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        Valid.tabelKosong(tabMode);
//        if ((status.getSelectedIndex() == 0) && nmpenjab.getText().equals("") && TCari.getText().equals("")) {
//            psreg = koneksi.prepareStatement(
//                "SELECT detail_pemberian_obat.tgl_perawatan, reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien " +
//                "FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
//                "WHERE reg_periksa.stts <> 'Batal' AND reg_periksa.tgl_registrasi BETWEEN ? AND ? " +
//                "ORDER BY detail_pemberian_obat.tgl_perawatan");
//        } else {
//            psreg = koneksi.prepareStatement(
//                "SELECT detail_pemberian_obat.tgl_perawatan, reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien " +
//                "FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
//                "INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj " +
//                "WHERE reg_periksa.stts <> 'Batal' AND reg_periksa.tgl_registrasi BETWEEN ? AND ? " +
//                "AND reg_periksa.status_lanjut LIKE ? AND CONCAT(reg_periksa.kd_pj, penjab.png_jawab) LIKE ? " +
//                "AND (reg_periksa.no_rkm_medis LIKE ? OR pasien.nm_pasien LIKE ?) " +
//                "ORDER BY detail_pemberian_obat.tgl_perawatan");
//        }
//
//        try {
//            if ((status.getSelectedIndex() == 0) && nmpenjab.getText().equals("") && TCari.getText().equals("")) {
//                psreg.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
//                psreg.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
//            } else {
//                psreg.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
//                psreg.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
//                psreg.setString(3, "%" + status.getSelectedItem().toString().replaceAll("Obat Rawat Jalan", "Ralan").replaceAll("Obat Rawat Inap", "Ranap").replaceAll("Semua Status", "") + "%");
//                psreg.setString(4, "%" + kdpenjab.getText() + nmpenjab.getText() + "%");
//                psreg.setString(5, "%" + TCari.getText().trim() + "%");
//                psreg.setString(6, "%" + TCari.getText().trim() + "%");
//            }
//
//            rsreg = psreg.executeQuery();
//            int i = 1;
//            double ttlbiaya = 0, ttlmodal = 0, ttlembalase = 0, ttltuslah = 0, ttltotal = 0;
//
//            while (rsreg.next()) {
//                // Query obat berdasarkan kondisi
//                PreparedStatement psobat = koneksi.prepareStatement(
//                    "SELECT " +
//                    "detail_pemberian_obat.kode_brng, " +
//                    "databarang.nama_brng, " +
//                    "detail_pemberian_obat.jml AS jml, " +
//                    "(detail_pemberian_obat.total - (detail_pemberian_obat.embalase + detail_pemberian_obat.tuslah)) AS biaya, " +
//                    "(detail_pemberian_obat.h_beli * detail_pemberian_obat.jml) AS modal, " +
//                    "detail_pemberian_obat.embalase AS embalase, " +
//                    "detail_pemberian_obat.tuslah AS tuslah, " +
//                    "detail_pemberian_obat.total AS total, " +
//                    "detail_pemberian_obat.asal_resep AS asal_resep, " +
//                    "CASE " +
//                    "WHEN detail_pemberian_obat.no_rawat IS NOT NULL THEN 'Obat Rawatan' " +
//                    "ELSE 'Obat Lain' END AS kategori " +
//                    "FROM detail_pemberian_obat " +
//                    "INNER JOIN reg_periksa ON detail_pemberian_obat.no_rawat = reg_periksa.no_rawat " +
//                    "INNER JOIN databarang ON detail_pemberian_obat.kode_brng = databarang.kode_brng " +
//                    "WHERE reg_periksa.no_rawat = ? "
//                );
//
//                psobat.setString(1, rsreg.getString("no_rawat"));
//                ResultSet rsobat = psobat.executeQuery();
//
//                while (rsobat.next()) {
//                    tabMode.addRow(new String[]{
//                        String.valueOf(i++), rsreg.getString("tgl_registrasi"),
//                        rsreg.getString("no_rkm_medis"), rsreg.getString("nm_pasien"),
//                        rsobat.getString(3), rsobat.getString(1) + " " + rsobat.getString(2),
//                        Valid.SetAngka(rsobat.getDouble(4)), // Biaya
//                        Valid.SetAngka(rsobat.getDouble(5)), // Modal
//                        "0", // Embalase (nilai 0)
//                        "0", // Tuslah (nilai 0)
//                        Valid.SetAngka(rsobat.getDouble(8)), // Total (indeks 8, karena total adalah kolom ke-9)
//                        rsobat.getString(9), // Asal Resep (indeks 9, karena asal_resep adalah kolom ke-10)
//                        rsobat.getString(10)  // Kategori (indeks 10, karena kategori adalah kolom ke-11)
//                    });
//                    ttlbiaya += rsobat.getDouble(4);
//                    ttlmodal += rsobat.getDouble(5);
//                    ttltotal += rsobat.getDouble(8);
//                }
//                rsobat.close();
//                psobat.close();
//
//                // Query resep pulang
//                PreparedStatement psResepPulang = koneksi.prepareStatement(
//                    "SELECT " +
//                    "resep_pulang.kode_brng, " +
//                    "databarang.nama_brng, " +
//                    "resep_pulang.jml_barang, " +
//                    "SUM(resep_pulang.total) AS biaya, " +
//                    "databarang.h_beli, " +
//                    "resep_pulang.total, " +
//                    "resep_pulang.asal_resep, " +
//                    "CASE WHEN resep_pulang.no_rawat IS NOT NULL THEN 'Obat Pulang' ELSE 'Obat Lain' END AS kategori " +
//                    "FROM resep_pulang " +
//                    "INNER JOIN databarang ON resep_pulang.kode_brng = databarang.kode_brng " +
//                    "WHERE resep_pulang.no_rawat = ? " +
//                    "GROUP BY " +
//                    "resep_pulang.kode_brng, " +
//                    "databarang.nama_brng, " +
//                    "resep_pulang.jml_barang, " +
//                    "databarang.h_beli, " +
//                    "resep_pulang.total, " +
//                    "resep_pulang.asal_resep, " +
//                    "resep_pulang.no_rawat " +
//                    "ORDER BY databarang.nama_brng" );
//
//                psResepPulang.setString(1, rsreg.getString("no_rawat"));
//                ResultSet rsResepPulang = psResepPulang.executeQuery();
//
//                while (rsResepPulang.next()) {
//                    tabMode.addRow(new String[]{
//                       String.valueOf(i++), rsreg.getString("tgl_registrasi"),
//                        rsreg.getString("no_rkm_medis"), rsreg.getString("nm_pasien"),
//                        rsResepPulang.getString(3), rsResepPulang.getString(1) + " " + rsResepPulang.getString(2),
//                        Valid.SetAngka(rsResepPulang.getDouble(4)), // Biaya
//                        Valid.SetAngka(rsResepPulang.getDouble(5)), // Modal
//                        "0", // Embalase (nilai 0)
//                        "0", // Tuslah (nilai 0)
//                        Valid.SetAngka(rsResepPulang.getDouble(6)), // Total
//                        rsResepPulang.getString(7), // Asal Resep
//                        rsResepPulang.getString(8)  // Kategori
//                    });
//                }
//
//                rsResepPulang.close();
//                psResepPulang.close();
//            }
//
//            // Menampilkan total biaya, modal, dan total
//            tabMode.addRow(new String[]{
//                "Total", "", "", "", "", "", 
//                Valid.SetAngka(ttlbiaya), 
//                Valid.SetAngka(ttlmodal), 
//                Valid.SetAngka(0), // Total Embalase
//                Valid.SetAngka(0), // Total Tuslah
//                Valid.SetAngka(ttltotal), 
//                "", 
//                ""
//            });
//
//        } catch (SQLException e) {
//            System.out.println("Error: " + e);
//        } finally {
//            rsreg.close();
//            psreg.close();
//            this.setCursor(Cursor.getDefaultCursor());
//        }
//    } catch (SQLException e) {
//        System.out.println("Error: " + e);
//    }
//}
    
    private void prosesCari() {
        PreparedStatement psreg = null;
        ResultSet rsreg = null;

        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.tabelKosong(tabMode);

            // Memastikan query yang benar
            // Memastikan query yang benar
        if ((status.getSelectedIndex() == 0) && nmpenjab.getText().equals("") && nmasal.getText().equals("") && TCari.getText().equals("")) {
            psreg = koneksi.prepareStatement(
                "SELECT reg_periksa.tgl_registrasi, reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien " +
                "FROM reg_periksa " +
                "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
                "WHERE reg_periksa.stts <> 'Batal' AND reg_periksa.tgl_registrasi BETWEEN ? AND ? " +
                "ORDER BY reg_periksa.tgl_registrasi");
        } else {
            psreg = koneksi.prepareStatement(
                "SELECT reg_periksa.tgl_registrasi, reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien " +
                "FROM reg_periksa " +
                "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
                "INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj " +
                "WHERE reg_periksa.stts <> 'Batal' AND reg_periksa.tgl_registrasi BETWEEN ? AND ? " +
                "AND reg_periksa.status_lanjut LIKE ? " +
                "AND CONCAT(reg_periksa.kd_pj, penjab.png_jawab) LIKE ? " +
                "AND (reg_periksa.no_rkm_medis LIKE ? OR pasien.nm_pasien LIKE ?) " +
                "ORDER BY reg_periksa.tgl_registrasi");
        }

        try {
            // Set parameter untuk query tanpa filter tambahan
            if ((status.getSelectedIndex() == 0) && nmpenjab.getText().equals("") && TCari.getText().equals("")) {
                psreg.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + "")); // Tanggal mulai
                psreg.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + "")); // Tanggal akhir
            } else {
                // Set parameter untuk query dengan filter tambahan
                psreg.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + "")); // Tanggal mulai
                psreg.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + "")); // Tanggal akhir

                // Filter status
                String statusLanjut = status.getSelectedItem().toString()
                    .replaceAll("Obat Rawat Jalan", "Ralan")
                    .replaceAll("Obat Rawat Inap", "Ranap")
                    .replaceAll("Semua Status", "");
                psreg.setString(3, "%" + statusLanjut + "%");

                // Filter kode penjamin
                psreg.setString(4, "%" + kdpenjab.getText() + nmpenjab.getText() + "%");

                // Filter nomor rekam medis atau nama pasien
                String cariText = TCari.getText().trim();
                psreg.setString(5, "%" + cariText + "%"); // Untuk no_rkm_medis
                psreg.setString(6, "%" + cariText + "%"); // Untuk nm_pasien
            }

                rsreg = psreg.executeQuery();
                int i = 1;


                while (rsreg.next()) {
                    double ttlbiaya = 0, ttlmodal = 0, ttlembalase = 0, ttltuslah = 0, ttltotal = 0;
                    // Query obat berdasarkan kondisi
                    PreparedStatement psobat = koneksi.prepareStatement(
                        "SELECT " +
                        "detail_pemberian_obat.kode_brng, " +                  // Index 1
                        "databarang.nama_brng, " +                             // Index 2
                        "detail_pemberian_obat.jml AS jml, " +                 // Index 3
                        "(detail_pemberian_obat.total - (detail_pemberian_obat.embalase + detail_pemberian_obat.tuslah)) AS biaya, " +  // Index 4
                        "(detail_pemberian_obat.h_beli * detail_pemberian_obat.jml) AS modal, " +                                       // Index 5
                        "detail_pemberian_obat.embalase AS embalase, " +       // Index 6
                        "detail_pemberian_obat.tuslah AS tuslah, " +           // Index 7
                        "detail_pemberian_obat.total AS total, " +            // Index 8
                        "detail_pemberian_obat.asal_resep AS asal_resep, " +   // Index 9
                        "CASE " +
                        "WHEN detail_pemberian_obat.no_rawat IS NOT NULL THEN 'Obat Rawatan' " +
                        "ELSE 'Obat Lain' END AS kategori, " +                // Index 10
                        "detail_pemberian_obat.tgl_perawatan, " +             // Index 11
                        "dokter.nm_dokter " +                                 // Index 12
                        "FROM detail_pemberian_obat " +
                        "INNER JOIN reg_periksa ON detail_pemberian_obat.no_rawat = reg_periksa.no_rawat " +
                        "INNER JOIN databarang ON detail_pemberian_obat.kode_brng = databarang.kode_brng " +
                        "LEFT JOIN dpjp_ranap ON dpjp_ranap.no_rawat = reg_periksa.no_rawat " +
                        "LEFT JOIN dokter ON dpjp_ranap.kd_dokter = dokter.kd_dokter " +
                        "WHERE reg_periksa.no_rawat = ? " +
                        "ORDER BY detail_pemberian_obat.tgl_perawatan"
                    );

                    // Menyetel parameter
                    psobat.setString(1, rsreg.getString("no_rawat"));
                    ResultSet rsobat = psobat.executeQuery();

                    // Memproses hasil
                    while (rsobat.next()) {
                        tabMode.addRow(new String[]{
                            String.valueOf(i++), rsreg.getString("tgl_registrasi"),
                            rsreg.getString("no_rkm_medis"), rsreg.getString("nm_pasien"),
                            rsobat.getString(3),                    // Jumlah
                            rsobat.getString(1) + " " + rsobat.getString(2),  // Kode dan Nama Barang
                            Valid.SetAngka(rsobat.getDouble(4)),    // Biaya
                            Valid.SetAngka(rsobat.getDouble(5)),    // Modal
                            Valid.SetAngka(rsobat.getDouble(6)),    // Embalase
                            Valid.SetAngka(rsobat.getDouble(7)),    // Tuslah
                            Valid.SetAngka(rsobat.getDouble(8)),    // Total
                            rsobat.getString(9),                    // Asal Resep
                            rsobat.getString(10),                   // Kategori
                            rsobat.getString(11),                   // Tanggal Pemberian
                            rsobat.getString(12)                    // Nama Dokter
                        });
                        ttlbiaya += rsobat.getDouble(4);
                        ttlembalase += rsobat.getDouble(6);
                        ttltuslah += rsobat.getDouble(7);
                        ttlmodal += rsobat.getDouble(5);
                        ttltotal += rsobat.getDouble(8);
                    }
                    rsobat.close();
                    psobat.close();

                    // Query resep pulang
                    PreparedStatement psResepPulang = koneksi.prepareStatement(
                        "SELECT " +
                        "resep_pulang.kode_brng, " +                        // Index 1
                        "databarang.nama_brng, " +                          // Index 2
                        "resep_pulang.jml_barang, " +                       // Index 3
                        "SUM(resep_pulang.total) AS biaya, " +              // Index 4
                        "databarang.h_beli, " +                             // Index 5
                        "resep_pulang.total, " +                            // Index 6
                        "resep_pulang.asal_resep, " +                       // Index 7
                        "CASE WHEN resep_pulang.no_rawat IS NOT NULL THEN 'Obat Pulang' ELSE 'Obat Lain' END AS kategori, " + // Index 8
                        "resep_pulang.tanggal, " +                          // Index 9
                        "dokter.nm_dokter " +                               // Index 10
                        "FROM resep_pulang " +
                        "INNER JOIN databarang ON resep_pulang.kode_brng = databarang.kode_brng " +
                        "LEFT JOIN reg_periksa ON resep_pulang.no_rawat = reg_periksa.no_rawat " +
                        "LEFT JOIN dpjp_ranap ON dpjp_ranap.no_rawat = reg_periksa.no_rawat " +
                        "LEFT JOIN dokter ON dpjp_ranap.kd_dokter = dokter.kd_dokter " +
                        "WHERE resep_pulang.no_rawat = ? " +
                        "GROUP BY " +
                        "resep_pulang.kode_brng, " +
                        "databarang.nama_brng, " +
                        "resep_pulang.jml_barang, " +
                        "databarang.h_beli, " +
                        "resep_pulang.total, " +
                        "resep_pulang.asal_resep, " +
                        "resep_pulang.no_rawat, " +
                        "dokter.nm_dokter " +
                        "ORDER BY resep_pulang.tanggal");

                    psResepPulang.setString(1, rsreg.getString("no_rawat"));
                    ResultSet rsResepPulang = psResepPulang.executeQuery();


                    while (rsResepPulang.next()) {
                        tabMode.addRow(new String[]{
                            String.valueOf(i++), rsreg.getString("tgl_registrasi"),
                            rsreg.getString("no_rkm_medis"), rsreg.getString("nm_pasien"),
                            rsResepPulang.getString(3),                   // Jumlah Barang
                            rsResepPulang.getString(1) + " " + rsResepPulang.getString(2),  // Kode dan Nama Barang
                            Valid.SetAngka(rsResepPulang.getDouble(4)),   // Biaya
                            Valid.SetAngka(rsResepPulang.getDouble(5)),   // Modal
                            "0",                                         // Embalase (nilai 0)
                            "0",                                         // Tuslah (nilai 0)
                            Valid.SetAngka(rsResepPulang.getDouble(6)),   // Total
                            rsResepPulang.getString(7),                  // Asal Resep
                            rsResepPulang.getString(8),                  // Kategori
                            rsResepPulang.getString(9),                  // Tanggal Beri
                            rsResepPulang.getString(10)                  // Nama Dokter
                        });
                    }


                    rsResepPulang.close();
                    psResepPulang.close();

                    // Menampilkan total biaya, modal, dan total
                tabMode.addRow(new String[]{
                    "Total", "", "", "", "", "", 
                    Valid.SetAngka(ttlbiaya), 
                    Valid.SetAngka(ttlmodal), 
                    Valid.SetAngka(ttlembalase), // Total Embalase
                    Valid.SetAngka(ttltuslah), // Total Tuslah
                    Valid.SetAngka(ttltotal), 
                    "", 
                    ""
                });
                }

            } catch (SQLException e) {
                System.out.println("Error: " + e);
            } finally {
                if (rsreg != null) {
                    try {
                        rsreg.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing ResultSet: " + e);
                    }
                }
                if (psreg != null) {
                    try {
                        psreg.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing PreparedStatement: " + e);
                    }
                }
                this.setCursor(Cursor.getDefaultCursor());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
    
    private void prosesCari2() {
        PreparedStatement psreg = null;
        ResultSet rsreg = null;

        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.tabelKosong(tabMode2);

            // Memastikan query yang benar
            // Memastikan query yang benar
        if ((status.getSelectedIndex() == 0) && nmpenjab.getText().equals("") && nmasal.getText().equals("") && TCari.getText().equals("")) {
            psreg = koneksi.prepareStatement(
                "SELECT kamar_inap.tgl_keluar, reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien " +
                "FROM kamar_inap " +
                "INNER JOIN reg_periksa ON kamar_inap.no_rawat = reg_periksa.no_rawat " +
                "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
                "WHERE reg_periksa.stts <> 'Batal' AND kamar_inap.tgl_keluar BETWEEN ? AND ? " +
                "ORDER BY kamar_inap.tgl_keluar");
        } else {
            psreg = koneksi.prepareStatement(
                "SELECT kamar_inap.tgl_keluar, reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien " +
                "FROM kamar_inap " +
                "INNER JOIN reg_periksa ON kamar_inap.no_rawat = reg_periksa.no_rawat " +
                "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
                "INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj " +
                "WHERE reg_periksa.stts <> 'Batal' AND kamar_inap.tgl_keluar BETWEEN ? AND ? " +
                "AND reg_periksa.status_lanjut LIKE ? " +
                "AND CONCAT(reg_periksa.kd_pj, penjab.png_jawab) LIKE ? " +
                "AND (reg_periksa.no_rkm_medis LIKE ? OR pasien.nm_pasien LIKE ?) " +
                "ORDER BY kamar_inap.tgl_keluar");
        }

        try {
            // Set parameter untuk query tanpa filter tambahan
            if ((status.getSelectedIndex() == 0) && nmpenjab.getText().equals("") && TCari.getText().equals("")) {
                psreg.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + "")); // Tanggal mulai
                psreg.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + "")); // Tanggal akhir
            } else {
                // Set parameter untuk query dengan filter tambahan
                psreg.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + "")); // Tanggal mulai
                psreg.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + "")); // Tanggal akhir

                // Filter status
                String statusLanjut = status.getSelectedItem().toString()
                    .replaceAll("Obat Rawat Jalan", "Ralan")
                    .replaceAll("Obat Rawat Inap", "Ranap")
                    .replaceAll("Semua Status", "");
                psreg.setString(3, "%" + statusLanjut + "%");

                // Filter kode penjamin
                psreg.setString(4, "%" + kdpenjab.getText() + nmpenjab.getText() + "%");

                // Filter nomor rekam medis atau nama pasien
                String cariText = TCari.getText().trim();
                psreg.setString(5, "%" + cariText + "%"); // Untuk no_rkm_medis
                psreg.setString(6, "%" + cariText + "%"); // Untuk nm_pasien
            }

                rsreg = psreg.executeQuery();
                int i = 1;


                while (rsreg.next()) {
                    double ttlbiaya = 0, ttlmodal = 0, ttlembalase = 0, ttltuslah = 0, ttltotal = 0;
                    // Query obat berdasarkan kondisi
                    PreparedStatement psobat = koneksi.prepareStatement(
                        "SELECT " +
                        "detail_pemberian_obat.kode_brng, " +                  // Index 1
                        "databarang.nama_brng, " +                             // Index 2
                        "detail_pemberian_obat.jml AS jml, " +                 // Index 3
                        "(detail_pemberian_obat.total - (detail_pemberian_obat.embalase + detail_pemberian_obat.tuslah)) AS biaya, " +  // Index 4
                        "(detail_pemberian_obat.h_beli * detail_pemberian_obat.jml) AS modal, " +                                       // Index 5
                        "detail_pemberian_obat.embalase AS embalase, " +       // Index 6
                        "detail_pemberian_obat.tuslah AS tuslah, " +           // Index 7
                        "detail_pemberian_obat.total AS total, " +            // Index 8
                        "detail_pemberian_obat.asal_resep AS asal_resep, " +   // Index 9
                        "CASE " +
                        "WHEN detail_pemberian_obat.no_rawat IS NOT NULL THEN 'Obat Rawatan' " +
                        "ELSE 'Obat Lain' END AS kategori, " +                // Index 10
                        "detail_pemberian_obat.tgl_perawatan, " +             // Index 11
                        "dokter.nm_dokter " +                                 // Index 12
                        "FROM detail_pemberian_obat " +
                        "INNER JOIN reg_periksa ON detail_pemberian_obat.no_rawat = reg_periksa.no_rawat " +
                        "INNER JOIN databarang ON detail_pemberian_obat.kode_brng = databarang.kode_brng " +
                        "LEFT JOIN dpjp_ranap ON dpjp_ranap.no_rawat = reg_periksa.no_rawat " +
                        "LEFT JOIN dokter ON dpjp_ranap.kd_dokter = dokter.kd_dokter " +
                        "WHERE reg_periksa.no_rawat = ? " +
                        "ORDER BY detail_pemberian_obat.tgl_perawatan"
                    );

                    // Menyetel parameter
                    psobat.setString(1, rsreg.getString("no_rawat"));
                    ResultSet rsobat = psobat.executeQuery();

                    // Memproses hasil
                    while (rsobat.next()) {
                        tabMode2.addRow(new String[]{
                            String.valueOf(i++), rsreg.getString("tgl_keluar"),
                            rsreg.getString("no_rkm_medis"), rsreg.getString("nm_pasien"),
                            rsobat.getString(3),                    // Jumlah
                            rsobat.getString(1) + " " + rsobat.getString(2),  // Kode dan Nama Barang
                            Valid.SetAngka(rsobat.getDouble(4)),    // Biaya
                            Valid.SetAngka(rsobat.getDouble(5)),    // Modal
                            Valid.SetAngka(rsobat.getDouble(6)),    // Embalase
                            Valid.SetAngka(rsobat.getDouble(7)),    // Tuslah
                            Valid.SetAngka(rsobat.getDouble(8)),    // Total
                            rsobat.getString(9),                    // Asal Resep
                            rsobat.getString(10),                   // Kategori
                            rsobat.getString(11),                   // Tanggal Pemberian
                            rsobat.getString(12)                    // Nama Dokter
                        });
                        ttlbiaya += rsobat.getDouble(4);
                        ttlembalase += rsobat.getDouble(6);
                        ttltuslah += rsobat.getDouble(7);
                        ttlmodal += rsobat.getDouble(5);
                        ttltotal += rsobat.getDouble(8);
                    }
                    rsobat.close();
                    psobat.close();


                    // Query resep pulang
                    PreparedStatement psResepPulang = koneksi.prepareStatement(
                        "SELECT " +
                        "resep_pulang.kode_brng, " +                        // Index 1
                        "databarang.nama_brng, " +                          // Index 2
                        "resep_pulang.jml_barang, " +                       // Index 3
                        "SUM(resep_pulang.total) AS biaya, " +              // Index 4
                        "databarang.h_beli, " +                             // Index 5
                        "resep_pulang.total, " +                            // Index 6
                        "resep_pulang.asal_resep, " +                       // Index 7
                        "CASE WHEN resep_pulang.no_rawat IS NOT NULL THEN 'Obat Pulang' ELSE 'Obat Lain' END AS kategori, " + // Index 8
                        "resep_pulang.tanggal, " +                          // Index 9
                        "dokter.nm_dokter " +                               // Index 10
                        "FROM resep_pulang " +
                        "INNER JOIN databarang ON resep_pulang.kode_brng = databarang.kode_brng " +
                        "LEFT JOIN reg_periksa ON resep_pulang.no_rawat = reg_periksa.no_rawat " +
                        "LEFT JOIN dpjp_ranap ON dpjp_ranap.no_rawat = reg_periksa.no_rawat " +
                        "LEFT JOIN dokter ON dpjp_ranap.kd_dokter = dokter.kd_dokter " +
                        "WHERE resep_pulang.no_rawat = ? " +
                        "GROUP BY " +
                        "resep_pulang.kode_brng, " +
                        "databarang.nama_brng, " +
                        "resep_pulang.jml_barang, " +
                        "databarang.h_beli, " +
                        "resep_pulang.total, " +
                        "resep_pulang.asal_resep, " +
                        "resep_pulang.no_rawat, " +
                        "dokter.nm_dokter " +
                        "ORDER BY resep_pulang.tanggal");

                    psResepPulang.setString(1, rsreg.getString("no_rawat"));
                    ResultSet rsResepPulang = psResepPulang.executeQuery();


                    while (rsResepPulang.next()) {
                        tabMode2.addRow(new String[]{
                            String.valueOf(i++), rsreg.getString("tgl_keluar"),
                            rsreg.getString("no_rkm_medis"), rsreg.getString("nm_pasien"),
                            rsResepPulang.getString(3),                   // Jumlah Barang
                            rsResepPulang.getString(1) + " " + rsResepPulang.getString(2),  // Kode dan Nama Barang
                            Valid.SetAngka(rsResepPulang.getDouble(4)),   // Biaya
                            Valid.SetAngka(rsResepPulang.getDouble(5)),   // Modal
                            "0",                                         // Embalase (nilai 0)
                            "0",                                         // Tuslah (nilai 0)
                            Valid.SetAngka(rsResepPulang.getDouble(6)),   // Total
                            rsResepPulang.getString(7),                  // Asal Resep
                            rsResepPulang.getString(8),                  // Kategori
                            rsResepPulang.getString(9),                  // Tanggal Beri
                            rsResepPulang.getString(10)                  // Nama Dokter
                        });
                    }


                    rsResepPulang.close();
                    psResepPulang.close();

                    // Menampilkan total biaya, modal, dan total
                tabMode2.addRow(new String[]{
                    "Total", "", "", "", "", "", 
                    Valid.SetAngka(ttlbiaya), 
                    Valid.SetAngka(ttlmodal), 
                    Valid.SetAngka(ttlembalase), // Total Embalase
                    Valid.SetAngka(ttltuslah), // Total Tuslah
                    Valid.SetAngka(ttltotal), 
                    "", 
                    ""
                });
                }

            } catch (SQLException e) {
                System.out.println("Error: " + e);
            } finally {
                if (rsreg != null) {
                    try {
                        rsreg.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing ResultSet: " + e);
                    }
                }
                if (psreg != null) {
                    try {
                        psreg.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing PreparedStatement: " + e);
                    }
                }
                this.setCursor(Cursor.getDefaultCursor());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
 
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,96));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
}
