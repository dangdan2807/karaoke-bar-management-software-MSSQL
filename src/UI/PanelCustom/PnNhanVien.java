package UI.PanelCustom;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.*;

import DAO.ConvertTime;
import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import UI.fDieuHuong;
import entity.NhanVien;
import entity.TaiKhoan;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PnNhanVien extends JFrame
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	private JTable tableStaff;
	private DefaultTableModel modelTableStaff;
	private JTextField txtCMND, txtPhoneNumber, txtSalary, txtStaffName, txtStaffID, boxFieldGender;
	private JTextField boxFieldSearch, txtKeyWord, boxFieldSearchPosition, boxFieldPosition, txtUsername;
	private JComboBox<String> cboGender, cboSearch, cboSearchPosition, cboPosition;
	private JLabel lbCMND, lbBirthDay, lbGender, lbPosition, lbSalary, lbPhoneNumber, lbStaffID;
	private JLabel lbStaffName, lbStatus, lpSearch;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch;
	private JRadioButton radWorking, radRetired;
	private kDatePicker dpBirthDay;

	private ImageIcon bg = new ImageIcon(
			new ImageIcon("img/bgBlue.jpg").getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon addIcon = new ImageIcon("img/blueAdd_16.png");
	private ImageIcon refreshIcon = new ImageIcon("img/refresh_16.png");
	private ImageIcon searchIcon = new ImageIcon("img/search_16.png");
	private ImageIcon backIcon = new ImageIcon("img/back_16.png");
	private ImageIcon updateIcon = new ImageIcon("img/update_16.png");
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	int index = 1;
	NhanVien staffLogin = null;

	/**
	 * Constructor form quản lý nhân viên
	 * 
	 * @param staff <code>NhanVien</code>: nhân viên truy cậpư
	 */
	public PnNhanVien(NhanVien staff) {
		this.staffLogin = staff;
		setLayout(null);
		setSize(1270, 630);
		setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				Image bgMain = bg.getImage();
				g2.drawImage(bgMain, 0, 0, null);
				setFont(new Font("Tahoma", Font.BOLD, 24));
				g2.setColor(Color.decode("#9B17EB"));
				g2.drawRoundRect(10, 50, 1238, 530, 20, 20);
				g2.drawRoundRect(9, 49, 1240, 530, 20, 20);
			}
		};
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 1270, 630);
		this.add(pnMain);

		JPanel pnTitle = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255, 255, 255));
			}
		};

		pnTitle.setBounds(0, 0, 1270, 50);
		pnTitle.setOpaque(false);
		pnTitle.setLayout(null);
		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 33, 19, 12, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		pnTitle.add(btnBack);
		pnMain.add(pnTitle);

		JLabel lbTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
		lbTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 1250, 45);
		pnTitle.add(lbTitle);

		JPanel pnInfo = new JPanel();
		pnInfo.setLayout(null);
		pnInfo.setOpaque(false);
		pnInfo.setBounds(10, 50, 1238, 182);
		pnMain.add(pnInfo);

		dpBirthDay = new kDatePicker(250, 20);
		dpBirthDay.setBackgroundColor(new Color(255, 255, 255, 50));
		dpBirthDay.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dpBirthDay.setForegroundCustom(Color.white);
		dpBirthDay.setOpaqueCustom(false);
		dpBirthDay.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnInfo.add(dpBirthDay);
		dpBirthDay.setBounds(965, 54, 250, 20);

		txtCMND = new JTextField();
		txtCMND.setForeground(Color.WHITE);
		txtCMND.setBounds(965, 29, 250, 20);
		txtCMND.setCaretColor(Color.WHITE);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		pnInfo.add(txtCMND);

		lbCMND = new JLabel("CMND/CCCD:");
		lbCMND.setForeground(Color.WHITE);
		lbCMND.setBounds(845, 29, 105, 20);
		pnInfo.add(lbCMND);

		lbBirthDay = new JLabel("Ngày sinh:");
		lbBirthDay.setForeground(Color.WHITE);
		lbBirthDay.setBounds(845, 54, 105, 20);
		pnInfo.add(lbBirthDay);

		lbGender = new JLabel("Giới tính:");
		lbGender.setForeground(Color.WHITE);
		lbGender.setBounds(845, 79, 105, 20);
		pnInfo.add(lbGender);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setForeground(Color.WHITE);
		txtPhoneNumber.setBounds(555, 79, 250, 20);
		txtPhoneNumber.setCaretColor(Color.WHITE);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		pnInfo.add(txtPhoneNumber);

		txtSalary = new JTextField("0");
		txtSalary.setForeground(Color.WHITE);
		txtSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSalary.setBounds(555, 54, 250, 20);
		txtSalary.setCaretColor(Color.WHITE);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtSalary);
		pnInfo.add(txtSalary);

		cboGender = new JComboBox<String>();
		cboGender.addItem("Nam");
		cboGender.addItem("Nữ");
		cboGender.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboGender.setOpaque(false);
		cboGender.setEditable(true);
		cboGender.setUI(new BasicComboBoxUI());
		boxFieldGender = (JTextField) cboGender.getEditor().getEditorComponent();
		boxFieldGender.setBorder(BorderFactory.createEmptyBorder());
		boxFieldGender.setBackground(new Color(246, 210, 255, 50));
		boxFieldGender.setForeground(Color.WHITE);
		boxFieldGender.setEditable(false);
		cboGender.setBounds(965, 79, 250, 20);
		cboGender.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnInfo.add(cboGender);

		lbPosition = new JLabel("Chức vụ:");
		lbPosition.setForeground(Color.WHITE);
		lbPosition.setBounds(435, 29, 115, 20);
		pnInfo.add(lbPosition);

		lbSalary = new JLabel("Mức lương:");
		lbSalary.setForeground(Color.WHITE);
		lbSalary.setBounds(435, 54, 115, 16);
		pnInfo.add(lbSalary);

		lbPhoneNumber = new JLabel("Số điện thoại:");
		lbPhoneNumber.setForeground(Color.WHITE);
		lbPhoneNumber.setBounds(435, 79, 115, 16);
		pnInfo.add(lbPhoneNumber);

		txtStaffName = new JTextField();
		txtStaffName.setForeground(Color.WHITE);
		txtStaffName.setBounds(145, 54, 250, 20);
		txtStaffName.setCaretColor(Color.WHITE);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtStaffName);
		pnInfo.add(txtStaffName);

		txtStaffID = new JTextField();
		txtStaffID.setForeground(Color.WHITE);
		txtStaffID.setEditable(false);
		txtStaffID.setBounds(145, 28, 250, 20);
		txtStaffID.setCaretColor(Color.WHITE);
		CustomUI.getInstance().setCustomTextFieldOff(txtStaffID);
		pnInfo.add(txtStaffID);

		lbStaffID = new JLabel("Mã nhân viên: ");
		lbStaffID.setForeground(Color.WHITE);
		lbStaffID.setBackground(new Color(249, 249, 249));
		lbStaffID.setBounds(20, 29, 120, 20);
		pnInfo.add(lbStaffID);

		lbStaffName = new JLabel("Tên nhân viên:");
		lbStaffName.setForeground(Color.WHITE);
		lbStaffName.setBounds(20, 54, 120, 20);
		pnInfo.add(lbStaffName);

		lbStatus = new JLabel("Trạng thái:");
		lbStatus.setForeground(Color.WHITE);
		lbStatus.setBounds(20, 79, 120, 20);
		pnInfo.add(lbStatus);

		radWorking = new JRadioButton("Đang làm ");
		radWorking.setForeground(Color.WHITE);
		radWorking.setFocusable(false);
		radWorking.setOpaque(false);
		radWorking.setBackground(Color.WHITE);
		radWorking.setBounds(155, 78, 103, 21);
		radWorking.setSelected(true);

		radRetired = new JRadioButton("Đã nghỉ");
		radRetired.setForeground(Color.WHITE);
		radRetired.setFocusable(false);
		radRetired.setOpaque(false);
		radRetired.setBackground(Color.WHITE);
		radRetired.setBounds(276, 78, 103, 21);

		ButtonGroup groupStatus = new ButtonGroup();
		groupStatus.add(radWorking);
		groupStatus.add(radRetired);
		pnInfo.add(radWorking);
		pnInfo.add(radRetired);

		cboPosition = new JComboBox<String>();
		cboPosition.addItem("Nhân viên");
		cboPosition.addItem("Chủ quán");
		cboPosition.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboPosition.setOpaque(false);
		cboPosition.setEditable(true);
		cboPosition.setUI(new BasicComboBoxUI());
		boxFieldPosition = (JTextField) cboPosition.getEditor().getEditorComponent();
		boxFieldPosition.setBorder(BorderFactory.createEmptyBorder());
		boxFieldPosition.setBackground(new Color(246, 210, 255, 50));
		boxFieldPosition.setForeground(Color.WHITE);
		boxFieldPosition.setEditable(false);
		cboPosition.setBounds(555, 28, 250, 20);
		cboPosition.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnInfo.add(cboPosition);

		JPanel pnSearch = new JPanel();
		pnSearch.setBounds(10, 135, 1218, 41);
		pnInfo.add(pnSearch);
		pnSearch.setOpaque(false);
		pnSearch.setLayout(null);
		pnInfo.add(pnSearch);

		lpSearch = new JLabel("Tìm kiếm theo:");
		lpSearch.setForeground(Color.WHITE);
		lpSearch.setBounds(285, 10, 100, 20);
		pnSearch.add(lpSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên nhân viên");
		cboSearch.addItem("Số diện thoại");
		cboSearch.addItem("Chức vụ");
		cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboSearch.setOpaque(false);
		cboSearch.setEditable(true);
		cboSearch.setUI(new BasicComboBoxUI());
		boxFieldSearch = (JTextField) cboSearch.getEditor().getEditorComponent();
		boxFieldSearch.setBorder(BorderFactory.createEmptyBorder());
		boxFieldSearch.setBackground(new Color(246, 210, 255, 50));
		boxFieldSearch.setForeground(Color.WHITE);
		boxFieldSearch.setEditable(false);
		cboSearch.setBounds(385, 11, 200, 20);
		cboSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnSearch.add(cboSearch);

		btnSearch = new MyButton(110, 35, "Tìm kiếm", gra, searchIcon.getImage(), 35, 19, 14, 5);
		btnSearch.setBounds(956, 5, 110, 35);
		pnSearch.add(btnSearch);

		JLabel lpKeyWord = new JLabel("Từ khóa:");
		lpKeyWord.setForeground(Color.WHITE);
		lpKeyWord.setBounds(641, 10, 73, 20);
		pnSearch.add(lpKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setForeground(Color.WHITE);
		txtKeyWord.setBounds(707, 10, 190, 20);
		txtKeyWord.setCaretColor(Color.WHITE);
		txtKeyWord.setEditable(false);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		pnSearch.add(txtKeyWord);

		cboSearchPosition = new JComboBox<String>();
		cboSearchPosition.addItem("Nhân viên");
		cboSearchPosition.addItem("Chủ quán");
		cboSearchPosition.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboSearchPosition.setOpaque(false);
		cboSearchPosition.setEditable(true);
		cboSearchPosition.setUI(new BasicComboBoxUI());
		boxFieldSearchPosition = (JTextField) cboSearchPosition.getEditor().getEditorComponent();
		boxFieldSearchPosition.setBorder(BorderFactory.createEmptyBorder());
		boxFieldSearchPosition.setBackground(new Color(246, 210, 255, 50));
		boxFieldSearchPosition.setForeground(Color.WHITE);
		boxFieldSearchPosition.setEditable(false);
		cboSearchPosition.setVisible(false);
		cboSearchPosition.setBounds(707, 11, 190, 20);
		cboSearchPosition.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnSearch.add(cboSearchPosition);

		btnRefresh = new MyButton(129, 35, "Làm mới form", gra, refreshIcon.getImage(), 31, 19, 10, 5);
		btnRefresh.setBounds(1076, 5, 129, 35);
		btnRefresh.setBackground(Color.CYAN);
		pnSearch.add(btnRefresh);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, updateIcon.getImage(), 45, 19, 24, 6);
		btnUpdate.setBounds(136, 5, 100, 35);
		btnUpdate.setEnabledCustom(false);
		pnSearch.add(btnUpdate);

		btnAdd = new MyButton(100, 35, "Thêm", gra, addIcon.getImage(), 40, 19, 19, 6);
		btnAdd.setBounds(10, 5, 100, 35);
		pnSearch.add(btnAdd);

		JLabel lbUsername = new JLabel("Tài khoản:");
		lbUsername.setForeground(Color.WHITE);
		lbUsername.setBounds(20, 104, 120, 20);
		pnInfo.add(lbUsername);

		txtUsername = new JTextField();
		txtUsername.setForeground(Color.WHITE);
		txtUsername.setCaretColor(Color.WHITE);
		txtUsername.setBounds(145, 104, 250, 20);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtUsername);
		pnInfo.add(txtUsername);

		JPanel pnTable = new JPanel();
		pnTable.setBackground(Color.WHITE);
		pnTable.setLayout(null);
		pnTable.setBounds(8, 234, 1240, 351);
		pnTable.setOpaque(false);
		String[] cols = { "STT", "Mã nhân viên", "Tên nhân viên", "CMND/CCCD", "Chức vụ", "SDT", "Ngày sinh",
				"Mức lương", "Giới tính", "Trạng thái", "Tài khoản" };
		modelTableStaff = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		tableStaff = new JTable(modelTableStaff);
		tableStaff.setBackground(new Color(255, 255, 255, 0));
		tableStaff.setForeground(new Color(255, 255, 255));
		tableStaff.setRowHeight(21);
		tableStaff.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
		tableStaff.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tableStaff.getTableHeader().setBackground(new Color(255, 255, 255));

		tableStaff.getTableHeader().setBackground(new Color(255, 255, 255));
		JScrollPane scpTable = new JScrollPane(tableStaff, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpTable.getViewport().setBackground(Color.WHITE);
		scpTable.setBounds(10, 10, 1220, 330);
		scpTable.setOpaque(false);
		scpTable.getViewport().setOpaque(false);

		pnTable.add(scpTable);
		pnMain.add(pnTable);

		btnSearch.addActionListener(this);
		btnBack.addActionListener(this);
		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnRefresh.addActionListener(this);

		btnSearch.addMouseListener(this);
		tableStaff.addMouseListener(this);
		txtStaffName.addMouseListener(this);
		txtCMND.addMouseListener(this);
		txtPhoneNumber.addMouseListener(this);
		txtSalary.addMouseListener(this);
		boxFieldGender.addMouseListener(this);
		boxFieldSearch.addMouseListener(this);
		boxFieldSearchPosition.addMouseListener(this);
		boxFieldPosition.addMouseListener(this);
		cboGender.addMouseListener(this);
		cboPosition.addMouseListener(this);
		cboSearch.addMouseListener(this);
		cboSearchPosition.addMouseListener(this);
		dpBirthDay.addMouseListener(this);
		dpBirthDay.getTextFieldCustom().addMouseListener(this);

		txtStaffName.addFocusListener(this);
		txtKeyWord.addFocusListener(this);
		txtPhoneNumber.addFocusListener(this);
		txtSalary.addFocusListener(this);
		txtCMND.addFocusListener(this);
		cboGender.addFocusListener(this);
		cboPosition.addFocusListener(this);
		cboSearch.addFocusListener(this);
		cboSearchPosition.addFocusListener(this);
		dpBirthDay.addFocusListener(this);
		txtUsername.addFocusListener(this);

		cboSearch.addItemListener(this);
		cboSearchPosition.addItemListener(this);

		txtKeyWord.addKeyListener(this);

		allLoaded();
	}

	public static void main(String[] args) {
		NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
		new PnNhanVien(staff).setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			txtStaffID.setText("");
			txtStaffName.setText("");
			txtCMND.setText("");
			txtPhoneNumber.setText("");
			txtSalary.setText("");
			cboGender.setSelectedIndex(0);
			cboPosition.setSelectedIndex(0);
			dpBirthDay.setValueToDay();
			radWorking.setSelected(true);
			txtUsername.setText("");
			txtUsername.setEditable(true);
			CustomUI.getInstance().setCustomTextFieldOn(txtUsername);
			btnUpdate.setEnabledCustom(false);
			btnAdd.setEnabledCustom(true);
		} else if (o.equals(btnAdd)) {
			String message = "";
			if (validData()) {
				NhanVien staff = getStaffDataSelectInForm();
				Boolean result = NhanVienDAO.getInstance().insertStaff(staff);
				if (result) {
					message = "Thêm nhân viên mới thành công";
					txtStaffID.setText(staff.getMaNhanVien());
					txtUsername.setEditable(false);
					btnAdd.setEnabledCustom(false);
					btnUpdate.setEnabledCustom(true);
					int stt = tableStaff.getRowCount();
					addRow(stt, staff);
					int lastIndex = tableStaff.getRowCount() - 1;
					tableStaff.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
					tableStaff.scrollRectToVisible(tableStaff.getCellRect(lastIndex, lastIndex, true));
				} else {
					message = "Thêm nhân viên thất bại";
				}
				JOptionPane.showMessageDialog(this, message);
			}
		} else if (o.equals(btnBack)) {
			fDieuHuong f = new fDieuHuong(staffLogin);
			this.setVisible(false);
			f.setVisible(true);
		} else if (o.equals(btnUpdate)) {
			if (validData()) {
				NhanVien staff = getStaffDataSelectInForm();
				String staffName = NhanVienDAO.getInstance().getStaffNameById(staff.getMaNhanVien());
				String message = "Xác nhận cập nhật thông tin nhân viên " + staffName;
				int choose = JOptionPane.showConfirmDialog(this, message, "Xác nhận cập nhật",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (choose == JOptionPane.OK_OPTION) {
					Boolean result = NhanVienDAO.getInstance().updateInfoStaff(staff);
					if (result) {
						message = "Cập nhật thông tin nhân viên thành công";
						txtUsername.setEditable(false);
						btnAdd.setEnabledCustom(false);
						btnUpdate.setEnabledCustom(true);
						int selectedRow = tableStaff.getSelectedRow();
						updateRow(selectedRow, staff);
					} else {
						message = "Cập nhật thông tin nhân viên thất bại";
					}
					JOptionPane.showMessageDialog(this, message);
				}
			}
		} else if (o.equals(btnSearch)) {
			searchEventUsingBtnSearch();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
			ArrayList<NhanVien> staffList = null;
			String searchTypeName = cboSearch.getSelectedItem().toString();
			if (searchTypeName.equalsIgnoreCase("Chức vụ")) {
				String searchTypePosition = cboSearchPosition.getSelectedItem().toString();
				staffList = NhanVienDAO.getInstance().getStaffListByPosition(searchTypePosition);
				cboSearchPosition.setVisible(true);
				txtKeyWord.setVisible(false);
				loadStaffList(staffList);
			} else {
				if (searchTypeName.equalsIgnoreCase("Tất cả")) {
					txtKeyWord.setText("");
					txtKeyWord.setEditable(false);
					CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
					staffList = NhanVienDAO.getInstance().getStaffList();
					loadStaffList(staffList);
				} else {
					txtKeyWord.setEditable(true);
					CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
				}
				cboSearchPosition.setVisible(false);
				txtKeyWord.setVisible(true);
			}
		} else if (o.equals(cboSearchPosition)) {
			String searchTypePosition = cboSearchPosition.getSelectedItem().toString();
			ArrayList<NhanVien> staffList = NhanVienDAO.getInstance().getStaffListByPosition(searchTypePosition);
			loadStaffList(staffList);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(boxFieldGender)) {
			cboGender.showPopup();
		} else if (o.equals(boxFieldSearch)) {
			cboSearch.showPopup();
		} else if (o.equals(boxFieldSearchPosition)) {
			cboSearchPosition.showPopup();
		} else if (o.equals(boxFieldPosition)) {
			cboPosition.showPopup();
		} else if (o.equals(tableStaff)) {
			int selectedRow = tableStaff.getSelectedRow();
			txtStaffID.setText(tableStaff.getValueAt(selectedRow, 1).toString().trim());
			txtStaffName.setText(tableStaff.getValueAt(selectedRow, 2).toString().trim());
			txtCMND.setText(tableStaff.getValueAt(selectedRow, 3).toString().trim());
			String positionStr = tableStaff.getValueAt(selectedRow, 4).toString().trim();
			cboPosition.setSelectedIndex(0);
			if (positionStr.equalsIgnoreCase("Chủ quán")) {
				cboPosition.setSelectedIndex(1);
			}
			txtPhoneNumber.setText(tableStaff.getValueAt(selectedRow, 5).toString().trim());
			dpBirthDay.setValue(tableStaff.getValueAt(selectedRow, 6).toString().trim());
			txtSalary.setText(tableStaff.getValueAt(selectedRow, 7).toString().trim());
			String genderStr = tableStaff.getValueAt(selectedRow, 8).toString().trim();
			cboGender.setSelectedIndex(0);
			if (genderStr.equalsIgnoreCase("Nữ")) {
				cboGender.setSelectedIndex(1);
			}
			radWorking.setSelected(true);
			String statusStr = tableStaff.getValueAt(selectedRow, 9).toString().trim();
			if (statusStr.equalsIgnoreCase("Đã nghỉ")) {
				radRetired.setSelected(true);
			}
			txtUsername.setText(tableStaff.getValueAt(selectedRow, 10).toString().trim());
			CustomUI.getInstance().setCustomTextFieldOff(txtUsername);
			txtUsername.setEditable(false);
			btnAdd.setEnabledCustom(false);
			btnUpdate.setEnabledCustom(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(boxFieldGender)) {
			cboGender.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(boxFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(boxFieldSearchPosition)) {
			cboSearchPosition.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(boxFieldPosition)) {
			cboPosition.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(dpBirthDay.getTextFieldCustom())) {
			dpBirthDay.getTextFieldCustom().setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(boxFieldGender)) {
			cboGender.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(boxFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(boxFieldSearchPosition)) {
			cboSearchPosition.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(boxFieldPosition)) {
			cboPosition.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(dpBirthDay.getTextFieldCustom())) {
			dpBirthDay.getTextFieldCustom().setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyWord)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchEventUsingBtnSearch();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtStaffName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtStaffName);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		} else if (o.equals(txtPhoneNumber)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtPhoneNumber);
		} else if (o.equals(txtSalary)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtSalary);
		} else if (o.equals(txtCMND)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtCMND);
		} else if (o.equals(txtUsername)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtUsername);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtStaffName)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtStaffName);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		} else if (o.equals(txtPhoneNumber)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		} else if (o.equals(txtSalary)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtSalary);
			String salaryStr = txtSalary.getText().trim();
			if (!salaryStr.equals("")) {
				String message = "";
				DecimalFormat df = new DecimalFormat("#,###.##");
				Double salary = 0.0;
				try {
					salary = Double.parseDouble(salaryStr);
					if (salary < 0) {
						message = "Mức lương phải lớn hơn hoặc bằng 0";
						showMessage(txtSalary, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					message = "Mức lương phải là một số";
					showMessage(txtSalary, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
				txtSalary.setText(df.format(salary));
			}
		} else if (o.equals(txtCMND)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		} else if (o.equals(txtUsername)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtUsername);
		}
	}

	private void allLoaded() {
		reSizeColumnTable();
		loadStaffList(NhanVienDAO.getInstance().getStaffList());
	}

	/**
	 * Hiển thị popup thông báo của 1 JTextField
	 * 
	 * @param txt     JTextField: được trỏ đến khi cần thông báo
	 * @param type    int: mã dạng thông báo (Nếu 1. là lỗi)
	 * @param message String: Tin nhắn được hiển thị
	 * @param title   String: Tiêu đề thông báo
	 * @param option  int: loại thông báo (icon)
	 */
	private void showMessage(JTextField txt, int type, String message, String title, int option) {
		if (type == 1) {
			txt.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
		}
		JOptionPane.showMessageDialog(this, message, title, option);
	}

	/**
	 * Hiển thị popup thông báo
	 * 
	 * @param message <code>String</code>: Tin nhắn được hiển thị
	 * @param title   <code>String</code>: Tiêu đề thông báo
	 * @param option  <code>int</code>: loại thông báo (icon)
	 */
	private void showMessage(String message, String title, int option) {
		JOptionPane.showMessageDialog(this, message, title, option);
	}

	/**
	 * Kiểm tra thông tin trong form
	 * 
	 * @return <code>boolean</code>: true nếu hợp lê, false nếu không hợp lệ
	 */
	private boolean validData() {
		String message = "";
		String staffID = txtStaffID.getText().trim();

		String staffName = txtStaffName.getText().trim();
		if (staffName.length() > 100 || staffName.length() <= 0 || staffName.equals("")) {
			if (staffName.length() > 100)
				message = "Tên phải bé hơn 100 ký tự";
			else
				message = "Tên không được rỗng";
			showMessage(txtStaffName, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		String cmnd = txtCMND.getText().trim();
		if (!cmnd.matches("^[\\d]{9}$|^[\\d]{12}$")) {
			message = "CMND phải là số và gồm 9 số hoặc nếu là CCCD phải là số và gồm 12 số";
			showMessage(txtCMND, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		String salaryStr = txtSalary.getText().trim().replace(",", "");
		if (salaryStr.length() > 0) {
			try {
				int num = Integer.parseInt(salaryStr);
				if (num < 0) {
					message = "Mức lương phải lớn hơn hoặc bằng 0";
					showMessage(txtSalary, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (Exception e) {
				message = "Mức lương phải là một số";
				showMessage(txtSalary, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			message = "Mức lương không được rỗng";
			showMessage(txtSalary, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		String phoneNumber = txtPhoneNumber.getText().trim();
		if (!((phoneNumber.length() > 0 || phoneNumber.length() < 10) && phoneNumber.matches("^0[35789][\\d]{8}$"))) {
			message = "số điện thoại phải là 10 số và bắt đầu bằng 03, 05, 07, 08, 09";
			showMessage(txtPhoneNumber, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Date birthDay = dpBirthDay.getValueSqlDate();
		Date today = dpBirthDay.getValueToDay();
		long difference = today.getTime() - birthDay.getTime();
		int staffAge = ((int) TimeUnit.MILLISECONDS.toDays(difference)) / 365;
		if (birthDay.after(today)) {
			message = "Ngày sinh phải trước ngày hiện tại và nhân viên phải đủ 16 tuổi";
			showMessage(message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (staffAge < 16) {
			message = "Nhân viên phải đủ 16 tuổi";
			showMessage(message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (staffID.equalsIgnoreCase("")) {
			String username = txtUsername.getText().trim();
			if ((username.equalsIgnoreCase("") || username.length() <= 0 || username.length() >= 100
					|| username.length() < 6) && !username.matches("^[a-zA-Z@!#$_]{6,100}$")) {
				if (username.equalsIgnoreCase("") || username.length() < 0) {
					message = "tài khoản không được rỗng";
				} else if (username.length() > 100) {
					message = "tài khoản không được quá 100 ký tự, số và @, !, #, $, _";
				} else if (username.length() < 6)
					message = "tài khoản tối thiểu có 6 ký tự, số và @, !, #, $, _";
				else if (!username.matches("^[a-zA-Z@!#$_]{6,100}$"))
					message = "tài khoản chỉ gồm 6-100 ký tự, số và @, !, #, $, _";
				showMessage(txtUsername, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			TaiKhoan account = TaiKhoanDAO.getInstance().getAccountByUsername(username);
			if (account != null) {
				message = "Tài khoản đã tồn tại, vui lòng nhập tài khoản khác";
				showMessage(txtUsername, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * Tự động tại mã nhân viên mới tăng theo thứ tụ tăng dần
	 * 
	 * @return <code>String</code>: mã nhân viên mới
	 */
	private String createNewStaffID() {
		String lastStaffId = NhanVienDAO.getInstance().getLastStaffID();
		String idStr = "NV";
		int oldNumberStaffID = 0;
		if (lastStaffId.equalsIgnoreCase("") == false || lastStaffId != null) {
			oldNumberStaffID = Integer.parseInt(lastStaffId.replace(idStr, ""));
		}

		int newStaffID = ++oldNumberStaffID;
		String newStaffIdStr = idStr + newStaffID;
		boolean flag = true;
		while (flag) {
			newStaffIdStr = newStaffIdStr.replace(idStr, idStr + "0");
			if (newStaffIdStr.length() > 9) {
				flag = false;
			}
		}
		return newStaffIdStr;
	}

	/**
	 * chuyển đổi thông tin trong form thành đối tượng NhanVien
	 * 
	 * @return <code>NhanVien</code>: nhân viên
	 */
	private NhanVien getStaffDataSelectInForm() {
		String staffID = txtStaffID.getText().trim();
		String staffName = txtStaffName.getText().trim();
		String status = radRetired.isSelected() ? "Đã nghỉ" : "Đang làm";
		String position = cboPosition.getSelectedItem().toString().trim();
		Double salary = Double.parseDouble(txtSalary.getText().trim().replace(",", ""));
		String phoneNumber = txtPhoneNumber.getText().trim();
		String cmnd = txtCMND.getText().trim();
		Date birthDay = dpBirthDay.getValueSqlDate();
		String gioiTinhStr = cboGender.getSelectedItem().toString().trim();
		boolean gioiTinh = gioiTinhStr.equalsIgnoreCase("nam") ? false : true;
		String username = txtUsername.getText().trim();
		TaiKhoan account = new TaiKhoan(username);
		if (!staffID.equals("")) {
			account = TaiKhoanDAO.getInstance().getAccountByUsername(username);
			if (account == null) {
				account = new TaiKhoan(username);
			}
		} else
			staffID = createNewStaffID();
		return new NhanVien(staffID, cmnd, staffName, birthDay, phoneNumber, position, salary, gioiTinh, status,
				account);
	}

	/**
	 * Thêm khoảng trắng vào trước và sao chuỗi được truyền vào
	 * 
	 * @param str <code>String</code>: chuỗi cần xử lý
	 * @return <code>String</code>: chuỗi đã xử lý
	 */
	private String addSpaceToString(String str) {
		return " " + str + " ";
	}

	/**
	 * Thêm dòng vào danh sách nhân viên đang hiển thị
	 * 
	 * @param stt   số thứ tự mới
	 * @param staff <code>NhanVien</code>: nhân viên cần được thêm
	 */
	private void addRow(int stt, NhanVien staff) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		String sttStr = df.format(stt);
		String mucLuongStr = df.format(staff.getMucLuong());
		boolean gioiTinh = staff.getGioiTinh();
		String gioiTinhStr = gioiTinh ? "Nữ" : "Nam";
		String format = "dd-MM-yyyy";
		String birthDayStr = ConvertTime.getInstance().convertSqlDateToUtilDateFormatString(staff.getNgaySinh(),
				format);
		modelTableStaff.addRow(new Object[] { sttStr, addSpaceToString(staff.getMaNhanVien()),
				addSpaceToString(staff.getHoTen()), addSpaceToString(staff.getCmnd()),
				addSpaceToString(staff.getChucVu()), addSpaceToString(staff.getSoDienThoai()),
				addSpaceToString(birthDayStr), addSpaceToString(mucLuongStr), addSpaceToString(gioiTinhStr),
				addSpaceToString(staff.getTrangThaiNV()), addSpaceToString(staff.getTaiKhoan().getTenDangNhap()) });
		modelTableStaff.fireTableDataChanged();
	}

	/**
	 * Cập nhật thông tin một dòng khi biết dòng mà thông tin nhân viên
	 * 
	 * @param selectedRow <code>Int</code>: dòng được chọn
	 * @param staff       <code>NhanVien</code: nhân viên cần cập nhật
	 */
	private void updateRow(int selectedRow, NhanVien staff) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		String mucLuongStr = df.format(staff.getMucLuong());
		boolean gioiTinh = staff.getGioiTinh();
		String gioiTinhStr = gioiTinh ? "Nữ" : "Nam";
		String format = "dd-MM-yyyy";
		String birthDayStr = ConvertTime.getInstance().convertSqlDateToUtilDateFormatString(staff.getNgaySinh(),
				format);
		modelTableStaff.setValueAt(addSpaceToString(staff.getHoTen()), selectedRow, 2);
		modelTableStaff.setValueAt(addSpaceToString(staff.getCmnd()), selectedRow, 3);
		modelTableStaff.setValueAt(addSpaceToString(staff.getChucVu()), selectedRow, 4);
		modelTableStaff.setValueAt(addSpaceToString(staff.getSoDienThoai()), selectedRow, 5);
		modelTableStaff.setValueAt(addSpaceToString(birthDayStr), selectedRow, 6);
		modelTableStaff.setValueAt(addSpaceToString(mucLuongStr), selectedRow, 7);
		modelTableStaff.setValueAt(addSpaceToString(gioiTinhStr), selectedRow, 8);
		modelTableStaff.setValueAt(addSpaceToString(staff.getTrangThaiNV()), selectedRow, 9);
		modelTableStaff.fireTableDataChanged();
	}

	/**
	 * Hiển thị danh sách nhân viên
	 * 
	 * @param staffList <code>ArrayList<NhanVien></code>: danh sách nhân viên
	 */
	private void loadStaffList(ArrayList<NhanVien> staffList) {
		modelTableStaff.getDataVector().removeAllElements();
		modelTableStaff.fireTableDataChanged();
		int i = 1;
		for (NhanVien item : staffList) {
			addRow(i++, item);
		}
	}

	/**
	 * Thay đổi kích thước cột
	 */
	private void reSizeColumnTable() {
		tableStaff.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableStaff.getColumnModel().getColumn(0).setPreferredWidth(40);
		tableStaff.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableStaff.getColumnModel().getColumn(2).setPreferredWidth(200);
		tableStaff.getColumnModel().getColumn(3).setPreferredWidth(90);
		tableStaff.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableStaff.getColumnModel().getColumn(5).setPreferredWidth(100);
		tableStaff.getColumnModel().getColumn(6).setPreferredWidth(100);
		tableStaff.getColumnModel().getColumn(7).setPreferredWidth(100);
		tableStaff.getColumnModel().getColumn(8).setPreferredWidth(80);
		tableStaff.getColumnModel().getColumn(9).setPreferredWidth(130);
		tableStaff.getColumnModel().getColumn(10).setPreferredWidth(160);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableStaff.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tableStaff.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tableStaff.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		tableStaff.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		tableStaff.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

	}

	/**
	 * tìm kiếm nhân viên dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		ArrayList<NhanVien> staffList = null;
		String keyword = "";
		if (searchTypeName.equalsIgnoreCase("Tất cả")) {
			staffList = NhanVienDAO.getInstance().getStaffList();
		} else if (searchTypeName.equalsIgnoreCase("Tên nhân viên")) {
			keyword = txtKeyWord.getText().trim();
			staffList = NhanVienDAO.getInstance().getStaffListByStaffName(keyword);
		} else if (searchTypeName.equalsIgnoreCase("Số diện thoại")) {
			keyword = txtKeyWord.getText().trim();
			if (keyword.matches("^[\\d]{0,10}$")) {
				staffList = NhanVienDAO.getInstance().getStaffListByPhoneNumber(keyword);
			} else {
				String message = "Sổ điện phải phải là số, không được quá 10 số";
				showMessage(txtKeyWord, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			}
		} else if (searchTypeName.equalsIgnoreCase("Chức vụ")) {
			keyword = cboSearchPosition.getSelectedItem().toString().trim();
			staffList = NhanVienDAO.getInstance().getStaffListByPosition(keyword);
		}
		loadStaffList(staffList);
	}
}