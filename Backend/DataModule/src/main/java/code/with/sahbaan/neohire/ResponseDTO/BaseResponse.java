package code.with.sahbaan.neohire.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {

    private String responseMessage;
    private T body;
}
