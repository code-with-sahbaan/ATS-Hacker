package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.RequestDTO.Candidate.InitiateInterview;
import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import code.with.sahbaan.neohire.Services.AiInterviewService;
import code.with.sahbaan.neohire.Services.UserService;
import code.with.sahbaan.neohire.Utils.InterviewDetails;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private OpenAiAudioTranscriptionModel audioTranscriptionModel;

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
                    .append("of experience. You need to ask relevant queries and evaluate the candidate between 1-100.")
                    .append(" Whenever user answer the query completely then ask next question or if he/she ask for some clarification of current one then give that.");

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
            return textToSpeech(response);

        }catch(Exception e){
            throw new  Exception("Failed to initiate interview");
        }
    }

    private byte[] textToSpeech(String message){
        return speechModel.call(new SpeechPrompt(message)).getResult().getOutput();
    }

    private String speechToText(MultipartFile speech){
        OpenAiAudioTranscriptionOptions aiAudioTranscriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .build();
        AudioTranscriptionPrompt audioTranscriptionPrompt = new AudioTranscriptionPrompt(speech.getResource(), aiAudioTranscriptionOptions);
        return audioTranscriptionModel.call(audioTranscriptionPrompt).getResult().getOutput();
    }

    @Override
    public byte[] getReply(MultipartFile speech) throws Exception {
        try{
            InterviewDetails interviewDetails = sessions.get(userService.getToken());
            List<Message> messages = interviewDetails.getMessages();
            String transcription = speechToText(speech);
            String response = chatClient.defaultSystem(interviewDetails
                    .getSystemMessage())
                    .build()
                    .prompt()
                    .user(transcription)
                    .messages(messages)
                    .call()
                    .content();

            messages.add(new UserMessage(transcription));
            messages.add(new AssistantMessage(response));
            interviewDetails.setMessages(messages);
            sessions.put(userService.getToken(), interviewDetails);
            return textToSpeech(response);
        }catch(Exception e){
            throw new  Exception("Failed to get reply");
        }
    }
}
