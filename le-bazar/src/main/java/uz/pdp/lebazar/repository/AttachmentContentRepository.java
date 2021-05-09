package uz.pdp.lebazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lebazar.entity.AttachmentContent;

import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    AttachmentContent findByAttachmentId(UUID attachment_id);
}
