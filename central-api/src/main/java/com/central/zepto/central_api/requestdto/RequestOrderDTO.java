package com.central.zepto.central_api.requestdto;

import com.central.zepto.central_api.models.AppOrder;
import com.central.zepto.central_api.models.AppUser;
import com.central.zepto.central_api.responsedto.ResponseBillDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestOrderDTO {
    AppUser customer;
    AppUser deliveryPartner;
    ResponseBillDTO bill;
    AppOrder order;
}
