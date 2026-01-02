package com.icinema.dto;

import com.icinema.enums.CardType;
import lombok.Data;

@Data
public class PaymentDTO {
    private Long bookingId;
    @jakarta.validation.constraints.NotNull(message = "Card type is required")
    private CardType cardType;

    @jakarta.validation.constraints.NotBlank(message = "Card number is required")
    @jakarta.validation.constraints.Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;

    @jakarta.validation.constraints.NotBlank(message = "CVV is required")
    @jakarta.validation.constraints.Pattern(regexp = "\\d{3}", message = "CVV must be 3 digits")
    private String cvv;

    @jakarta.validation.constraints.NotBlank(message = "Expiry date is required")
    @jakarta.validation.constraints.Pattern(regexp = "^(0[1-9]|1[0-2])\\/([0-9]{2})$", message = "Expiry date must be MM/YY")
    private String expiryDate;
}
