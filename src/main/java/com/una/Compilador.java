package com.una;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;
import java.util.HashMap;

/**
 *
 * @author Valeria Fernandez Carvajal
 * @author Randall Alvarez Cheveez
 */

public class Compilador extends javax.swing.JFrame {

    int cont = 0;

    //Expresiones regulares
    String palabras[] = {"suma", "resta", "multiplicacion", "division", "int", "imprimir", "elevar", "raiz2", "raiz3", "fibonacci", "ayuda"}; //conjunto de palabras reservadas
    boolean stopSystem = false; //Se usa en caso de que ocurra un error en la funcion sintax
    HashMap<String, Integer> vars = new HashMap<>();

    public void sintax(String pSentencia, boolean pActionSwitch) {
        boolean activeSwitch = pActionSwitch;
        byte result = -2;

        int value1 = 0;
        int value2 = 0;
        String mensaje = "", text = " ";
        String[] array = new String[3];
        array = pSentencia.split(" ");

        for (byte i = 0; i < this.palabras.length; i++) {//Se recorre buscando la funcion invocada por el usuario
            if (array[0].matches(this.palabras[i])) {
                result = i;
                break;
            }
        }
        if (result >= 0) {
            if (result == 4) { //En caso de que sea una varible
                if (this.sintaxVariable(pSentencia) != 1) { //SI es diferente de 1 sigifica que hubo un error => (duplicidad,no existe,guarda letras)
                    return;
                }
            }//Fin Verificacion Variable
            else if (result == 5) {//Se verifica la funcion IMPRIMIR
                array = pSentencia.split(" ");
                text = this.Error.getText();

                for (int i = 1; i < array.length; i++) { //Se toma el mensaje
                    mensaje = mensaje + " " + array[i];
                }
                this.Error.setText(text + "\n" + mensaje);


                return;

            }//Fin Imprimir


            //Se procede a verificar si son numeros o variables
            //Puede que el array venga de 2 (caso de las funciones de raiz) o de 3
            if (array.length == 2) {
                value1 = getValue(array[1]);
            } else if (array.length == 3) {
                value1 = getValue(array[1]);
                value2 = getValue(array[2]);
            }

            if (value1 == 'n' || value2 == 'n') {//Si alguno de los valores no es valido
                activeSwitch = false;//Se desactiva el switch en caso de que la variable o numero no sea valido
                return;
            } else {
                if (!activeSwitch) {//Se activa el Swicth si la funcion es llamada desde el boton compilar
                    this.Error.setText("Compilacion Exitosa");
                }
            }

            if (activeSwitch) {//Se activa el Swicth, ya que la funcion es llamada por "CompilaryEjecutar"
                fnSwitch(result, value1, value2, mensaje);
            }

        } else {
            this.Error.setText("Error de Compilacion");
            this.stopSystem = true;
        }
    }

    public int getValue(String pVariable) {
        boolean numeric = pVariable.matches("\\d*");
        if (numeric) {
            return Integer.parseInt(pVariable);

        } else {
            if (this.vars.containsKey(pVariable)) {
                return this.vars.get(pVariable);
            } else {
                this.Error.setText("Errror!!! La variable: " + pVariable + " no existe.");
                this.stopSystem = true;
            }
        }
        return 'n';
    }

