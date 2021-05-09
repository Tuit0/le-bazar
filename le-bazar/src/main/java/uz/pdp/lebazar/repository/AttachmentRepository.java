package uz.pdp.lebazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lebazar.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

}
