package com.example.koifarm.api;

import com.example.koifarm.entity.Cart;
import com.example.koifarm.entity.CartItem;
import com.example.koifarm.entity.User;
import com.example.koifarm.model.request.CartRequest;
import com.example.koifarm.model.response.CartResponse;
import com.example.koifarm.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class CartAPI {

    @Autowired
    CartService cartService;

    // Thêm cá Koi vào giỏ
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody CartRequest cartRequest, @AuthenticationPrincipal User user) {
        Cart cart = cartService.addToCart(user.getPhone(), cartRequest); // Sử dụng số điện thoại

        return ResponseEntity.ok(createCartResponse(cart));
    }

    // Lấy thông tin giỏ hàng
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);

        if (cart == null) {
            return ResponseEntity.notFound().build(); // Nếu không tìm thấy giỏ hàng
        }

        return ResponseEntity.ok(createCartResponse(cart));
    }

    // Xóa cá Koi khỏi giỏ hàng
    @DeleteMapping("/{cartId}/remove/{koiId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId, @PathVariable Long koiId) {
        boolean success = cartService.removeItemFromCart(cartId, koiId);

        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build(); // Trả về theo kết quả xóa
    }

    // Tạo CartResponse từ Cart
    private CartResponse createCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getId());
        cartResponse.setItems(cart.getItems());

        // Tính tổng số lượng
        int totalQuantity = cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        cartResponse.setTotalQuantity(totalQuantity);

        // Tính tổng giá trị
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> item.getKoi().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cartResponse.setTotalPrice(totalPrice);

        return cartResponse;
    }

}
