package UI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

import UI.PanelCustom.*;
import entity.NhanVien;

/**
 * Khung của giao diện quản trị hệ thống
 * <p>
 * Người tham gia thiết kế: Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 10/10/2021
 * <p>
 * Lần cập nhật cuối: 14/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class fQuanTri extends JFrame implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 2840284348303179625L;
    private static fQuanTri instance;
    private JButton btnBackStaff, btnBackCustomer, btnBackRoomType, btnBackRoom, btnBackServiceType;
    private JButton btnBackService, btnBackBill;

    public static fQuanTri getInstance(NhanVien staffLogin) {
        if (instance == null)
            instance = new fQuanTri(staffLogin);
        return instance;
    }

    private ImageIcon logoApp = CustomUI.LOGO_APP;
    private JTabbedPane tabMain;
    private NhanVien staffLogin = null;
    private PnNhanVien pnlStaff;
    private PnKhachHang pnlCustomer;
    private PnLoaiPhong pnlRoomType;
    private PnPhong pnlRoom;
    private PnLoaiDichVu pnlServiceType;
    private PnDichVu pnlService;
    private PnHoaDon pnlBill;

    /**
     * Khởi tạo giao diện form quản trị
     * 
     * @param staff {@code NhanVien}: nhân viên truy cập
     */
    public fQuanTri(NhanVien staff) {
        setTitle("Quản Lý Hệ Thống");
        setSize(1280, 665);
        setResizable(false);
        setIconImage(logoApp.getImage());
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
        tabMain.setFont(new Font("Dialog", Font.PLAIN, 14));

        pnlStaff = new PnNhanVien(staffLogin);
        pnlCustomer = new PnKhachHang(staffLogin, 0);
        pnlRoomType = new PnLoaiPhong(staffLogin);
        pnlRoom = new PnPhong(staffLogin);
        pnlServiceType = new PnLoaiDichVu(staffLogin);
        pnlService = new PnDichVu(staffLogin);
        pnlBill = new PnHoaDon(staffLogin);

        tabMain.addTab("Nhân viên", null, pnlStaff, "Quản lý Nhân viên");
        tabMain.addTab("Khách hàng", null, pnlCustomer, "Quản lý Khách hàng");
        tabMain.addTab("Loại phòng", null, pnlRoomType, "Quản lý loại phòng");
        tabMain.addTab("Phòng", null, pnlRoom, "Quản lý Phòng");
        tabMain.addTab("Loại dịch vụ", null, pnlServiceType, "Quản lý loại dịch vụ");
        tabMain.addTab("Dịch vụ", null, pnlService, "Quản lý dịch vụ");
        tabMain.addTab("Hóa đơn", null, pnlBill, "Quản lý Hóa đơn");
        this.add(tabMain);

        btnBackStaff = pnlStaff.getBtnBack();
        btnBackCustomer = pnlCustomer.getBtnBack();
        btnBackRoomType = pnlRoomType.getBtnBack();
        btnBackRoom = pnlRoom.getBtnBack();
        btnBackServiceType = pnlServiceType.getBtnBack();
        btnBackService = pnlService.getBtnBack();
        btnBackBill = pnlBill.getBtnBack();

        btnBackStaff.addActionListener(this);
        btnBackCustomer.addActionListener(this);
        btnBackRoomType.addActionListener(this);
        btnBackRoom.addActionListener(this);
        btnBackServiceType.addActionListener(this);
        btnBackService.addActionListener(this);
        btnBackBill.addActionListener(this);
    }

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = new NhanVien("NV00000001");
			new fQuanTri(staff).setVisible(true);
		});
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnBackStaff) || o.equals(btnBackCustomer) || o.equals(btnBackRoomType) || o.equals(btnBackRoom)
                || o.equals(btnBackServiceType) || o.equals(btnBackService) || o.equals(btnBackBill)) {
            EventBackTofDieuHuong();
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