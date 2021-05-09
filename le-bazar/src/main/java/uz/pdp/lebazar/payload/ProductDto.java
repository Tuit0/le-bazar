package uz.pdp.lebazar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lebazar.entity.AttachmentContent;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private UUID id;
    private String maker;
    private String name;
    private Double price;
    private byte[] avatar;
    private String desc;
//    private AttachmentContent attachmentContent;

    public ProductDto(String maker, String name, Double price, byte[] avatar, String desc) {
        this.maker = maker;
        this.name = name;
        this.price = price;
        this.avatar = avatar;
        this.desc = desc;
    }
}
