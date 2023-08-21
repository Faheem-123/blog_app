package blog_App.payloads;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailResponse {
    private Integer code;
    private String message;
}