    public int fibo(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            //Hago la suma
            return fibo(n - 1) + fibo(n - 2);
        }

    }

    public void ayuda() {
        String text = " ";

        this.Error.setText("\t\tINFORMACIÓN DEL SISTEMA\n\n");

        for (byte i = 0; i < this.palabras.length; i++) {
            text = this.Error.getText();
            switch (i) {
                case 0:
                    this.Error.setText(text +"\n"+i+ "=> SUMA X1 X2;");
                    break;
                case 1:
                    this.Error.setText(text +"\n"+i+ "=> RESTA X1 X2;");
                    break;
                case 2:
                    this.Error.setText(text +"\n"+i+ "=> MULTIPLICACION X1 X2;");
                    break;
                case 3:
                    this.Error.setText(text+"\n"+i+ "=> DIVISION X1 X2;");
                    break;
                case 4:
                    this.Error.setText(text +"\n"+i+ "=> Declaracion Variable: INT NOMBRE X1;");
                    break;
                case 5:
                    this.Error.setText(text +"\n"+i+ "=> IMPRIMIR TEXTO;");
                    break;
                case 6:
                    this.Error.setText(text +"\n"+i+ "=> ELEVAR X1 X2;");
                    break;
                case 7:
                    this.Error.setText(text +"\n"+i+ "=> Raiz Cuadrada: RAIZ2 X1;");
                    break;
                case 8:
                    this.Error.setText(text +"\n"+i+ "=> Raiz Cubica: RAIZ3 X1;");
                    break;
                case 9:
                    this.Error.setText(text +"\n"+i+ "=> FIBONACCI X1;");
                    break;
                case 10:
                    this.Error.setText(text +"\n"+i+ "=> Informacion del Sistema:AYUDA;");
                    break;
                default:
                    break;
            }
            //String palabras[] = {"ELEVAR", "RAIZ2", "RAIZ3", "FIBONACCI","?"}; //conjunto de palabras reservadas

        }

    }

    public void fnSwitch(byte pPoss, int value1, int value2, String pMensaje) {
        String text = this.Error.getText();
        int resultado = 0;
        switch (pPoss) {
            case 0://Suma
                resultado = value1 + value2;
                this.Error.setText(text + "\n" + "El resultado de la suma es: " + resultado);

                break;
            case 1://Resta
                resultado = value1 - value2;
                this.Error.setText(text + "\n" + "El resultado de la resta es: " + resultado);
                break;

            case 2://Multiplicacion
                resultado = value1 * value2;
                this.Error.setText(text + "\n" + "El resultado de la multiplicacion es: " + resultado);
                break;
            case 3://Division
                resultado = value1 / value2;
                this.Error.setText(text + "\n" + "El resultado de la division es: " + resultado);
                break;
            case 5://Imprimir
                this.Error.setText(text + "\n" + pMensaje);
                break;
            case 6: //Potencia
                resultado = 1;
                System.out.println(value1);
                System.out.println(value2);
                while (value2 != 0) {
                    resultado = resultado * value1;
                    value2--;
                }
                this.Error.setText(text + "\n" + "El resultado de la potencia es: " + resultado);
                break;
            case 7: //Raiz cuadrada
                resultado = (int) Math.sqrt(value1);
                this.Error.setText(text + "\n" + "El resultado de la raiz cuadrada es: " + resultado);
                break;
            case 8: //Raiz cubica
                float val1 = value1;
                double res2;
                res2 = Math.pow(val1, (double) 1 / 3);
                this.Error.setText(text + "\n" + "El resultado de la raiz cubica es: " + res2);
                break;
            case 9: //Fibonacci
                resultado = fibo(value1);
                this.Error.setText(text + "\n" + "Fibonacci de " + value1 + " es: " + resultado);
                break;
            case 10: //Ayuda
                this.ayuda();
                break;

        }

    }

    public byte sintaxVariable(String pSentencia) {
        String array[]; //= new String[3];
        boolean insert = false;
        boolean numeric = false;
        array = pSentencia.split(" ");
        numeric = array[2].matches("\\d*");

        //Vertifica que el nombre de la variable no se encuentre en el arreglo de palabras reservadas
        for (byte j = 0; j < this.palabras.length; j++) {
            insert = this.palabras[j].equals(array[1]);//True == Encuentra coincidencias
            if (insert) {//Si encuentra coincidencias se debe de salir
                this.Error.setText("Errror!!! El nombre: " + array[1] + " es una palabra reservada.");
                this.stopSystem = true;
                return 0;// No Permitido
            }
        }
        if (this.vars.containsKey(array[1])) {
            this.Error.setText("Errror!!! La variable: " + array[1] + " ya se encuentra declarada.");
            this.stopSystem = true;
            return 2;//Ya se encuentra registrada
        } else {
            if (numeric) {
                //Inserta la variable con el valor
                this.vars.put(array[1], Integer.parseInt(array[2]));
                return 1;
            } else {//No ingresa ya que la varibale contiene letras
                this.stopSystem = true;
                this.Error.setText("Errror!!! La Variable: " + array[1] + " solo admite numeros.");
                return 3;
            }
        }

    }

    public Compilador() {
        initComponents();
        setLayout(new BorderLayout());

        // Configura las propiedades de la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setVisible(true);
        add(jPanel1, BorderLayout.CENTER);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        background = new JPanel();
        jScrollPane2 = new JScrollPane();
        leftLineEditor = new JPanel();
        textEditor = new JEditorPane();
        editorNumberLine = new JEditorPane();
        jScrollPane5 = new JScrollPane();
        Error = new JEditorPane();
        Compilar = new JButton();
        Limpiar = new JButton();
        compejecutar = new JButton();
        errorLineLeft = new JEditorPane();
        // Crear el panel superior
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, 20)); // 20 píxeles de altura
        topPanel.setBackground(new Color(0x39, 0x41, 0x48));

        // Crear un JLabel para el texto
        titleLabel = new JLabel("Val Compiler");
        titleLabel.setForeground(new Color(0x59, 0x59, 0x59));
        titleLabel.setFont(new Font("Tahoma", 3, 16));


        textEditor.setFont(new Font("Arial", 1, 12)); // NOI18N
        textEditor.setForeground(Color.WHITE);
        textEditor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtATexto1KeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                txtATexto1KeyReleased(evt);
            }
        });

        editorNumberLine.setEditable(false);
        editorNumberLine.setFont(new Font("Arial", 1, 12)); // NOI18N
        editorNumberLine.setForeground(new Color(0xA6, 0x5E, 0x44));
        editorNumberLine.setText("1");
        editorNumberLine.setOpaque(false);

        GroupLayout jPanel3Layout = new GroupLayout(leftLineEditor);
        background.setBackground(new Color(0x80, 0x80, 0x80));
        leftLineEditor.setBackground(new Color(0x39, 0x41, 0x48));
        editorNumberLine.setBackground(new Color(0x39, 0x41, 0x48));
        errorLineLeft.setBackground(new Color(0x39, 0x41, 0x48));
        editorNumberLine.setBackground(new Color(0x39, 0x41, 0x48));
        Error.setBackground(new Color(0x39, 0x41, 0x48));
        jScrollPane2.setBackground(new Color(0x39, 0x41, 0x48));
        jScrollPane5.setBackground(new Color(0x39, 0x41, 0x48));
        textEditor.setBackground(new Color(0x39, 0x41, 0x48));




        leftLineEditor.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(editorNumberLine, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textEditor, GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE))
        );

        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(textEditor, GroupLayout.PREFERRED_SIZE, 3000, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(editorNumberLine, GroupLayout.PREFERRED_SIZE, 3000, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(leftLineEditor);

        Error.setEditable(false);
        Error.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        Error.setFont(new Font("Arial", 1, 12)); // NOI18N
        Error.setForeground(Color.black);
        jScrollPane5.setViewportView(Error);

        Compilar.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        Compilar.setText("Compilar");
        Compilar.setBackground(new Color(0x39, 0x41, 0x48));
        Compilar.setForeground(Color.WHITE);
        Compilar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CompilarActionPerformed(evt);
            }
        });

        Limpiar.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        Limpiar.setText("Limpiar");
        Limpiar.setBackground(new Color(0x39, 0x41, 0x48));
        Limpiar.setForeground(Color.WHITE);
        Limpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                LimpiarActionPerformed(evt);
            }
        });


        compejecutar.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        compejecutar.setText("Compilar y Ejecutar");
        compejecutar.setBackground(new Color(0x39, 0x41, 0x48));
        compejecutar.setForeground(Color.WHITE);
        compejecutar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                compejecutarActionPerformed(evt);
            }
        });

        errorLineLeft.setEditable(false);
        errorLineLeft.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        errorLineLeft.setForeground(Color.red);
        errorLineLeft.setToolTipText("");
        errorLineLeft.setOpaque(false);

        // Configurar el layout del topPanel para centrar el texto
        GroupLayout topPanelLayout = new GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setAutoCreateGaps(true); // Crear automáticamente espacios
        topPanelLayout.setAutoCreateContainerGaps(true); // Crear automáticamente espacios entre el contenedor y los componentes

        topPanelLayout.setHorizontalGroup(
                topPanelLayout.createSequentialGroup()
                        .addContainerGap(60, GroupLayout.PREFERRED_SIZE) // Espacio en los extremos
                        .addComponent(titleLabel) // Agregar el JLabel
                        .addContainerGap(0, GroupLayout.PREFERRED_SIZE) // Espacio en los extremos

        );

        topPanelLayout.setVerticalGroup(
                topPanelLayout.createSequentialGroup()
                        .addContainerGap(74, Short.MAX_VALUE) // Agregar márgenes
                        .addComponent(titleLabel) // Agregar el JLabel
                        .addContainerGap(74, Short.MAX_VALUE) // Agregar márgenes
        );

