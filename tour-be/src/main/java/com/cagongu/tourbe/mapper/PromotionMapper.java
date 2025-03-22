package com.cagongu.tourbe.mapper;


import com.cagongu.tourbe.dto.request.PromotionCreationRequest;
import com.cagongu.tourbe.dto.request.PromotionUpdateRequest;
import com.cagongu.tourbe.dto.response.PromotionResponse;
import com.cagongu.tourbe.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {BookingMapper.class})
public interface PromotionMapper {
    @Mapping(target = "dateAdded", ignore = true)
    @Mapping(target = "dateEdited", ignore = true)
    @Mapping(target = "dateDeleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    Promotion promotionRequestToPromotion(PromotionCreationRequest request);

    PromotionResponse promotionToPromotionResponse(Promotion promotion);

    @Mapping(target = "dateAdded", ignore = true)
    @Mapping(target = "dateEdited", ignore = true)
    @Mapping(target = "dateDeleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    void updatePromotion(@MappingTarget Promotion promotion, PromotionUpdateRequest request);
}
