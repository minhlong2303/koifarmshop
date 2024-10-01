package com.example.koifarm.service;

import com.example.koifarm.entity.Cart;
import com.example.koifarm.entity.CartItem;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.User;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.request.CartRequest;
import com.example.koifarm.repository.CartRepository;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    KoiRepository koiRepository;

    @Autowired
    UserRepository userRepository;

    public Cart addToCart(String phone, CartRequest cartRequest) {
        // Lấy thông tin người dùng dựa vào số điện thoại
        User user = userRepository.findUserByPhone(phone);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        // Lấy giỏ hàng của user
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElse(new Cart());

        // Tìm cá Koi dựa vào ID
        Koi koi = koiRepository.findById(cartRequest.getKoiId())
                .orElseThrow(() -> new EntityNotFoundException("Koi not found"));

        // Tạo CartItem mới
        CartItem cartItem = new CartItem();
        cartItem.setKoi(koi);
        cartItem.setQuantity(cartRequest.getQuantity());
        cartItem.setCart(cart);

        // Thêm CartItem vào giỏ
        cart.getItems().add(cartItem);

        // Lưu lại giỏ hàng
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found")); // Ném ngoại lệ nếu không tìm thấy
    }

    public boolean removeItemFromCart(Long cartId, Long koiId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        // Tìm CartItem dựa vào Koi ID và xóa nó
        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                .filter(item -> item.getKoi().getKoiID().equals(koiId)) // Sử dụng getKoiID() để lấy ID
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cart.getItems().remove(cartItem);
            cartRepository.save(cart);
            return true; // Xóa thành công
        }

        return false; // Không tìm thấy CartItem để xóa
    }

    public BigDecimal calculateTotalPrice(Cart cart) {
        BigDecimal total = BigDecimal.ZERO; // Khởi tạo tổng

        for (CartItem item : cart.getItems()) {
            BigDecimal price = item.getKoi().getPrice(); // Lấy giá cá Koi
            int quantity = item.getQuantity(); // Số lượng cá Koi

            // Tính toán tổng giá trị
            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
        }

        return total; // Trả về tổng giá trị
    }
}