// Configuración del diseño del panel principal
        GroupLayout jPanel2Layout = new GroupLayout(background);
        background.setLayout(jPanel2Layout);

        // Configuración horizontal
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(topPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Agregar topPanel
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(errorLineLeft, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                                        .addComponent(jScrollPane5, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(Compilar)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Limpiar))
                                        .addComponent(compejecutar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(46, 46, 46))
        );

        // Configuración vertical
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(topPanel) // Agregar topPanel
                                .addGap(55, 55, 55)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(errorLineLeft)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(Compilar)
                                                        .addComponent(Limpiar))
                                                .addGap(18, 18, 18)
                                                .addComponent(compejecutar)))
                                .addContainerGap(74, Short.MAX_VALUE))
        );



        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(background, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(background, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    private void txtATexto1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtATexto1KeyPressed
        StringTokenizer st = new StringTokenizer(textEditor.getText(), "\n", true);
        String txt = "", token;
        errorLineLeft.setText("");
        Error.setText("");
        cont = 1;

        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if ("\n".equals(token)) {
                cont++;
            }
        }

        for (int i = 1; i <= cont; i++) {
            txt += i + "\n";
        }
        editorNumberLine.setText(txt);
    }//GEN-LAST:event_txtATexto1KeyPressed

    private void txtATexto1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtATexto1KeyReleased
        StringTokenizer st = new StringTokenizer(textEditor.getText(), "\n", true);
        String txt = "", token;
        cont = 1;

        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if ("\n".equals(token)) {
                cont++;
            }
        }

        for (int i = 1; i <= cont; i++) {
            txt += i + "\n";
        }
        editorNumberLine.setText(txt);
    }//GEN-LAST:event_txtATexto1KeyReleased

    private void CompilarActionPerformed(java.awt.event.ActionEvent evt) {
        String texto = this.textEditor.getText();
        this.Error.setText(" ");
        vars.clear();
        this.stopSystem = false;
        StringTokenizer tokens = new StringTokenizer(texto, ";\n\r");
        String sentencia = "";

        while (tokens.hasMoreTokens()) {

            sentencia = tokens.nextToken();
            if (!stopSystem) {
                sintax(sentencia, false);
            } else {
                return;
            }

        }
    }

    private void LimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        textEditor.setText("");
        errorLineLeft.setText("");
        Error.setText("");
    }

    private void compejecutarActionPerformed(java.awt.event.ActionEvent evt) {
        this.Error.setText(" ");
        this.stopSystem = false;
        vars.clear();
        String texto = this.textEditor.getText();
        StringTokenizer tokens = new StringTokenizer(texto, ";\n\r");
        String sentencia = "";
        while (tokens.hasMoreTokens()) {
            sentencia = tokens.nextToken();
            if (!stopSystem) {
                sintax(sentencia, true);
            } else {
                return;
            }
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
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Compilador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton Compilar;
    private javax.swing.JEditorPane Error;
    private javax.swing.JButton Limpiar;
    private javax.swing.JEditorPane errorLineLeft;
    private javax.swing.JEditorPane editorNumberLine;
    private javax.swing.JButton compejecutar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel topPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel background;
    private javax.swing.JPanel leftLineEditor;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JEditorPane textEditor;


    // End of variables declaration//GEN-END:variables



}
