package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.RequestDTO.Candidate.InitiateInterview;
import code.with.sahbaan.neohire.Services.AiInterviewService;
import code.with.sahbaan.neohire.Services.UserService;
import code.with.sahbaan.neohire.Utils.InterviewDetails;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AiInterviewServiceImpl implements AiInterviewService {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatClient.Builder chatClient;

    @Autowired
    private SpeechModel speechModel;

    private final Map<String, InterviewDetails> sessions = new ConcurrentHashMap<>();

    @Override
    public byte[] initiateInterview(InitiateInterview initiateInterview) throws Exception {
        try{
            // Initiating
            InterviewDetails interviewDetails = new InterviewDetails();

            // Building System message
            StringBuilder systemMessage = new  StringBuilder();
            systemMessage.append("You are a interviewer needs to conduct interview for a candidate having ")
                    .append(initiateInterview.getYourYearsOfExperience())
                    .append(" years of experience in ")
                    .append(initiateInterview.getTitle())
                    .append(". You have to take interview for the position of ")
                    .append(initiateInterview.getApplyingForPosition())
                    .append(" that requires ")
                    .append(initiateInterview.getRequiredExperienceForJob())
                    .append("of experience. You need to ask relevant queries and evaluate the candidate between 1-100");

            // Building Introduction Message
            String query = "Introduce yourself to the candidate and ask for his introduction";
            String response = chatClient.defaultSystem(systemMessage.toString()).build().prompt().user(query).call().content();

            // Adding messages to message list
            List<Message> messages = interviewDetails.getMessages();
            messages.add(new UserMessage(query));
            messages.add(new AssistantMessage(response));

            // Updating interviewDetails Object
            interviewDetails.setMessages(messages);
            interviewDetails.setSystemMessage(systemMessage.toString());

            // Adding Interview into the session
            sessions.put(userService.getToken(), interviewDetails);

            // converting text into speech
            return speechModel.call(new SpeechPrompt(response)).getResult().getOutput();

        }catch(Exception e){
            throw new  Exception("Failed to initiate interview");
        }
    }
}
