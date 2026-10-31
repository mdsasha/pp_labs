import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.util.*;

public class Main extends JFrame {
    private JCheckBox chkHost1, chkHost2, chkHost3;
    private JCheckBox chkGift1, chkGift2, chkGift3;
    private JRadioButton rdoConcertYes, rdoConcertNo;
    private JRadioButton rdoRegularYes, rdoRegularNo;
    private JLabel lblCost, lblLongTime, lblShortTime;
    private JLabel lblLang, lblHost, lblGift, lblConcert, lblRegular;
    private JComboBox<String> cmbLocale;
    private JButton btnSave;
    private Locale currentLocale = new Locale("ru", "RU");
    private Map<String, String> t;
    private double total = 0;
    private static final Map<String, Map<String, String>> TEXTS = new HashMap<>();
    static {
        Map<String, String> ru = new HashMap<>();
        ru.put("title", "вычисление затрат на награждение победителей олимпиады");
        ru.put("lang", "Язык / Language");
        ru.put("host_label", "поздравитель:");
        ru.put("host1", "ведущий 1 (500)");
        ru.put("host2", "ведущий 2 (550)");
        ru.put("host3", "ведущий C (600)");
        ru.put("gift_label", "подарок:");
        ru.put("gift1", "ручка (2)");
        ru.put("gift2", "блокнот (3)");
        ru.put("gift3", "шоколадная медаль (5)");
        ru.put("concert_label", "концерт (1000):");
        ru.put("regular_label", "постоянный клиент (скидка 10%):");
        ru.put("yes", "да");
        ru.put("no", "нет");
        ru.put("total", "итого:");
        ru.put("save", "оформить заказ");
        ru.put("error", "ошибка записи файла");
        TEXTS.put("ru_RU", ru);

        Map<String, String> en = new HashMap<>();
        en.put("title", "Olympiad Winners Award Cost Calculator");
        en.put("lang", "Language / Язык");
        en.put("host_label", "host:");
        en.put("host1", "host 1 (500)");
        en.put("host2", "host 2 (550)");
        en.put("host3", "host 3 (600)");
        en.put("gift_label", "gift:");
        en.put("gift1", "pen (2)");
        en.put("gift2", "notebook (3)");
        en.put("gift3", "chocolate medal (5)");
        en.put("concert_label", "concert(1000):");
        en.put("regular_label", "regular customer (10% discount):");
        en.put("yes", "yes");
        en.put("no", "no");
        en.put("total", "total:");
        en.put("save", "save order");
        en.put("error", "file write error");
        TEXTS.put("en_US", en);

        Map<String, String> be = new HashMap<>();
        be.put("title", "вылічэнне выдаткаў на ўзнагароджанне пераможцаў алімпіяды");
        be.put("lang", "Мова / Language");
        be.put("host_label", "віншавальнік:");
        be.put("host1", "вядучы 1 (500)");
        be.put("host2", "вядучы 2 (550)");
        be.put("host3", "вядучы 3 (600)");
        be.put("gift_label", "падарунак:");
        be.put("gift1", "ручка (2)");
        be.put("gift2", "блакнот (3)");
        be.put("gift3", "шакаладны медаль (5)");
        be.put("concert_label", "канцэрт (1000):");
        be.put("regular_label", "пастаянны кліент (зніжка 10%):");
        be.put("yes", "так");
        be.put("no", "не");
        be.put("total", "разам:");
        be.put("save", "аформіць заказ");
        be.put("error", "памылка запісу файла");
        TEXTS.put("be_BY", be);
    }
    public Main() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 1, 5, 5));
        JPanel p1 = new JPanel();
        lblLang = new JLabel();
        cmbLocale = new JComboBox<>(new String[]{"ru_RU", "en_US", "be_BY"});
        p1.add(lblLang);
        p1.add(cmbLocale);
        add(p1);
        JPanel p2 = new JPanel();
        lblHost = new JLabel();
        chkHost1 = new JCheckBox();
        chkHost2 = new JCheckBox();
        chkHost3 = new JCheckBox();
        p2.add(lblHost);
        p2.add(chkHost1);
        p2.add(chkHost2);
        p2.add(chkHost3);
        add(p2);
        JPanel p3 = new JPanel();
        lblGift = new JLabel();
        chkGift1 = new JCheckBox();
        chkGift2 = new JCheckBox();
        chkGift3 = new JCheckBox();
        p3.add(lblGift);
        p3.add(chkGift1);
        p3.add(chkGift2);
        p3.add(chkGift3);
        add(p3);
        JPanel p4 = new JPanel();
        lblConcert = new JLabel();
        rdoConcertYes = new JRadioButton("", true);
        rdoConcertNo = new JRadioButton("");
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(rdoConcertYes);
        bg1.add(rdoConcertNo);
        p4.add(lblConcert);
        p4.add(rdoConcertYes);
        p4.add(rdoConcertNo);
        add(p4);
        JPanel p5 = new JPanel();
        lblRegular = new JLabel();
        rdoRegularYes = new JRadioButton("", true);
        rdoRegularNo = new JRadioButton("");
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(rdoRegularYes);
        bg2.add(rdoRegularNo);
        p5.add(lblRegular);
        p5.add(rdoRegularYes);
        p5.add(rdoRegularNo);
        add(p5);
        lblCost = new JLabel();
        add(lblCost);
        lblLongTime = new JLabel();
        add(lblLongTime);
        lblShortTime = new JLabel();
        add(lblShortTime);
        btnSave = new JButton();
        add(btnSave);
        ActionListener calc = e -> recalc();
        chkHost1.addActionListener(calc);
        chkHost2.addActionListener(calc);
        chkHost3.addActionListener(calc);
        chkGift1.addActionListener(calc);
        chkGift2.addActionListener(calc);
        chkGift3.addActionListener(calc);
        rdoConcertYes.addActionListener(calc);
        rdoConcertNo.addActionListener(calc);
        rdoRegularYes.addActionListener(calc);
        rdoRegularNo.addActionListener(calc);
        cmbLocale.addActionListener(e -> {
            String sel = (String) cmbLocale.getSelectedItem();
            if ("ru_RU".equals(sel)) currentLocale = new Locale("ru", "RU");
            else if ("en_US".equals(sel)) currentLocale = Locale.US;
            else currentLocale = new Locale("be", "BY");
            applyLocale();
        });
        btnSave.addActionListener(e -> saveOrder());
        applyLocale();
        recalc();
        updateTimeLabels();
        setVisible(true);
    }
    private void applyLocale() {
        String sel = (String) cmbLocale.getSelectedItem();
        t = TEXTS.get(sel);
        setTitle(t.get("title"));
        lblLang.setText(t.get("lang"));
        lblHost.setText(t.get("host_label"));
        lblGift.setText(t.get("gift_label"));
        lblConcert.setText(t.get("concert_label"));
        lblRegular.setText(t.get("regular_label"));
        chkHost1.setText(t.get("host1"));
        chkHost2.setText(t.get("host2"));
        chkHost3.setText(t.get("host3"));
        chkGift1.setText(t.get("gift1"));
        chkGift2.setText(t.get("gift2"));
        chkGift3.setText(t.get("gift3"));
        rdoConcertYes.setText(t.get("yes"));
        rdoConcertNo.setText(t.get("no"));
        rdoRegularYes.setText(t.get("yes"));
        rdoRegularNo.setText(t.get("no"));
        btnSave.setText(t.get("save"));
        recalc();
        updateTimeLabels();
    }
    private void recalc() {
        total = 0;
        if (chkHost1.isSelected()) total += 500;
        if (chkHost2.isSelected()) total += 550;
        if (chkHost3.isSelected()) total += 600;
        if (chkGift1.isSelected()) total += 2;
        if (chkGift2.isSelected()) total += 3;
        if (chkGift3.isSelected()) total += 5;
        if (rdoConcertYes.isSelected()) total += 1000;
        if (rdoRegularYes.isSelected()) total *= 0.9;
        String totalWord = t != null ? t.get("total") : "итого:";
        lblCost.setText(totalWord + " " + String.format("%.2f", total));
    }
    private void updateTimeLabels() {
        Date now = new Date();
        DateFormat lf = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, currentLocale);
        DateFormat sf = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, currentLocale);
        lblLongTime.setText(lf.format(now));
        lblShortTime.setText(sf.format(now));
    }
    private void saveOrder() {
        try (PrintWriter out = new PrintWriter(new FileWriter("order.txt", true))) {
            out.println(t.get("total"));
            out.println(t.get("host_label") + " " + selectedHosts());
            out.println(t.get("gift_label") + " " + selectedGifts());
            out.println(t.get("concert_label") + " " + (rdoConcertYes.isSelected() ? t.get("yes") : t.get("no")));
            out.println(t.get("regular_label") + " " + (rdoRegularYes.isSelected() ? t.get("yes") : t.get("no")));
            out.println(lblCost.getText());
            out.println(lblLongTime.getText());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, t.get("error"));
        }
    }
    private String selectedHosts() {
        StringBuilder sb = new StringBuilder();
        if (chkHost1.isSelected()) sb.append(chkHost1.getText());
        if (chkHost2.isSelected()) sb.append(chkHost2.getText());
        if (chkHost3.isSelected()) sb.append(chkHost3.getText());
        return sb.toString();
    }
    private String selectedGifts() {
        StringBuilder sb = new StringBuilder();
        if (chkGift1.isSelected()) sb.append(chkGift1.getText());
        if (chkGift2.isSelected()) sb.append(chkGift2.getText());
        if (chkGift3.isSelected()) sb.append(chkGift3.getText());
        return sb.toString();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}