package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.LoaiDichVu;

public class LoaiDichVuDAO {
    private static LoaiDichVuDAO instance = new LoaiDichVuDAO();

    public static LoaiDichVuDAO getInstance() {
        if (instance == null)
            instance = new LoaiDichVuDAO();
        return instance;
    }

    /**
     * Lấy danh sách thông tin tất cả loại dịch vụ
     * 
     * @return <code>ArrayList - LoaiDichVu</code>: danh sách loại dịch vụ
     */
    public ArrayList<LoaiDichVu> getServiceTypeList() {
        ArrayList<LoaiDichVu> dataList = new ArrayList<LoaiDichVu>();
        String query = "{CALL USP_getLoaiDichVuList()}";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new LoaiDichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy thông tin loại dịch vụ dựa theo tên
     * 
     * @param serviceTypeName <code>String</code>: tên loại dịch vụ
     * @return <code>LoaiDichVu</code>: loại dịch vụ được tìm thấy
     */
    public LoaiDichVu getServiceTypeByName(String serviceTypeName) {
        LoaiDichVu data = null;
        String query = "{CALL USP_getServiceTypeByName( ? )}";
        Object[] parameter = new Object[] { serviceTypeName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next())
                data = new LoaiDichVu(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Lấy mã loại dịch vụ được thêm mới nhất
     * 
     * @return <code>String</code>: mã loại dịch vụ mới nhất được thêm vào
     */
    public String getLastServiceTypeID() {
        String query = "{CALL USP_getLastServiceTypeID}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String staffID = obj != null ? obj.toString() : "";
        return staffID;
    }

    /**
     * Lấy danh sách loại dịch vụ có tên phù hợp với từ khóa
     * 
     * @param serviceTypeName <code>String</code>: từ khóa trong tên loại dịch vụ
     * @return <code>ArrayList - LoaiDichVu</code>: danh sách loại dịch vụ phù hợp điều kiện
     */
    public ArrayList<LoaiDichVu> getServiceTypeListByName(String serviceTypeName) {
        String query = "{CALL USP_getServiceTypeListByName( ? )}";
        Object[] parameter = new Object[] { serviceTypeName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<LoaiDichVu> serviceTypeList = new ArrayList<LoaiDichVu>();
        try {
            while (rs.next()) {
                serviceTypeList.add(new LoaiDichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceTypeList;
    }

    /**
     * Lấy thông tin loại dịch vụ dựa theo tên
     * 
     * @param serviceTypeID <code>String</code>: mã loại dịch vụ
     * @return <code>LoaiDichVu</code>: loại dịch vụ được tìm thấy
     */
    public LoaiDichVu getServiceTypeById(String serviceTypeID) {
        LoaiDichVu data = null;
        String query = "{CALL USP_getServiceTypeById( ? )}";
        Object[] parameter = new Object[] { serviceTypeID };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next())
                data = new LoaiDichVu(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Thêm một loại dịch vụ mới vào cơ sở dữ liệu
     * 
     * @param serviceType <code>LoaiDichVu</code>: loại dịch vụ cần thêm
     * @return <code>boolean</code>: <code>true</code> thành công,
     *         <code>false</code> nếu thất bại
     */
    public Boolean insertService(LoaiDichVu serviceType) {
        String query = "{CALL USP_insertServiceType( ? , ? )}";
        Object[] parameter = new Object[] { serviceType.getMaLDV(), serviceType.getTenLDV() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Cập nhật thông tin loại dịch vụ vào cơ sở dữ liệu
     * 
     * @param serviceType <code>LoaiDichVu</code>: loại dịch vụ cần cập nhật
     * @return <code>boolean</code>: <code>true</code> thành công,
     *         <code>false</code> nếu thất bại
     */
    public Boolean updateInfoServiceType(LoaiDichVu serviceType) {
        String query = "{CALL USP_updateInfoServiceType( ? , ? )}";
        Object[] parameter = new Object[] { serviceType.getMaLDV(), serviceType.getTenLDV() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}