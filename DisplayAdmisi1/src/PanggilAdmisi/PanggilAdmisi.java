package PanggilAdmisi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;
import koneksi.Koneksi;
/**
 *
 * @author SERVER
 */
public class PanggilAdmisi extends javax.swing.JFrame {
    
    
    private Connection koneksi;
    private Timer loketTimer;

    public PanggilAdmisi() {
        initComponents();
        koneksi = Koneksi.getKoneksi();
        cekDanHapusDataTidakValid();
        jTextField1.setText("1");
        
    }

     private void cekDanHapusDataTidakValid() {
        try {
            // Ambil tanggal hari ini
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String todayDate = sdf.format(new Date());

            // Koneksi ke database
            koneksi = Koneksi.getKoneksi();

            // Query untuk menghapus data dengan postdate tidak sama dengan hari ini
            String sql = "DELETE FROM antrian_loket WHERE postdate <> ?";
            PreparedStatement pstmt = koneksi.prepareStatement(sql);
            pstmt.setString(1, todayDate);
            int rowsDeleted = pstmt.executeUpdate();

            System.out.println(rowsDeleted + " baris data dihapus.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menghapus data tidak valid.");
        }
    }

    private void playSound(String soundFileName) throws Exception {
        File soundFile = new File(soundFileName);
            if (!soundFile.exists()) {
                throw new FileNotFoundException("File suara tidak ditemukan: " + soundFileName);
            }

            // Load and play the audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        }

//    private String getSoundForNumbers(String nomor) {
//        try {
//            String soundFileName = "";
//            String baseFileName = "VOICE/";
//
//            // Ambil angka pertama, kedua, dan ketiga
//            String angkaPertama = nomor.substring(0, 1);
//            String angkaKedua = nomor.length() > 1 ? nomor.substring(1, 2) : "";
//            String angkaKetiga = nomor.length() > 2 ? nomor.substring(2) : "";
//
//            // Tentukan suara nomor antrian
//            if (nomor.equals("10")) {
//                soundFileName = baseFileName + "10.wav";
//            } else if (nomor.equals("11")) {
//                soundFileName = baseFileName + "11.wav";
//            } else if (nomor.equals("100")) {
//                soundFileName = baseFileName + "100.wav";
//            } else if (nomor.equals("12") || nomor.equals("13") || nomor.equals("14") ||
//                       nomor.equals("15") || nomor.equals("16") || nomor.equals("17") ||
//                       nomor.equals("18") || nomor.equals("19")) {
//                soundFileName = combineSounds(baseFileName + angkaKedua + ".wav", baseFileName + "belas.wav");
//            } else if (nomor.equals("20") || nomor.equals("30") || nomor.equals("40") ||
//                       nomor.equals("50") || nomor.equals("60") || nomor.equals("70") ||
//                       nomor.equals("80") || nomor.equals("90")) {
//                soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "puluh.wav");
//            } else if (nomor.equals("200") || nomor.equals("300") || nomor.equals("400") ||
//                       nomor.equals("500") || nomor.equals("600") || nomor.equals("700") ||
//                       nomor.equals("800") || nomor.equals("900")) {
//                soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "ratus.wav");
//            } else if (nomor.length() == 2) {
//                soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "puluh.wav");
//                if (!angkaKedua.equals("0")) {
//                    soundFileName = combineSounds(soundFileName, baseFileName + angkaKedua + ".wav");
//                }
//            } else if (nomor.length() == 3) {
//                if (angkaPertama.equals("1")) {
//                    if (angkaKedua.equals("0")) {
//                        soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + angkaKetiga + ".wav");
//                    } else if (angkaKedua.equals("1")) {
//                        soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + angkaKetiga + ".wav", baseFileName + "belas.wav");
//                    } else {
//                        soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + angkaKedua + ".wav", baseFileName + "puluh.wav");
//                        if (!angkaKetiga.equals("0")) {
//                            soundFileName = combineSounds(soundFileName, baseFileName + angkaKetiga + ".wav");
//                        }
//                    }
//                } else {
//                    soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "ratus.wav");
//                    if (angkaKedua.equals("1")) {
//                        soundFileName = combineSounds(soundFileName, baseFileName + angkaKetiga + ".wav", baseFileName + "belas.wav");
//                    } else if (!angkaKedua.equals("0")) {
//                        soundFileName = combineSounds(soundFileName, baseFileName + angkaKedua + ".wav", baseFileName + "puluh.wav");
//                        if (!angkaKetiga.equals("0")) {
//                            soundFileName = combineSounds(soundFileName, baseFileName + angkaKetiga + ".wav");
//                        }
//                    }
//                }
//            } else {
//                soundFileName = baseFileName + nomor + ".wav";
//            }
//
//            return soundFileName;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return "";
//        }
//    }

private String getSoundForNumbers(String nomor) {
    try {
        String soundFileName = "";
        String baseFileName = "VOICE/";

        // Ambil angka pertama, kedua, dan ketiga
        String angkaPertama = nomor.substring(0, 1);
        String angkaKedua = nomor.length() > 1 ? nomor.substring(1, 2) : "";
        String angkaKetiga = nomor.length() > 2 ? nomor.substring(2) : "";

        if (nomor.equals("10")) {
            // Angka sepuluh
            soundFileName = baseFileName + "10.wav";
        } else if (nomor.equals("11")) {
            // Angka sebelas
            soundFileName = baseFileName + "11.wav";
        } else if (nomor.equals("100")) {
            // Angka seratus
            soundFileName = baseFileName + "100.wav";
        
        }else if (nomor.equals("12") || nomor.equals("13") || nomor.equals("14")
                || nomor.equals("15") || nomor.equals("16") || nomor.equals("17")
                || nomor.equals("18") || nomor.equals("19")) {
            // Untuk angka 12-19, gunakan suara angka kedua + belas.wav
            soundFileName = combineSounds(baseFileName + angkaKedua + ".wav", baseFileName + "belas.wav");
        } else if (nomor.equals("20") || nomor.equals("30") || nomor.equals("40")
                || nomor.equals("50") || nomor.equals("60") || nomor.equals("70")
                || nomor.equals("80") || nomor.equals("90")) {
            // Untuk angka 20, 30, 40, dst., gunakan suara angka pertama + puluh.wav
            soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "puluh.wav");
        } else if (nomor.equals("200") || nomor.equals("300") || nomor.equals("400")
                || nomor.equals("500") || nomor.equals("600") || nomor.equals("700")
                || nomor.equals("800") || nomor.equals("900")) {
            // Untuk angka 200, 300, 400, dst., gunakan suara angka pertama + ratus.wav
            soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "ratus.wav");
        } else if (nomor.equals("210") || nomor.equals("310") || nomor.equals("410")
                || nomor.equals("510") || nomor.equals("610") || nomor.equals("710")
                || nomor.equals("810") || nomor.equals("910")) {
            // Untuk angka 200, 300, 400, dst., gunakan suara angka pertama + ratus.wav
            soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "ratus.wav", baseFileName + "10.wav");
        } else if (nomor.equals("211") || nomor.equals("311") || nomor.equals("411")
                || nomor.equals("511") || nomor.equals("611") || nomor.equals("711")
                || nomor.equals("811") || nomor.equals("911")) {
            // Untuk angka 200, 300, 400, dst., gunakan suara angka pertama + ratus.wav
            soundFileName = combineSounds(baseFileName + angkaPertama + ".wav",  baseFileName + "ratus.wav", baseFileName + "11.wav");
        } else if (nomor.equals("110")) {
            // Angka seratus sepuluh
            soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + "10.wav");
        } else if (nomor.equals("111")) {
            // Angka seratus sebelas
            soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + "11.wav");       
        } else if (nomor.length() == 2) {
            // Untuk angka lainnya di atas 20, tambahkan suara angka pertama + puluh.wav + angka kedua jika bukan 0
            soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "puluh.wav");
            if (!angkaKedua.equals("0")) {
                soundFileName = combineSounds(soundFileName, baseFileName + angkaKedua + ".wav");
            }
        } else if (nomor.length() == 3 && angkaPertama.equals("1")) {
            // Jika angka pertama adalah 1
            if (angkaKedua.equals("0")) {
                // Jika angka kedua adalah 0, baca angka ketiga saja
                soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + angkaKetiga + ".wav");
            } else if (angkaKedua.equals("1")) {
                // Jika angka kedua adalah 1, baca angka ketiga + belas.wav
                soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + angkaKetiga + ".wav", baseFileName + "belas.wav");
            } else if (angkaKetiga.equals("0")) {
                // Jika angka kedua adalah 1, baca angka ketiga + belas.wav
                soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + angkaKedua + ".wav", baseFileName + "puluh.wav");
            } else {
                // Jika angka kedua selain 0 dan 1, baca angka pertama + ratus.wav, angka kedua + puluh.wav, angka ketiga.wav
                soundFileName = combineSounds(baseFileName + "100.wav", baseFileName + angkaKedua + ".wav", baseFileName + "puluh.wav", baseFileName + angkaKetiga + ".wav");
            }
        } else if (nomor.length() == 3 && !angkaPertama.equals("1")) {
            // Jika angka pertama bukan 1 dan panjang angka adalah 3
            if (angkaKedua.equals("0")) {
                // Jika angka kedua dan ketiga adalah 0, baca hanya angka pertama + ratus.wav
                soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "ratus.wav", baseFileName + angkaKetiga + ".wav");
            } else if (angkaKedua.equals("1")) {
                // Jika angka kedua adalah 1, baca angka ketiga + belas.wav
                soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "ratus.wav", baseFileName + angkaKetiga + ".wav", baseFileName + "belas.wav");
            } else if (angkaKetiga.equals("0")) {
                // Jika angka kedua adalah 1, baca angka ketiga + belas.wav
                soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "ratus.wav", baseFileName + angkaKedua + ".wav", baseFileName + "puluh.wav");
            } else {
                // Jika angka kedua selain 0 dan 1, baca angka pertama + ratus.wav, angka kedua + puluh.wav, angka ketiga.wav
                soundFileName = combineSounds(baseFileName + angkaPertama + ".wav", baseFileName + "ratus.wav", baseFileName + angkaKedua + ".wav", baseFileName + "puluh.wav", baseFileName + angkaKetiga + ".wav");
            }
        } else {
            // Untuk angka satuan (0-9)
            soundFileName = baseFileName + nomor + ".wav";
        }

        return soundFileName;
    } catch (Exception ex) {
        ex.printStackTrace();
        return "";
    }
}





     
    private String combineSounds(String... soundFiles) {
    try {
        // Buat objek untuk menyimpan file audio sementara
        List<File> files = new ArrayList<>();
        for (String soundFile : soundFiles) {
            File file = new File(soundFile);
            if (!file.exists()) {
                System.out.println("File suara tidak ditemukan: " + soundFile);
                return "";
            }
            files.add(file);
        }

        // Gabungkan file audio sementara
        List<AudioInputStream> audioStreams = new ArrayList<>();
        for (File file : files) {
            audioStreams.add(AudioSystem.getAudioInputStream(file));
        }

        AudioInputStream appendedFiles = new AudioInputStream(
            new SequenceInputStream(Collections.enumeration(audioStreams)),
            audioStreams.get(0).getFormat(),
            audioStreams.stream().mapToLong(AudioInputStream::getFrameLength).sum()
        );

        // Simpan hasil gabungan ke file sementara
        File tempFile = File.createTempFile("temp", ".wav");
        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, tempFile);

        return tempFile.getAbsolutePath(); // Kembalikan path dari file hasil gabungan
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return "";
}

    
    private void playSoundForNumber(String nomor) {
        try {
            String soundFileName = getSoundForNumbers(nomor);

            // Check if the file exists
            File soundFile = new File(soundFileName);
            if (!soundFile.exists()) {
                System.out.println("File suara tidak ditemukan: " + soundFileName);
                return; // Exit the method if file is not found
            }

            // Load and play the audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     
    private void playSoundBasedOnLoket() {
        String loket = (String) jComboBox1.getSelectedItem();
        String soundFileName = "VOICE/loket.wav";

        switch (loket) {
            case "LOKET 1":
                playSequentialSounds(soundFileName, "VOICE/1.wav");
                break;
            case "LOKET 2":
                playSequentialSounds(soundFileName, "VOICE/2.wav");
                break;
            case "LOKET 3":
                playSequentialSounds(soundFileName, "VOICE/3.wav");
                break;
            // Add more cases for other loket options if needed
            default:
                // Default action
                break;
        }
    }

     
//    private void delayPlaySound(String nomor) {
//        int delay = 2000; // Default delay untuk satuan
//
//        if (nomor.length() == 2) {
//            delay = 3000; // Delay untuk puluhan
//        } else if (nomor.length() == 3) {
//            delay = 3100; // Delay untuk ratusan
//        }
//
//        Timer timer = new Timer(delay, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                playSoundBasedOnLoket(); // Panggil metode untuk memainkan suara berdasarkan loket
//            }
//        });
//        timer.setRepeats(false); // Hanya jalan sekali setelah delay
//        timer.start();
//    }
    
    private void delayPlaySound(String nomor) {
        // Delay masing-masing suara
        int delayNoAntri = 1500;  // Delay untuk suara noantri.wav (1 detik)
        int delayNomor = 1500;     // Delay untuk suara nomor (2 detik)
        int delayLoket = 2200;     // Delay tambahan untuk suara loket
        int delayAkhir = 1700;     // Delay untuk suara akhir.wav (1 detik)

        String baseFileName = "VOICE/";
        String awalSound = baseFileName + "awal.wav";
        String noAntriSound = baseFileName + "noantri.wav";
        String akhirSound = baseFileName + "akhir.wav";

        // Pertama, langsung mainkan suara awal.wav tanpa delay
        try {
            playSound(awalSound);  // Tidak ada delay untuk awal.wav
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Setelah suara awal, mainkan noantri.wav dengan delay 1000ms
        Timer timerNoAntri = new Timer(delayNoAntri, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    playSound(noAntriSound);  // Mainkan noantri.wav setelah 1000ms
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        timerNoAntri.setRepeats(false);
        timerNoAntri.start();

        // Setelah suara noantri diputar, mainkan nomor dengan delay 2000ms setelah noantri
        Timer timerNomor = new Timer(delayNoAntri + delayNomor, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String soundFileName = getSoundForNumbers(nomor);  // Dapatkan suara nomor
                    playSound(soundFileName);  // Mainkan nomor dengan delay
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        timerNomor.setRepeats(false);
        timerNomor.start();

        // Setelah nomor diputar, mainkan suara berdasarkan loket dengan delay tambahan
        Timer timerLoket = new Timer(delayNoAntri + delayNomor + delayLoket, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundBasedOnLoket(); // Mainkan suara berdasarkan loket
            }
        });
        timerLoket.setRepeats(false);
        timerLoket.start();

        // Setelah loket diputar, mainkan akhir.wav dengan delay tambahan
        Timer timerAkhir = new Timer(delayNoAntri + delayNomor + delayLoket + delayAkhir, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    playSound(akhirSound);  // Mainkan akhir.wav setelah delay
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        timerAkhir.setRepeats(false);
        timerAkhir.start();
    }







    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        BLanjut = new javax.swing.JButton();
        BDisplay = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 255));

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NOMOR      :");

        jTextField1.setForeground(new java.awt.Color(51, 51, 51));
        jTextField1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jComboBox1.setForeground(new java.awt.Color(51, 51, 51));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LOKET 1", "LOKET 2", "LOKET 3" }));

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("LOKET        :");

        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("PANGGIL");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("ULANGI");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BLanjut.setForeground(new java.awt.Color(51, 51, 51));
        BLanjut.setText("LANJUT");
        BLanjut.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BLanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BLanjutActionPerformed(evt);
            }
        });

        BDisplay.setForeground(new java.awt.Color(51, 51, 51));
        BDisplay.setText("DISPLAY");
        BDisplay.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BDisplay.setName("BDisplay"); // NOI18N
        BDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDisplayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BLanjut, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BLanjut, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void BDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDisplayActionPerformed
       DisplayAdmisi displayAdmisi = new DisplayAdmisi();
       displayAdmisi.setVisible(true);
    }//GEN-LAST:event_BDisplayActionPerformed

    private void BLanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BLanjutActionPerformed
       
        // Ambil nilai saat ini dari jTextField1
        String currentValue = jTextField1.getText();
        String nomor = jTextField1.getText();
        simpanEndTime(nomor);
        
        // Konversi nilai menjadi integer
        int currentNumber = Integer.parseInt(currentValue);
        
        // Tambahkan 1 ke nilai saat ini
        currentNumber++;
        
        // Ubah kembali ke string dan set ke jTextField1
        jTextField1.setText(String.valueOf(currentNumber));
    }//GEN-LAST:event_BLanjutActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nomor = jTextField1.getText();
        String loket = jComboBox1.getSelectedItem().toString();

        // Panggil method untuk memainkan suara berdasarkan nomor
        
