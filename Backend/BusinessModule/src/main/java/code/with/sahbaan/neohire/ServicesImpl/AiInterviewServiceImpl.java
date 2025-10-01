package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Services.AiInterviewService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AiInterviewServiceImpl implements AiInterviewService {

    private final Map<String, List<Message>> sessions = new ConcurrentHashMap<>();
}
