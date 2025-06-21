//VentanaVentas.java
package exament2;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VentanaVentas extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtMonto;
    private JButton btnAdicionar;
    private JButton btnReemplazar;
    private JButton btnIncrementar;
    private JButton btnEliminar;
    private JButton btnListar;
    private JScrollPane scrollPane;
    private JTextArea txtS;
    private RegistroVentas registro;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaVentas frame = new VentanaVentas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VentanaVentas() {
        this.registro = new RegistroVentas();
        setTitle("Registro de Ventas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        
        this.txtMonto = new JTextField();
        this.txtMonto.setFont(new Font("Monospaced", 0, 13));
        this.txtMonto.setBounds(66, 11, 100, 23);
        this.contentPane.add(this.txtMonto);
        this.txtMonto.setColumns(10);
        
        this.btnAdicionar = new JButton("Adicionar Venta");
        this.btnAdicionar.addActionListener(this);
        this.btnAdicionar.setBounds(380, 48, 195, 23);
        this.contentPane.add(this.btnAdicionar);
        
        this.btnReemplazar = new JButton("Reemplazar última <1500");
        this.btnReemplazar.addActionListener(this);
        this.btnReemplazar.setBounds(380, 78, 195, 23);
        this.contentPane.add(this.btnReemplazar);
        
        this.btnIncrementar = new JButton("Incrementar <1800 (+10%)");
        this.btnIncrementar.addActionListener(this);
        this.btnIncrementar.setBounds(380, 108, 195, 23);
        this.contentPane.add(this.btnIncrementar);
        
        this.btnEliminar = new JButton("Eliminar última <1500");
        this.btnEliminar.addActionListener(this);
        this.btnEliminar.setBounds(380, 138, 195, 23);
        this.contentPane.add(this.btnEliminar);
        
        this.btnListar = new JButton("Listar ventas 3000-4000");
        this.btnListar.addActionListener(this);
        this.btnListar.setBounds(380, 168, 195, 23);
        this.contentPane.add(this.btnListar);
        
        this.scrollPane = new JScrollPane();
        this.scrollPane.setBounds(10, 48, 360, 400);
        this.contentPane.add(this.scrollPane);
        
        this.txtS = new JTextArea();
        this.txtS.setFont(new Font("Monospaced", 0, 13));
        this.scrollPane.setViewportView(this.txtS);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnAdicionar)
            actionPerformedBtnAdicionar(e);
        if (e.getSource() == this.btnReemplazar)
            actionPerformedBtnReemplazar(e);
        if (e.getSource() == this.btnIncrementar)
            actionPerformedBtnIncrementar(e);
        if (e.getSource() == this.btnEliminar)
            actionPerformedBtnEliminar(e);
        if (e.getSource() == this.btnListar)
            actionPerformedBtnListar(e);
        limpieza();
    }

    protected void actionPerformedBtnAdicionar(ActionEvent e) {
        try {
            double monto = leerMonto();
            this.registro.adicionar(monto);
            listarVentas();
        } catch (Exception ex) {
            mensaje("Error: Ingrese un monto válido");
        }
    }

    protected void actionPerformedBtnReemplazar(ActionEvent e) {
        if (!this.registro.reemplazarUltimaVentaMenorQue1500()) {
            mensaje("No existe ninguna venta menor que 1500");
        }
        listarVentas();
    }

    protected void actionPerformedBtnIncrementar(ActionEvent e) {
        int incrementos = this.registro.incrementarVentasMenoresQue1800();
        listarVentas();
        mensaje("Ventas incrementadas: " + incrementos);
    }

    protected void actionPerformedBtnEliminar(ActionEvent e) {
        if (!this.registro.eliminarUltimaVentaMenorQue1500()) {
            mensaje("No existe ninguna venta menor que 1500");
        }
        listarVentas();
    }

    protected void actionPerformedBtnListar(ActionEvent e) {
        String lista = this.registro.listarVentasEntre3000y4000();
        if (lista == null) {
            mensaje("No existe venta entre 3000 y 4000 soles");
        } else {
            this.txtS.setText("Ventas entre 3000 y 4000 soles:\n" + lista);
        }
    }

    void limpieza() {
        this.txtMonto.setText("");
        this.txtMonto.requestFocus();
    }

    void listarVentas() {
        this.txtS.setText("");
        for (int i = 0; i < this.registro.tamaño(); i++) {
            imprimir("Venta[" + i + "]: S/ " + this.registro.obtener(i));
        }
    }

    void imprimir(String s) {
        this.txtS.append(s + "\n");
    }

    void mensaje(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    double leerMonto() {
        return Double.parseDouble(this.txtMonto.getText().trim());
    }
}
