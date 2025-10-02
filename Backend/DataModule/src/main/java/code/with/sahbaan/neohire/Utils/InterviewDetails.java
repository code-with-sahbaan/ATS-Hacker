package code.with.sahbaan.neohire.Utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.messages.Message;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InterviewDetails {

    private String systemMessage;
    private List<Message> messages = new ArrayList<>();
}
