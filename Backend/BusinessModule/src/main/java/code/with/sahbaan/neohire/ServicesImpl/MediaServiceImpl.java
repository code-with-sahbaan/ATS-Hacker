package code.with.sahbaan.neohire.ServicesImpl;

import code.with.sahbaan.neohire.Services.MediaService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) throws Exception{
        try{
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder","neo-hire-files",
                    "use_filename", "true",
                    "unique_filename", "false"
            )).get("url").toString();
        }catch (Exception e){
            throw new Exception("Failed to upload file");
        }
    }
}
