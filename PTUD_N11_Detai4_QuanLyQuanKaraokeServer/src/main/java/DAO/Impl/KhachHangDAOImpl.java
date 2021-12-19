package DAO.Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import DAO.KhachHangDAO;
import Util.HibernateUtil;
import entity.KhachHang;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code KhachHang}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Võ Minh Hiếu, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 11/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class KhachHangDAOImpl extends UnicastRemoteObject implements KhachHangDAO {
    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public KhachHangDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * Lấy ra danh sách khách hàng theo từng trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>} : danh sách khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<KhachHang> getCustomerListAndPageNumber(int currentPage, int lineNumberDisplayed)
            throws RemoteException {
        String query = "{CALL USP_getCustomerListAndPageNumber( ? , ? )}";
        ArrayList<KhachHang> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, currentPage)
                    .setParameter(2, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng khách hàng
     * 
     * @return {@code int}: số lượng khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfCustomerList() throws RemoteException {
        String query = "{CALL USP_getTotalLineOfCustomerList()}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách tất cả khách hàng dựa theo tên khách hàng và số trang được chỉ
     * định
     * 
     * @param customerName        {@code String}: tên khách hàng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<KhachHang> getCustomerListByNameAndPageNumber(String customerName, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByNameAndPageNumber( ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, customerName)
                    .setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng khách hàng dựa theo tên khách hàng
     * 
     * @param customerName {@code String}: tên khách hàng
     * @return {@code int}: số lượng khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfCustomerListByName(String customerName) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfCustomerListByName( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, customerName).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách tất cả khách hàng dựa theo số điện thoại và số trang được chỉ
     * định
     * 
     * @param phoneNumber         {@code String}: số điện thoại của khách hàng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<KhachHang> getCustomerListByPhoneNumberAndPageNumber(String phoneNumber, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByPhoneNumberAndPageNumber( ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, phoneNumber)
                    .setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng khách hàng dựa theo số điện thoại
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @return {@code int}: số lượng khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfCustomerListByPhoneNumber(String phoneNumber) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfCustomerListByPhoneNumber( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, phoneNumber).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng
     * 
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<KhachHang> getCustomerListUnBooked() throws RemoteException {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBooked()}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo tên
     * 
     * @param customerName {@code String}: tên khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<KhachHang> getCustomerListUnBookedByName(String customerName) throws RemoteException {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBookedByName( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, customerName)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo CMND/CCCD
     * 
     * @param cmnd {@code String}: CMND/CCCD của khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<KhachHang> getCustomerListUnBookedByCMND(String cmnd) throws RemoteException {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBookedByCMND( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, cmnd)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo CMND/CCCD
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<KhachHang> getCustomerListUnBookedByPhoneNumber(String phoneNumber) throws RemoteException {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBookedByPhoneNumber( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, phoneNumber)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy thông tin khách hàng theo mã khách hàng
     * 
     * @param customerId {@code String}: mã khách hàng
     * @return {@code KhachHang}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code KhachHang}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public KhachHang getCustomerById(String customerId) throws RemoteException {
        KhachHang result = null;
        String query = "{CALL USP_getCustomerById( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            ArrayList<KhachHang> resultQuery = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, customerId)
                    .getResultList();
            tr.commit();
            if (resultQuery.size() > 0) {
                result = resultQuery.get(0);
            } else {
                result = null;
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thêm khách hàng mới
     * 
     * @param customer {@code KhachHang}: khách hàng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    @Override
    public boolean insertCustomer(KhachHang customer) throws RemoteException {
        String query = "{CALL USP_insertCustomer( ? , ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, customer.getMaKH())
                    .setParameter(2, customer.getCmnd())
                    .setParameter(3, customer.getHoTen())
                    .setParameter(4, customer.getGioiTinh())
                    .setParameter(5, customer.getSoDienThoai())
                    .setParameter(6, customer.getNgaySinh())
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Lấy thông tin khách hàng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã khách hàng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getLastCustomerId() throws RemoteException {
        String query = "{CALL USP_getLastCustomerId()}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        String result = "";
        try {
            tr.begin();
            ArrayList<String> resultQuery = (ArrayList<String>) em.createNativeQuery(query)
                    .getResultList();
            tr.commit();
            if (resultQuery.size() > 0) {
                result = resultQuery.get(0);
            } else {
                result = "";
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cập nhật thông tin khách hàng
     * 
     * @param customer {@code khachHang}: khách hàng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu cập nhật thành công thì trả về {@code true}</li>
     *         <li>Nếu cập nhật thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    @Override
    public boolean updateCustomerInfo(KhachHang customer) throws RemoteException {
        String query = "{CALL USP_updateCustomerInfo( ? , ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, customer.getMaKH())
                    .setParameter(2, customer.getCmnd())
                    .setParameter(3, customer.getHoTen())
                    .setParameter(4, customer.getGioiTinh())
                    .setParameter(5, customer.getSoDienThoai())
                    .setParameter(6, customer.getNgaySinh())
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Lấy danh sách khách hàng theo giới tính
     * 
     * @param gender              {@code boolean}: giới tính khách hàng
     *                            <ul>
     *                            <li>{@code true} thì là Nữ</li>
     *                            <li>{@code false} thì là Nam</li>
     *                            </ul>
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<KhachHang> getCustomerListByGenderAndPageNumber(boolean gender, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getCustomerListByGenderAndPageNumber( ? , ? , ? )}";
        ArrayList<KhachHang> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, gender)
                    .setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng khách hàng dựa theo giới tính
     * 
     * @param gender {@code boolean}: giới tính khách hàng
     *               <ul>
     *               <li>{@code true} thì là Nữ</li>
     *               <li>{@code false} thì là Nam</li>
     *               </ul>
     * @return {@code int}: số lượng khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfCustomerListByGender(boolean gender) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfCustomerListByGender( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, gender).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy thông tin khách hàng theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn
     * @return {@code KhachHang}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code KhachHang}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    @SuppressWarnings("unchecked")
    @Override
    public KhachHang getCustomerByBillId(String billId) {
        KhachHang result = null;
        String query = "{CALL USP_getCustomerByBillId( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            ArrayList<KhachHang> resultQuery = (ArrayList<KhachHang>) em.createNativeQuery(query, KhachHang.class)
                    .setParameter(1, billId)
                    .getResultList();
            tr.commit();
            if (resultQuery.size() > 0) {
                result = resultQuery.get(0);
            } else {
                result = null;
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }
}