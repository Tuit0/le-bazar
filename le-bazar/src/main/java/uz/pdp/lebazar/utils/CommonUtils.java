package uz.pdp.lebazar.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uz.pdp.lebazar.exceptions.MyRuntimeException;

import java.util.Random;

public class CommonUtils {
    public static int generateCode() {
        return new Random().nextInt(999999-100000+1)+100000;
    }
    public static void validatePageNumberOrSize(int page, int size) {
        if (page<0) {
            throw new MyRuntimeException("The number of page can't be less than 0 !!");
        }
        if (size > ApplicationConstants.DEFAULT_MAX_SIZE) {
            throw new MyRuntimeException("The size of page can't be more than "+ApplicationConstants.DEFAULT_MAX_SIZE+" !!");
        }
    }

    public static Pageable simplePageable(int page, int size) {
        validatePageNumberOrSize(page,size);
        return PageRequest.of(page,size);
    }

    public static Pageable sortedByCreatedAt(int page, int size) {
        validatePageNumberOrSize(page,size);
        return PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt"));
    }
}
