package com.nissum.technical.challenge.users.model.dto.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberRequests {

    @Schema(hidden = true)
    private Long id;

    @Schema(description = "Phone number", example = "2559433")
    private String number;

    @Schema(description = "City code", example = "4")
    private String cityCode;

    @Schema(description = "Country code", example = "57")
    private String countryCode;


}