//        playSoundForNumber(nomor);
        delayPlaySound(nomor);
       

        // Buka jendela display atau lakukan tindakan lain yang diperlukan
        DisplayAdmisi displayAdmisi = DisplayAdmisi.getInstance();
        displayAdmisi.refreshNomor(nomor);
        displayAdmisi.setNomor(nomor);
        displayAdmisi.setLoket(loket);
        displayAdmisi.setupAntrianData();
        displayAdmisi.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void playSequentialSounds(String soundFile1, String soundFile2) {
        try {
            AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(new File(soundFile1));
            AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(new File(soundFile2));

            AudioInputStream appendedFiles = new AudioInputStream(
                    new SequenceInputStream(audioStream1, audioStream2),
                    audioStream1.getFormat(),
                    audioStream1.getFrameLength() + audioStream2.getFrameLength());

            Clip clip = AudioSystem.getClip();
            clip.open(appendedFiles);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PanggilAdmisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanggilAdmisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanggilAdmisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanggilAdmisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanggilAdmisi().setVisible(true);
            }
        });
    }
    
    private void updateLoket(String nomor, String loket) {
        PanggilAdmisi panggilAdmisi = new PanggilAdmisi();
        panggilAdmisi.setVisible(true);

        try {
            // Your application logic here
            
        } finally {
            // Tutup koneksi di akhir aplikasi
            if (panggilAdmisi.koneksi != null) {
                Koneksi.closeKoneksi(panggilAdmisi.koneksi);
            }
        }
    }
    
    private void simpanLoketKeDatabase(String nomor, String loket) {
        String sql = "UPDATE antrian_loket SET loket = ? WHERE noantrian = ?";

        try (PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setString(1, loket);
            stmt.setString(2, nomor);
            int rowsAffected = stmt.executeUpdate();

//            if (rowsAffected > 0) {
//                JOptionPane.showMessageDialog(this, "Data loket berhasil diperbarui");
//            } else {
//                JOptionPane.showMessageDialog(this, "Gagal memperbarui data loket", "Error", JOptionPane.ERROR_MESSAGE);
//            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
   private void simpanstatus(String loket) {
        String sql = "UPDATE antrian_loket SET status = 1 WHERE noantrian = ?";

        try (PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setString(1, loket);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Status berhasil diperbarui untuk loket: " + loket);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui status untuk loket: " + loket, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
   
  private void simpanEndTime(String nomor) {
    String sql = "UPDATE antrian_loket SET end_time = CURRENT_TIME() WHERE noantrian = ?";

    try (PreparedStatement stmt = koneksi.prepareStatement(sql)) {
        stmt.setString(1, nomor);
        int rowsAffected = stmt.executeUpdate();

//        if (rowsAffected > 0) {
//            JOptionPane.showMessageDialog(this, "Waktu selesai berhasil disimpan untuk nomor antrian: " + nomor);
//        } else {
//            JOptionPane.showMessageDialog(this, "Gagal menyimpan waktu selesai untuk nomor antrian: " + nomor, "Error", JOptionPane.ERROR_MESSAGE);
//        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BDisplay;
    private javax.swing.JButton BLanjut;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
