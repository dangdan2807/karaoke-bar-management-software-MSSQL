package UI;

import javax.swing.*;
import javax.swing.event.*;

import DAO.KhachHangDAO;

import java.awt.*;
import java.awt.event.*;

import UI.PanelCustom.*;
import entity.KhachHang;
import entity.NhanVien;

/**
 * khung giao diện quản lý đặt phòng
 * <p>
 * Người tham gia thiết kế: Võ Minh Hiếu
 * <p>
 * Ngày tạo: 11/10/2021
 * <p>
 * Lần cập nhật cuối: 18/12/2021
 * <p>
 * Nội dung cập nhật: Sửa lỗi thêm dịch vụ
 */
public class fQuanLyDatPhong extends JFrame implements ActionListener, ChangeListener {
    /**
     * 
     */
    private static final long serialVersionUID = 2840284348303179625L;
    private static fQuanLyDatPhong instance;
    private MyButton btnBackCustomer, btnBackBill, btnBackRentRoom, btnPnChooseCustomer, btnPnDatPhong;

    public static fQuanLyDatPhong getInstance(NhanVien staffLogin) {
        if (instance == null)
            instance = new fQuanLyDatPhong(staffLogin);
        return instance;
    }

    private ImageIcon logoApp = new ImageIcon(fQuanLyDatPhong.class.getResource(CustomUI.LOGO_APP));
    private JTabbedPane tabMain;
    private NhanVien staffLogin = null;

    private PnDatPhong pnlRentRoom;
    private PnKhachHang pnlCustomer;
    private PnHoaDon pnlBill;

    /**
     * Constructor form quản trị
     * 
     * @param staff {@code NhanVien}: nhân viên truy cập
     */
    public fQuanLyDatPhong(NhanVien staff) {
        setTitle("Quản Lý Đặt Phòng");
        setSize(1280, 700);
        setIconImage(logoApp.getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.staffLogin = staff;
        createTabControl();
        setCloseAction(this);
    }

    /**
     * Khởi tạo giao diện và Tab menu
     */
    public void createTabControl() {
        tabMain = new JTabbedPane();
        tabMain.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        tabMain.setBorder(null);
        tabMain.setFont(new Font("Dialog", Font.PLAIN, 15));

        pnlRentRoom = new PnDatPhong(staffLogin);
        pnlCustomer = new PnKhachHang(staffLogin, 1);
        pnlBill = new PnHoaDon(staffLogin);

        tabMain.addTab("Quản lý đặt phòng", null, pnlRentRoom, "Quản lý Nhân viên");
        tabMain.addTab("Quản lý khách hàng", null, pnlCustomer, "Quản lý Khách hàng");
        tabMain.addTab("Quản lý Hóa đơn", null, null, "Quản lý Hóa đơn");
        this.add(tabMain);

        btnBackRentRoom = pnlRentRoom.getBtnBack();
        btnBackCustomer = pnlCustomer.getBtnBack();
        btnBackBill = pnlBill.getBtnBack();
        btnPnChooseCustomer = pnlCustomer.getBtnChooseCustomer();
        btnPnDatPhong = pnlRentRoom.getBtnChooseCustomer();

        tabMain.addChangeListener(this);
        btnBackRentRoom.addActionListener(this);
        btnBackCustomer.addActionListener(this);
        btnBackBill.addActionListener(this);
        btnPnChooseCustomer.addActionListener(this);
        btnPnDatPhong.addActionListener(this);

        tabMain.addChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnBackCustomer) || o.equals(btnBackBill) || o.equals(btnBackRentRoom)) {
            EventBackTofDieuHuong();
        } else if (o.equals(btnPnDatPhong)) {
            String roomId = pnlRentRoom.getSelectedRoomId();
            if (!roomId.equalsIgnoreCase("") || roomId.length() > 0) {
                tabMain.setSelectedIndex(1);
            } else {
                String message = "Bạn cần chọn phòng trước";
                JOptionPane.showConfirmDialog(pnlRentRoom, message, "Thông báo chọn phòng", JOptionPane.OK_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (o.equals(btnPnChooseCustomer)) {
            String selectedCustomerId = pnlCustomer.getSelectedCustomerId();
            KhachHang selectedCustomer = new KhachHang();
            KhachHangDAO customerDAO = KhachHangDAO.getInstance();
            selectedCustomer = customerDAO.getCustomerById(selectedCustomerId);
            if (selectedCustomer == null)
                selectedCustomer = new KhachHang();
            pnlRentRoom.setSelectedCustomer(selectedCustomer);
            tabMain.setSelectedIndex(0);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int tabSelectedIndex = tabMain.getSelectedIndex();
        switch (tabSelectedIndex) {
            case 2:
                pnlBill = new PnHoaDon(staffLogin);
                tabMain.setComponentAt(2, pnlBill);
                btnBackBill = pnlBill.getBtnBack();
                btnBackBill.addActionListener(this);
                break;
        }
    }

    /**
     * Bắt sự kiện khi click btn close(x), sẽ show 1 form xác nhận đăng xuất hay
     * thoát chương trình
     * 
     * @param jframe {@code JFrame} sẽ nhận sự kiện
     */
    public void setCloseAction(JFrame jframe) {
        jframe.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                fDieuHuong f = new fDieuHuong(staffLogin);
                jframe.setVisible(false);
                f.setVisible(true);
            }
        });
    }

    /**
     * Đóng form hiện tại và mở form điều hướng
     */
    public void EventBackTofDieuHuong() {
        fDieuHuong f = new fDieuHuong(staffLogin);
        setVisible(false);
        f.setVisible(true);
    }
}
