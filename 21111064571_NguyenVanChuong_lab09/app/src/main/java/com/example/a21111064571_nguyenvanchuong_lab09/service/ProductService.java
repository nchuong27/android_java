package com.example.a21111064571_nguyenvanchuong_lab09.service;

import android.content.Context;

import com.example.a21111064571_nguyenvanchuong_lab09.DAO.ProductDAO;
import com.example.a21111064571_nguyenvanchuong_lab09.DAO.ProductDetailDAO;
import com.example.a21111064571_nguyenvanchuong_lab09.model.Product;
import com.example.a21111064571_nguyenvanchuong_lab09.model.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;
    private ProductDetailDAO productDetailDAO;
    public ProductService(Context context) {
        productDAO = new ProductDAO(context);
        productDetailDAO = new ProductDetailDAO(context);
    }
    public void open() {
        productDAO.open();
        productDetailDAO.open();
    }
    public void close() {
        productDAO.close();
        productDetailDAO.close();
    }
    // Thêm một sản phẩm mới kèm theo chi tiết vào cả hai bảng
    public long addProductWithDetail(Product product, ProductDetail productDetail) {
        long productId = productDAO.addProduct(product);
        if (productId != -1) {
            productDetail.setProductId((int) productId);
            return productDetailDAO.addProductDetail(productDetail);
        }
        return -1;
    }
    // Lấy tất cả sản phẩm từ cả hai bảng
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        List<Product> allProducts = productDAO.getAllProducts();
        for (Product product : allProducts) {
            ProductDetail productDetail = productDetailDAO.getProductDetailByProductId(product.getId());
            if (productDetail != null) {
                product.setProductDetail(productDetail);
            }
            products.add(product);
        }
        return products;
    }
    // Sửa thông tin sản phẩm
    public long updateProductWithDetail(Product product, ProductDetail productDetail) {
        long rowsAffected = productDAO.updateProduct(product);
        if (rowsAffected > 0) {
            productDetail.setProductId(product.getId()); // Cập nhật productId cho productDetail
            return productDetailDAO.updateProductDetail(productDetail);
        }
        return -1;
    }
    // Xóa sản phẩm
    public void deleteProduct(int productId) {
// Trước khi xóa sản phẩm, bạn có thể kiểm tra và xóa các chi tiết sản phẩm liên quan ở bảng product_details
        productDetailDAO.deleteProductDetail(productId);
        // Sau đó, gọi phương thức deleteProduct từ ProductDAO để xóa sản phẩm
        productDAO.deleteProduct(productId);
    }

    public void updateProduct(Product editedProduct) {
        // Đầu tiên, cập nhật thông tin sản phẩm trong bảng products
        long rowsAffected = productDAO.updateProduct(editedProduct);
        // Nếu cập nhật thành công, tiếp tục cập nhật thông tin chi tiết sản phẩm trong bảng product_details
        if (rowsAffected > 0) {
            // Lấy chi tiết sản phẩm hiện tại từ bảng product_details
            ProductDetail existingDetail = productDetailDAO.getProductDetailByProductId(editedProduct.getId());

            // Nếu sản phẩm có chi tiết sản phẩm, cập nhật thông tin chi tiết sản phẩm
            if (existingDetail != null) {
                ProductDetail updatedDetail = editedProduct.getProductDetail();
                updatedDetail.setProductId(editedProduct.getId()); // Đảm bảo rằng productId của chi tiết sản phẩm được cập nhật đúng
                productDetailDAO.updateProductDetail(updatedDetail);
            } else {
                // Nếu sản phẩm không có chi tiết sản phẩm, thêm mới chi tiết sản phẩm
                ProductDetail newDetail = editedProduct.getProductDetail();
                newDetail.setProductId(editedProduct.getId()); // Đảm bảo rằng productId của chi tiết sản phẩm được cập nhật đúng
                productDetailDAO.addProductDetail(newDetail);
            }
        }
    }
}
